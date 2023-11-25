package view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import define.errorColor
import sever.Student

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun searchScreen(screenNum:(Int)->Unit,studentReturn: (Student)->Unit) {
    var text by remember { mutableStateOf("") }
    var student by remember { mutableStateOf(Student(0,"",0.0))}
    var r1 by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(modifier = Modifier.width(560.dp)) {
            Text(text = "查找学生信息:", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "输入学号：", fontSize = 14.sp, modifier = Modifier.width(560.dp))
        OutlinedTextField(
            value = text,
            onValueChange = { text = it;r1 = false },
            isError = r1,
            modifier = Modifier.width(560.dp),
            singleLine = true,
            textStyle = TextStyle(fontSize = 16.sp),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                        .clickable {
                            var num = 0L
                            try {
                                num=text.toLong()
                            } catch (e: Exception) {
                                r1 = true
                            }
                            if (!r1){
                                val t = Thread{
                                    student= Sever.getAStu(num)
                                }
                                t.start()
                            }
                        },
                )
            }
        )
        Spacer(modifier = Modifier.height(5.dp))
        if (student.name!=""&&student.name!="未找到学生"&&student.name!="IO错误"&&student.name!="程序损坏！"){
            r1=false
            studentReturn(student)
            screenNum(3)
        }else if (student.name=="IO错误"){
            Text(text = "IO错误", color = errorColor)
        }else if (student.name=="程序损坏！"){
            Text(text = "程序可能已经损坏！ClassNotFound！", color = errorColor)
        }else if (student.name=="未找到学生"){
            r1=true
            Text(text = "未找到学生", color = errorColor)
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}