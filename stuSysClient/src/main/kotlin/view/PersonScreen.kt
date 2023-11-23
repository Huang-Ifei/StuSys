package view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sever.dao.StudentDAO
import define.borderColor
import sever.Student
import widget.headCard
import widget.outPutCard

@Composable
fun personScreen(screenNum: (Int) -> Unit, student: Student, studentReturn: (Student) -> Unit) {
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
                    studentReturn(StudentDAO().getStudent(student.stuNum))
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
