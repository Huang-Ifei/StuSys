import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectDragGestures
import view.mainScreen
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.minimumInteractiveComponentSize
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.*
import define.borderColor
import define.colorScheme
import java.awt.Dimension
import kotlin.math.roundToInt
import kotlin.math.roundToLong


fun main() = application {
    var windowOffset by remember { mutableStateOf(Offset.Zero) }
    var windowWidth by remember { mutableStateOf(900.dp) }
    var windowHeight by remember { mutableStateOf(700.dp) }
    Window(
        onCloseRequest = ::exitApplication,
        title = "StuSystem",
        state = rememberWindowState(
            position = WindowPosition(Alignment.Center),
            size = DpSize(windowWidth, windowHeight)
        ),
    ) {
        window.minimumSize = Dimension(800, 600)
        MaterialTheme(colorScheme = colorScheme) {
            Surface(
                color = Color.White,
                modifier = Modifier.fillMaxSize()
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth().pointerInput(Unit) {
                            detectDragGestures(onDrag = { _: PointerInputChange, dragAmount: Offset ->
                                windowOffset += dragAmount
                                window.setLocation(
                                    (window.x + windowOffset.x).roundToInt(),
                                    (window.y + windowOffset.y).roundToInt()
                                )
                            })
                        },
                    ) {
                        Column {
                            Spacer(modifier = Modifier.height(15.dp))
                            Box {
                                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = "华北科技学院学生信息管理教务系统",
                                        fontSize = 22.sp,
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                    mainScreen()
                }
            }
        }
    }
}
