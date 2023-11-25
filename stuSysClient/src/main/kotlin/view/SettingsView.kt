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
import sever.Student
import java.io.FileInputStream
import java.io.FileOutputStream


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun settingsScreen(screenNum: (Int) -> Unit, student: Student, studentReturn: (Student) -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var pIP by remember { mutableStateOf(9766) }
    var rIP by remember { mutableStateOf(9768) }

    try {
        val fis = FileInputStream("src\\main\\java\\sever\\IP")
        val ip =  fis.readBytes().toString()
        val list =  ip.lines()
        pIP=list[0].toInt()
        rIP=list[1].toInt()
        fis.close()
    }catch (e:Exception){
        val fos = FileOutputStream("src\\main\\java\\sever\\IP")
        fos.write((pIP.toString()+"\n"+rIP.toString()).toByteArray())
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
            Text(text = "设置写入端口：", fontWeight = FontWeight.SemiBold)
            OutlinedTextField(
                value = pIP.toString(),
                onValueChange = {
                    try {
                        pIP=it.toInt()
                        val fis = FileInputStream("src\\main\\java\\sever\\IP")
                        val ip =  fis.readBytes().toString()
                        val list =  ip.lines()
                        pIP=list[0].toInt()
                        rIP=list[1].toInt()
                        fis.close()
                    }catch (e:Exception){
                        val fos = FileOutputStream("src\\main\\java\\sever\\IP")
                        fos.write((pIP.toString()+"\n"+rIP.toString()).toByteArray())
                    }
                },
                textStyle = TextStyle(fontSize = 16.sp),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "设置读取端口：", fontWeight = FontWeight.SemiBold)
            OutlinedTextField(
                value = rIP.toString(),
                onValueChange = {
                    try {
                        rIP=it.toInt()
                        val fis = FileInputStream("src\\main\\java\\sever\\IP")
                        val ip =  fis.readBytes().toString()
                        val list =  ip.lines()
                        pIP=list[0].toInt()
                        rIP=list[1].toInt()
                        fis.close()
                    }catch (e:Exception){
                        val fos = FileOutputStream("src\\main\\java\\sever\\IP")
                        fos.write((pIP.toString()+"\n"+rIP.toString()).toByteArray())
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