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
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.textbrushtool.ui.theme.TextBrushToolTheme

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
        mutableStateListOf(Pair(Offset(0.0F, 0.0F), Offset(0.0F, 0.0F)))
    }
    var currentIndex by remember {
        mutableIntStateOf(0)
    }
    var previousTimeMillis by remember {
        mutableLongStateOf(0L)
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    change.consumeAllChanges()
                    if (previousTimeMillis != change.previousUptimeMillis) {
                        positions.add(currentIndex, Pair(change.position, change.position))
                        previousTimeMillis = change.uptimeMillis
                    } else {
                        positions.add(
                            currentIndex,
                            Pair(positions[currentIndex].first, change.position)
                        )
                        currentIndex++
                    }
                }
            }
    ) {
        positions.forEach {
            drawLine(
                start = it.first,
                end = it.second,
                color = Color.White,
                strokeWidth = 5.dp.toPx()
            )
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