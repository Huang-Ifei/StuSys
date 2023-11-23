package widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun headCard(){
    Card(
        modifier = Modifier.fillMaxWidth().height(55.dp).padding(horizontal = 10.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = AbsoluteRoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().background(color = Color(244, 244, 246)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "学号", modifier = Modifier.width(150.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "姓名", modifier = Modifier.width(100.dp), textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "成绩", modifier = Modifier.width(100.dp), textAlign = TextAlign.End)
        }
    }
}