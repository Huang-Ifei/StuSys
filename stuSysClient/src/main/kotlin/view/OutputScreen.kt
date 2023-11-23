package view

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import define.primary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import sever.Student
import sever.StudentList
import widget.headCard
import widget.outPutCard
import java.io.BufferedReader
import java.io.FileOutputStream
import java.io.FileReader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun outputScreen(screenNum: (Int) -> Unit, studentReturn: (Student) -> Unit) {
    var isOpen by remember {
        mutableStateOf(false)
    }
    var className by remember { mutableStateOf("base") }
    try {
        val br = BufferedReader(FileReader("src\\main\\java\\sever\\Class"))
        var read = ""
        read = br.readLine()
        if (read != "") {
            className = read
        } else {
            val fos = FileOutputStream("src\\main\\java\\sever\\Class")
            fos.write("base".toByteArray())
            fos.close()
        }
        br.close()
    } catch (e: Exception) {
        val fos = FileOutputStream("src\\main\\java\\sever\\Class")
        fos.write("base".toByteArray())
        fos.close()
    }
    var show by remember { mutableStateOf(false) }
    var list by remember { mutableStateOf(StudentList()) }
    var classList by remember { mutableStateOf(Sever.getBasicClasses()) }
    var showList by remember { mutableStateOf(list.stuList) }
    //协程赋值
    LaunchedEffect(Unit) {
        list = withContext(Dispatchers.IO) {
            Sever.getByClass(className)
        }
        classList = withContext(Dispatchers.IO) {
            Sever.getClasses()
        }
        showList = list.stuList
        show = false
        show = true
    }
    val height by animateIntAsState(
        targetValue = if (isOpen) ((classList.size) * 50 + 40) else 40,
        label = "",
        animationSpec = tween(500)
    )

    Box() {
        Column {
            Box(modifier = Modifier.height(55.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxHeight()) {
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = "学生表:", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Button(
                        onClick = { show = false;showList = list.numSortOutput();show = true },
                        colors = ButtonDefaults.buttonColors(containerColor = primary, contentColor = Color.White),
                    ) {
                        Text(text = "学号顺序", fontSize = 13.sp)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        onClick = { show = false;showList = list.sortLowOutput();show = true },
                        colors = ButtonDefaults.buttonColors(containerColor = primary, contentColor = Color.White)
                    ) {
                        Text(text = "成绩顺序", fontSize = 13.sp)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        onClick = { show = false;showList = list.sortTopOutput();show = true },
                        colors = ButtonDefaults.buttonColors(containerColor = primary, contentColor = Color.White)
                    ) {
                        Text(text = "成绩逆序", fontSize = 13.sp)
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                }
            }
            headCard()
            LazyColumn {
                if (show) {
                    for (i in 0 until showList.size) {
                        item {
                            outPutCard(showList[i], screenNum, studentReturn)
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }
            }
        }
        Card(
            colors = CardDefaults.cardColors(Color(248, 248, 255)),
            modifier = Modifier.padding(start = 115.dp, top = 8.dp).height(height.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        ) {
            Column(
                modifier = Modifier.clickable { isOpen = !isOpen }.width(160.dp).height(40.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("班级$className", color = Color.Black)
            }
            LazyColumn(modifier = Modifier.padding(horizontal = 10.dp)) {
                items(classList.size) {
                    if (classList[it] != null) {
                        ElevatedFilterChip(
                            selected = (className == classList[it]),
                            label = { Text("班级${classList[it]}") },
                            onClick = {
                                if (!classList[it].equals("IO错误") && !classList[it].equals("等待中..")) {
                                    className = classList[it]
                                    val fos = FileOutputStream("src\\main\\java\\sever\\Class")
                                    fos.write(className.toByteArray())
                                    fos.close()
                                    list = Sever.getByClass(classList[it])
                                    showList = list.stuList
                                    show = false
                                    show = true
                                }
                            },
                            border = FilterChipDefaults.filterChipBorder(
                                selectedBorderColor = Color(248, 248, 255),
                            ),
                            elevation = FilterChipDefaults.elevatedFilterChipElevation(0.dp)
                        )
                    }
                }
            }
        }
    }
}