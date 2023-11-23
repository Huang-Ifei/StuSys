package view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sever.dao.StudentDAO
import define.borderColor
import define.errorColor
import sever.Student

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun updateScreen(screenNum: (Int) -> Unit, student: Student, studentReturn: (Student) -> Unit) {
    var isTure by remember { mutableStateOf(false) }
    var stuNum by remember { mutableStateOf(student.stuNum.toString()) }
    var name by remember { mutableStateOf(student.name) }
    var point by remember { mutableStateOf(student.point.toString()) }
    var r1 by remember { mutableStateOf(false) }
    var r2 by remember { mutableStateOf(false) }
    var r3 by remember { mutableStateOf(false) }
    var sqlSystemFailed by remember { mutableStateOf(false) }
    if (!isTure) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(modifier = Modifier.width(280.dp)) {
                Text(text = "更新学生信息:", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "输入学号：", fontSize = 14.sp, modifier = Modifier.width(280.dp))
            OutlinedTextField(
                value = stuNum,
                onValueChange = { stuNum = it;r1 = false },
                isError = r1,
                modifier = Modifier.width(280.dp),
                singleLine = true,
                textStyle = TextStyle(fontSize = 16.sp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "输入姓名：", fontSize = 14.sp, modifier = Modifier.width(280.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it;r2 = false },
                isError = r2,
                modifier = Modifier.width(280.dp),
                singleLine = true,
                textStyle = TextStyle(fontSize = 16.sp)
            )

            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "输入成绩：", fontSize = 14.sp, modifier = Modifier.width(280.dp))
            OutlinedTextField(
                value = point,
                onValueChange = { point = it;r3 = false },
                isError = r3,
                modifier = Modifier.width(280.dp),
                singleLine = true,
                textStyle = TextStyle(fontSize = 16.sp)
            )
            if (r1 != false || r2 != false || r3 != false) {
                Text(text = "输入的数据有误", color = errorColor)
                Spacer(modifier = Modifier.height(5.dp))
            }else if (sqlSystemFailed==true){
                Text(text = "数据库操作出错", color = errorColor)
                Spacer(modifier = Modifier.height(5.dp))
            }
            else {
                Spacer(modifier = Modifier.height(25.dp))
            }
            Button(
                onClick = {
                    var input = true
                    r1 = false
                    r2 = false
                    r3 = false
                    var a = 0L
                    var b = ""
                    var c = 0.0
                    try {
                        a = stuNum.toLong()
                    } catch (e: Exception) {
                        input = false
                        r1 = true
                    }
                    try {
                        if (name != "") b = name
                        else {
                            input = false
                            r2 = true
                        }
                    } catch (e: Exception) {
                        input = false
                        r2 = true
                    }
                    try {
                        c = point.toDouble()
                    } catch (e: Exception) {
                        input = false
                        r3 = true
                    }
                    if (input) {
                        val studentDAO = StudentDAO()
                        studentDAO.updateByNum(student.stuNum,a,b,c)
                        if (studentDAO.errorMessage==""){
                            stuNum = ""
                            name = ""
                            point = ""
                            isTure = true
                        }else{
                            sqlSystemFailed=true
                        }
                    }
                }, modifier = Modifier.size(280.dp, 55.dp),
                shape = AbsoluteRoundedCornerShape(5.dp)
            ) {
                Text("确认更新信息")
            }
            Spacer(modifier = Modifier.height(35.dp))
        }
    } else if (isTure) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "更新信息成功", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(5.dp))
            Row {
                Button(onClick = { screenNum(0)}) {
                    Text(text = "返回列表界面")
                }
                Spacer(modifier = Modifier.width(5.dp))
                Button(
                    onClick = { screenNum(2) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
                    border = BorderStroke(1.dp, borderColor)
                ) {
                    Text(text = "返回搜索界面")
                }
            }
            Spacer(modifier = Modifier.height(35.dp))
        }
    }
}