package widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import define.borderColor
import sever.Student
import sever.TestPoint
import sever.TestPointList
import java.text.DecimalFormat
import kotlin.math.absoluteValue

@Composable
fun outPutCard(student: Student, screenNum: (Int) -> Unit, studentReturn: (Student)->Unit) {
    var df by remember { mutableStateOf(DecimalFormat("000000000000")) }
    Row(
        modifier = Modifier.fillMaxWidth().height(55.dp).clickable { studentReturn(student);screenNum(3) },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = df.format(student.id).toString(), modifier = Modifier.width(120.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = student.name, modifier = Modifier.width(100.dp), textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = student.sex, modifier = Modifier.width(30.dp), textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = student.address, modifier = Modifier.width(180.dp), textAlign = TextAlign.Center)
    }
    Divider(Modifier.padding(horizontal = 12.dp), color = borderColor)
}

@Composable
fun outPutTestCard(testPoint: TestPoint) {
    var id by remember { mutableStateOf(testPoint.id.toString()) }
    var point by remember { mutableStateOf(testPoint.point.toString()) }
    Row(
        modifier = Modifier.fillMaxWidth().height(55.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(value = id, onValueChange = { id=(it.toLongOrNull()?:0L).toString();testPoint.id=(it.toLongOrNull()?:0L) }, modifier = Modifier.width(120.dp), singleLine = true)
        Spacer(modifier = Modifier.width(10.dp))
        BasicTextField(value = point, onValueChange = { point=(it.toDoubleOrNull()?:0.0).toString();testPoint.point=(it.toDoubleOrNull()?:0.0) }, modifier = Modifier.width(40.dp), singleLine = true)
    }
    Divider(Modifier.padding(horizontal = 12.dp), color = borderColor)
}

@Composable
fun personTestCard(testPoint: TestPoint){
    Row(
        modifier = Modifier.fillMaxWidth().height(30.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(testPoint.test.toString(), modifier = Modifier.width(180.dp), color = Color.Black, fontSize = 18.sp)
        Spacer(modifier = Modifier.width(10.dp))
        Text(testPoint.point.toString()+"分",  modifier = Modifier.width(60.dp), color = Color.Black, fontSize = 18.sp)
    }
}