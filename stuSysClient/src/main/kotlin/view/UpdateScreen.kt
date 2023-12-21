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
import sever.Student

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun updateScreen(screenNum: (Int) -> Unit, student: Student) {
    var isTure by remember { mutableStateOf(false) }
    var id by remember { mutableStateOf(student.id.toString()) }
    var name by remember { mutableStateOf(student.name) }
    var sex by remember { mutableStateOf(student.sex) }
    var address by remember { mutableStateOf(student.address) }
    var netError by remember { mutableStateOf(false) }
    var r1 by remember { mutableStateOf(false) }
    var r2 by remember { mutableStateOf(false) }
    var r3 by remember { mutableStateOf(false) }
    var buttonText by remember { mutableStateOf("确认更新信息") }
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
                value = id,
                onValueChange = { id = it;r1 = false },
                isError = r1,
                modifier = Modifier.width(280.dp),
                singleLine = true,
                textStyle = TextStyle(fontSize = 16.sp),
                readOnly = true
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "输入姓名：", fontSize = 14.sp, modifier = Modifier.width(280.dp))
            Row {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it;r2 = false },
                    isError = r2,
                    modifier = Modifier.width(200.dp),
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 16.sp)
                )
                Spacer(Modifier.width(10.dp))
                OutlinedTextField(
                    value = sex,
                    onValueChange = { sex = it;r3 = false },
                    isError = r3,
                    modifier = Modifier.width(70.dp),
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 16.sp)
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "输入地址：", fontSize = 14.sp, modifier = Modifier.width(280.dp))
            OutlinedTextField(
                value = address,
                onValueChange = { address = it},
                modifier = Modifier.width(280.dp),
                singleLine = true,
                textStyle = TextStyle(fontSize = 16.sp)
            )
            if (r1 || r2 || r3) {
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
                    var d = ""
                    try {
                        a = id.toLong()
                        d= (a/100).toString()
                    } catch (e: Exception) {
                        input = false
                        r1 = true
                        buttonText="确认更新信息"
                    }
                    try {
                        if (name != "") b = name
                        else {
                            input = false
                            r2 = true
                            buttonText="确认更新信息"
                        }
                    } catch (e: Exception) {
                        input = false
                        r2 = true
                        buttonText="确认更新信息"
                    }
                    if(!sex.equals("男")&&!sex.equals("女")){
                        input = false
                        r3 = true
                        buttonText="确认更新信息"
                    }
                    if (input) {
                        val t = Thread{
                            if (Sever.changeAStu(d,a,b,sex,address).equals("Success")){
                                id = ""
                                name = ""
                                sex = ""
                                netError = false
                                isTure = true
                            }else {
                                netError = true
                                buttonText="确认更新信息"
                            }
                            buttonText="确认录入信息"
                        }
                        t.start()
                    }
                }, modifier = Modifier.size(280.dp, 55.dp),
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