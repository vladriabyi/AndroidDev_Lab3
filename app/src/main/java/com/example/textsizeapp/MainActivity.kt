package com.example.textsizeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TextSizeApp()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TextSizeApp() {
    var text by remember { mutableStateOf("") }
    var displayedText by remember { mutableStateOf("") }
    var selectedSize by remember { mutableStateOf(16.sp) }

    val sizes = listOf(16.sp, 24.sp, 32.sp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Поле введення
        BasicTextField(
            value = text,
            onValueChange = { text = it },
            textStyle = TextStyle(fontSize = 18.sp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .border(1.dp, Color.Gray)
                .padding(8.dp)
        )

        // Радіокнопки для вибору розміру шрифту
        sizes.forEach { size ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedSize == size,
                    onClick = { selectedSize = size }
                )
                Text(text = "${size.value.toInt()}sp", fontSize = size)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопки OK і Cancel
        Row {
            Button(onClick = { displayedText = text }) {
                Text("OK")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { text = ""; displayedText = "" }) {
                Text("Cancel")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Відображений текст з обраним розміром (попередній вигляд)
        Text(
            text = displayedText,
            fontSize = selectedSize
        )
    }
}