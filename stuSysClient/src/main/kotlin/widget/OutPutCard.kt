package widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import define.borderColor
import sever.Student
import java.text.DecimalFormat

@Composable
fun outPutCard(student: Student, screenNum: (Int) -> Unit, studentReturn: (Student)->Unit) {
    var df by remember { mutableStateOf(DecimalFormat("000000000000")) }
    Row(
        modifier = Modifier.fillMaxWidth().height(55.dp).clickable { studentReturn(student);screenNum(3) },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = df.format(student.stuNum).toString(), modifier = Modifier.width(150.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = student.name, modifier = Modifier.width(100.dp), textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = student.point.toString(), modifier = Modifier.width(100.dp), textAlign = TextAlign.End)
    }
    Divider(Modifier.padding(horizontal = 12.dp), color = borderColor)
}