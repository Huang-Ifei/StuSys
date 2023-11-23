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
import define.borderColor
import define.errorColor
import kotlinx.coroutines.runBlocking
import sever.StudentList


@Composable
fun addScreen(screenNum: (Int) -> Unit) {
    var isTure by remember { mutableStateOf(false) }
    var stuClass by remember { mutableStateOf(StudentList()) }
    var r1 by remember { mutableStateOf(false) }
    var r2 by remember { mutableStateOf(false) }
    var r3 by remember { mutableStateOf(false) }
    var netError by remember { mutableStateOf(false) }
    var stuNum by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var point by remember { mutableStateOf("") }
    var buttonText by remember { mutableStateOf("确认录入信息") }
    stuClass.createdClass()
    if (!isTure) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(modifier = Modifier.width(280.dp)) {
                Text(text = "录入学生信息:", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
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
                Spacer(modifier = Modifier.height(2.dp))
            } else if(netError){
                Text(text = "服务器请求失败", color = errorColor)
                Spacer(modifier = Modifier.height(2.dp))
            }
            else {
                Spacer(modifier = Modifier.height(25.dp))
            }
            Button(
                onClick = {
                    buttonText="向服务器发送信息中..."
                    var input = true
                    r1 = false
                    r2 = false
                    r3 = false
                    var a = 0L
                    var b = ""
                    var c = 0.0
                    var d = ""
                    try {
                        a = stuNum.toLong()
                        d=(a/100).toString()
                    } catch (e: Exception) {
                        input = false
                        r1 = true
                        buttonText="确认录入信息"
                    }
                    try {
                        if (name != "") {
                            b = name
                        }
                        else {
                            input = false
                            r2 = true
                        }
                    } catch (e: Exception) {
                        input = false
                        r2 = true
                        buttonText="确认录入信息"
                    }
                    try {
                        c = point.toDouble()
                    } catch (e: Exception) {
                        input = false
                        r3 = true
                        buttonText="确认录入信息"
                    }
                    if (input) {
                        val t = Thread{
                            if (Sever.AddAStu(d,a,b,c).equals("Success")){
                                stuNum = ""
                                name = ""
                                point = ""
                                netError = false
                                isTure = true
                            }else {
                                netError = true
                            }
                            buttonText="确认录入信息"
                        }
                        t.start()
                    }
                }, modifier = Modifier.size(280.dp, 55.dp),
                // colors = ButtonDefaults.buttonColors(containerColor = primary, contentColor = Color.White)
                shape = AbsoluteRoundedCornerShape(5.dp)
            ) {
                Text(buttonText)
            }
            Spacer(modifier = Modifier.height(35.dp))
        }
    } else if (isTure) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "录入成功", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(5.dp))
            Row {
                Button(onClick = { isTure = false }) {
                    Text(text = "继续录入")
                }
                Spacer(modifier = Modifier.width(5.dp))
                Button(
                    onClick = { screenNum(0) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
                    border = BorderStroke(1.dp, borderColor)
                ) {
                    Text(text = "返回列表")
                }
            }
            Spacer(modifier = Modifier.height(35.dp))
        }
    }
}
