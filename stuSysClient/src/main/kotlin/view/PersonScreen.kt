package view

import androidx.compose.foundation.BorderStroke
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
import com.ncist.stu.sever.Student
import com.ncist.stu.sever.TestPointList
import define.borderColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import widget.headCard
import widget.outPutCard
import widget.personTestCard

@Composable
fun personScreen(screenNum: (Int) -> Unit, student: Student, studentReturn: (Student) -> Unit) {
    var testPointList by remember { mutableStateOf(TestPointList()) }
    LaunchedEffect(Unit){
        testPointList = withContext(Dispatchers.IO){
            Sever.getAStuTest(student.id)
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(modifier = Modifier.width(620.dp), verticalAlignment = Alignment.Top) {
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "学生信息:", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
        }
        Spacer(modifier = Modifier.height(5.dp))
        Column(modifier = Modifier.width(640.dp)) {
            headCard()
            outPutCard(student, screenNum, studentReturn)
            Spacer(modifier = Modifier.height(5.dp))
        }
        Card (colors = CardDefaults.cardColors(Color(244, 244, 246))) {
            LazyColumn (modifier = Modifier.size(620.dp,200.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                items(testPointList.testPointList.size){
                    personTestCard(testPointList.testPointList[it])
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.width(620.dp)
        ) {
            Button(
                onClick = {
                    studentReturn(student)
                    screenNum(5)
                },
            ) {
                Text(text = "变更学生信息")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = {
                    studentReturn(student)
                    screenNum(6)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
                border = BorderStroke(1.dp, borderColor)
            ) {
                Text(text = "删除学生信息")
            }
            Spacer(modifier = Modifier.width(10.dp))
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}
