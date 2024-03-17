package com.example.textbrushtool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
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
                .size(Dp(50F))
                .padding(Dp(10f))
        )
    }

    Box(contentAlignment = Alignment.TopEnd) {
        Image(
            painterResource(R.drawable.edit),
            contentDescription = "Draw",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(Dp(50F))
                .padding(Dp(10f))
        )
    }
}

@Preview(showBackground = false)
@Composable
fun DrawToolPreview() {
    TextBrushToolTheme {
        DrawTool()
    }
}