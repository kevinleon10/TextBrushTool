package com.example.textbrushtool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.textbrushtool.ui.theme.TextBrushToolTheme
import kotlin.math.atan2

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TextBrushToolTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DrawTool()
                }
            }
        }
    }
}

@Composable
fun DrawTool() {
    var startDrawing by remember { mutableStateOf(false) }

    Image(
        painterResource(R.drawable.background),
        contentDescription = "Background",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
    )

    Box(contentAlignment = Alignment.TopStart) {
        Image(
            painterResource(R.drawable.close),
            contentDescription = "Discard",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .padding(10.dp)
        )
    }

    Box(contentAlignment = Alignment.TopEnd) {
        Image(
            painterResource(R.drawable.edit),
            contentDescription = "Draw",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .padding(10.dp)
                .clickable {
                    startDrawing = true
                }
        )
    }

    if (startDrawing) {
        DrawText()
    }
}

@Composable
fun DrawText() {
    val positions = remember {
        mutableStateListOf(Pair(Offset(0.0F, 0.0F), ""))
    }
    var previousTimeMillis by remember {
        mutableLongStateOf(0L)
    }
    var currentIndex by remember {
        mutableIntStateOf(1)
    }
    val textToDraw = "TEXT BRUSH"
    val textMeasurer = rememberTextMeasurer()
    remember(textToDraw) {
        textMeasurer.measure(textToDraw)
    }
    val style = TextStyle(
        fontSize = 50.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
    )

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                // It only registers last position of the tap
                detectDragGestures { change, _ ->
                    change.consumeAllChanges()
                    if (previousTimeMillis != change.previousUptimeMillis) {
                        previousTimeMillis = change.uptimeMillis
                    } else {
                        positions.add(
                            Pair(
                                change.position,
                                textToDraw[currentIndex % textToDraw.length].toString()
                            )
                        )
                        currentIndex++
                    }
                }
            }
    ) {
        // It draws previous taps and new ones as well
        for (i in 1 until positions.size) {
            // Angle calculation
            val angle = atan2(
                positions[i].first.y - (size.height / 2),
                positions[i].first.x - (size.width / 2)
            )
            rotate(
                degrees = angle * 10, pivot = positions[i].first
            ) {
                drawText(
                    textMeasurer = textMeasurer,
                    text = positions[i].second,
                    style = style,
                    maxLines = 1,
                    topLeft = positions[i].first
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun DrawToolPreview() {
    TextBrushToolTheme {
        DrawTool()
    }
}