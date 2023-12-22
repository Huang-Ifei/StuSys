package view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ncist.stu.sever.Student
import define.borderColor

@Composable
fun deleteScreen(screenNum:(Int)->Unit,student: Student){
    var isDelete by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row (verticalAlignment = Alignment.CenterVertically){
            Text(text = "正在删除：", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
            Text(text = student.id.toString(), fontSize = 22.sp)
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row {
            Button(onClick = {
                val t = Thread{
                    if (Sever.deleteAStu((student.id/100).toString(),student.id,student.name).equals("Success")) isDelete=true
                }
                t.start()
            }) {
                Text(text = "确认删除")
            }
            Spacer(modifier = Modifier.width(5.dp))
            Button(
                onClick = { screenNum(0) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
                border = BorderStroke(1.dp, borderColor)
            ) {
                Text(text = "取消删除")
            }
        }
        if (isDelete)screenNum(0)
        Spacer(modifier = Modifier.height(35.dp))
    }
}