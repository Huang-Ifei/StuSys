package view

import Sever.getTestPoints
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ncist.stu.sever.TestPointList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import widget.headTestCard
import widget.outPutTestCard
import com.ncist.stu.sever.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun gradePointManagement() {
    var show by remember { mutableStateOf(false) }
    var testList by remember { mutableStateOf(Sever.getBasicClasses()) }
    var listName by remember { mutableStateOf("选择成绩表") }
    var pointList by remember { mutableStateOf(getTestPoints(listName)) }
    var new by remember { mutableStateOf(false) }
    var deleteButtonText by remember { mutableStateOf("删除此测试表") }
    //协程赋值
    LaunchedEffect(Unit) {
        testList = withContext(Dispatchers.IO) {
            Sever.getTests()
        }
    }
    Row {
        Column {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "测试表:",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 20.dp)
            )
            Button(
                onClick = {
                    if (!listName.equals("选择成绩表") && !listName.equals("添加一个新测试") && !listName.equals("")) {
                        val t = Thread {
                            Sever.saveTestPoint(pointList, listName)
                            Thread.sleep(500)
                            pointList = TestPointList()
                            pointList = getTestPoints(listName)
                            testList = Sever.getTests()
                            show = false
                            show = true
                        }
                        t.start()
                    }
                }, modifier = Modifier.size(185.dp, 64.dp).padding(start = 10.dp, end = 5.dp, top = 7.dp),
                shape = AbsoluteRoundedCornerShape(5.dp)
            ) {
                Text("保存此测试表")
            }
            if (new) {
                OutlinedTextField(
                    value = listName,
                    onValueChange = { listName = it },
                    label = { Text("测试名") },
                    modifier = Modifier.padding(start = 10.dp).size(170.dp, 64.dp),
                    singleLine = true
                )

            } else {
                Button(
                    onClick = {
                              if(!deleteButtonText.equals("确认删除吗？")){
                                  deleteButtonText="确认删除吗？"
                              }else{
                                  val t = Thread{
                                      if (!listName.equals("IO错误") && !listName.equals("等待中..")&&!listName.equals("")) {
                                          deleteButtonText=Sever.deleteTest(listName)
                                      }else{
                                          deleteButtonText="请删除正确的表"
                                      }
                                      Thread.sleep(300)
                                      deleteButtonText="删除此测试表"
                                      testList=Sever.getTests()
                                  }
                                  t.start()
                              }
                    },
                    modifier = Modifier.size(185.dp, 64.dp).padding(start = 10.dp, end = 5.dp, top = 7.dp),
                    shape = AbsoluteRoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(define.Surface),
                    border = BorderStroke(1.dp, define.borderColor)
                ) {
                    Text(deleteButtonText, color = Color.Black)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Divider(modifier = Modifier.width(190.dp), color = define.borderColor)
            LazyColumn(
                modifier = Modifier.width(180.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    ElevatedFilterChip(
                        selected = (listName == "添加一个新测试"),
                        label = { Text("添加一个新测试") },
                        onClick = {
                            listName = "添加一个新测试"
                            pointList = TestPointList()
                            listName = ""
                            new = true
                            show = false
                            show = true
                        },
                        border = FilterChipDefaults.filterChipBorder(
                            selectedBorderColor = Color(248, 248, 255),
                        ),
                        elevation = FilterChipDefaults.elevatedFilterChipElevation(0.dp)
                    )
                }
                items(testList.size) {
                    if (testList[it] != null) {
                        ElevatedFilterChip(
                            selected = (listName == testList[it]),
                            label = { Text(testList[it]) },
                            onClick = {
                                if (!testList[it].equals("IO错误") && !testList[it].equals("等待中..")) {
                                    listName = testList[it]
                                    deleteButtonText="删除此测试表"
                                    val t = Thread {
                                        pointList = TestPointList()
                                        pointList = getTestPoints(listName)
                                        new = false
                                        show = false
                                        show = true
                                    }
                                    t.start()
                                }
                            },
                            border = FilterChipDefaults.filterChipBorder(
                                selectedBorderColor = Color(248, 248, 255),
                            ),
                            elevation = FilterChipDefaults.elevatedFilterChipElevation(0.dp)
                        )
                    }
                }
            }
        }
        Divider(modifier = Modifier.fillMaxHeight().width(1.dp), color = define.borderColor)
        Column {
            Spacer(modifier = Modifier.height(10.dp))
            headTestCard()
            LazyColumn {
                if (show) {
                    items(pointList.testPointList.size) {
                        outPutTestCard(pointList.testPointList[it])
                    }
                    item {
                        Spacer(Modifier.height(10.dp))
                        Card(
                            modifier = Modifier.fillMaxWidth().height(55.dp).padding(horizontal = 10.dp).clickable {
                                pointList.add(
                                    listName,
                                    TestPoint(0, 0.0)
                                )
                                show = false
                                show = true
                            },
                            elevation = CardDefaults.cardElevation(0.dp),
                            shape = AbsoluteRoundedCornerShape(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize().background(color = Color(248, 248, 255)),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "添加一条信息", color = Color.Black)
                            }
                        }
                        Spacer(Modifier.height(10.dp))
                    }
                }
            }
        }
        //选择表
    }
}