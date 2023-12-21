package view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import define.borderColor
import sever.Student
import widget.columnButton

@Composable
fun mainScreen() {
    var screenNum by remember { mutableStateOf(0) }
    var student by remember { mutableStateOf(Student(0, "", "", "")) }
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.width(5.dp))
            Column {
                Spacer(modifier = Modifier.height(5.dp))
                columnButton(0, screenNum, { screenNum = it }, Icons.Default.List)
                Spacer(modifier = Modifier.height(5.dp))
                columnButton(1, screenNum, { screenNum = it }, Icons.Default.Add)
                Spacer(modifier = Modifier.height(5.dp))
                columnButton(2, screenNum, { screenNum = it }, Icons.Default.Search)
                Spacer(modifier = Modifier.height(5.dp))
                columnButton(7, screenNum, { screenNum = it }, Icons.Default.Send)
                Spacer(modifier = Modifier.height(5.dp))
                if (screenNum == 3) columnButton(3, screenNum, { screenNum = it }, Icons.Default.Person)
                if (screenNum == 5) columnButton(5, screenNum, { screenNum = it }, Icons.Default.Edit)
                if (screenNum == 6) columnButton(6, screenNum, { screenNum = it }, Icons.Default.Delete)
                Column(Modifier.fillMaxHeight(), Arrangement.Bottom) {
                    columnButton(4, screenNum, { screenNum = it }, Icons.Default.Settings)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
            Card(
                elevation = CardDefaults.cardElevation(0.dp),
                border = BorderStroke(1.dp, borderColor),
                modifier = Modifier.fillMaxSize().padding(5.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                when (screenNum) {
                    0 -> outputScreen(screenNum = { screenNum = it }, studentReturn = { student = it })
                    1 -> addScreen(screenNum = { screenNum = it })
                    2 -> searchScreen(screenNum = { screenNum = it }, studentReturn = { student = it })
                    3 -> personScreen(
                        screenNum = { screenNum = it },
                        student = student,
                        studentReturn = { student = it })

                    4 -> settingsScreen()
                    5 -> updateScreen(screenNum = { screenNum = it }, student = student)
                    6 -> deleteScreen(screenNum = { screenNum = it }, student = student)
                    7 -> gradePointManagement()
                }
            }
        }
    }
}
