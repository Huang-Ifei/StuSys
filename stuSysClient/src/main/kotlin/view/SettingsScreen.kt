package view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import define.borderColor
import java.io.FileOutputStream
import java.io.FileReader


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun settingsScreen() {
    var IP by remember { mutableStateOf(9766) }
    var host by remember { mutableStateOf("") }

    try {
        val fis = FileReader("src\\main\\java\\sever\\IP")
        val ip =  fis.readText()
        val list =  ip.lines()
        IP=list[0].toInt()
        host=list[1]
        fis.close()
    }catch (e:Exception){
        val fos = FileOutputStream("src\\main\\java\\sever\\IP",false)
        fos.write((IP.toString()+"\n"+host).toByteArray())
        fos.close()
    }
    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "数据库基础设置：", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(5.dp))
        Divider(color = borderColor)
        Spacer(modifier = Modifier.height(20.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "设置端口：", fontWeight = FontWeight.SemiBold)
            OutlinedTextField(
                value = IP.toString(),
                onValueChange = {
                    try {
                        IP=it.toInt()
                        val fos = FileOutputStream("src\\main\\java\\sever\\IP",false)
                        fos.write((IP.toString()+"\n"+host).toByteArray())
                        fos.close()
                        val fis = FileReader("src\\main\\java\\sever\\IP")
                        val ip =  fis.readText()
                        val list =  ip.lines()
                        IP=list[0].toInt()
                        host=list[1]
                        fis.close()
                    }catch (e:Exception){
                        val fos = FileOutputStream("src\\main\\java\\sever\\IP")
                        fos.write((IP.toString()).toByteArray())
                    }
                },
                textStyle = TextStyle(fontSize = 16.sp),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "设置host地址：", fontWeight = FontWeight.SemiBold)
            OutlinedTextField(
                value = host,
                onValueChange = {
                    try {
                        host=it
                        val fos = FileOutputStream("src\\main\\java\\sever\\IP",false)
                        fos.write((IP.toString()+"\n"+host).toByteArray())
                        fos.close()
                        val fis = FileReader("src\\main\\java\\sever\\IP")
                        val ip =  fis.readText()
                        val list =  ip.lines()
                        IP=list[0].toInt()
                        host=list[1]
                        fis.close()
                    }catch (e:Exception){
                        val fos = FileOutputStream("src\\main\\java\\sever\\IP")
                        fos.write((IP.toString()+"\n"+host).toByteArray())
                    }
                },
                textStyle = TextStyle(fontSize = 16.sp),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Divider(color = borderColor)
    }
}