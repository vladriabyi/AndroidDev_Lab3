package com.example.textsizeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.fragment.app.Fragment

class InputFragment : Fragment() {
    private var inputListener: OnInputListener? = null

    interface OnInputListener {
        fun onInputSubmitted(text: String, size: Int)
    }

    fun setOnInputListener(listener: OnInputListener) {
        inputListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        InputScreen(
                            onInputSubmitted = { text, size ->
                                inputListener?.onInputSubmitted(text, size)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InputScreen(onInputSubmitted: (String, Int) -> Unit) {
    var text by remember { mutableStateOf("") }
    var selectedSize by remember { mutableStateOf(16) }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Попередження") },
            text = { Text("Будь ласка, введіть текст та виберіть розмір шрифту") },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("OK")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Enter text") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Radio buttons for text size selection
        Column {
            listOf(16, 24, 32).forEach { size ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    RadioButton(
                        selected = selectedSize == size,
                        onClick = { selectedSize = size }
                    )
                    Text(
                        text = "${size}sp",
                        fontSize = size.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (text.isBlank()) {
                    showDialog = true
                } else {
                    onInputSubmitted(text, selectedSize)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("OK")
        }
    }
} 