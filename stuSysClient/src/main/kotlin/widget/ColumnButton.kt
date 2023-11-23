package widget

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import define.primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun columnButton(num: Int, nowNum: Int, screenNum: (Int) -> Unit, imageVector: ImageVector,) {
    var click by remember { mutableStateOf(false) }
    if (num == nowNum) click = true
    else click = false
    val backgroundColor by animateColorAsState(
        targetValue = if (click) primary else Color.Transparent,
        label = "",
        animationSpec = tween(500)
    )
    val contentColor by animateColorAsState(
        targetValue = if (click) Color.White else Color.Black,
        label = "",
        animationSpec = tween(500)
    )
    Card(
        modifier = Modifier.size(50.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(contentColor = contentColor, containerColor = backgroundColor),
    ) {
        Column(
            Modifier.fillMaxSize().clickable(onClick = { screenNum(num) }),
            Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                modifier = Modifier.size(25.dp),
            )
        }
    }
}