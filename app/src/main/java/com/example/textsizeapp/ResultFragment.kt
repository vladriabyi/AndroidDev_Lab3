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
import androidx.fragment.app.Fragment

class ResultFragment : Fragment() {
    private var cancelListener: OnCancelListener? = null
    private var displayText: String = ""
    private var textSize: Int = 16

    interface OnCancelListener {
        fun onCancel()
    }

    fun setOnCancelListener(listener: OnCancelListener) {
        cancelListener = listener
    }

    fun updateText(text: String, size: Int) {
        displayText = text
        textSize = size
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
                        ResultScreen(
                            text = displayText,
                            textSize = textSize,
                            onCancel = { cancelListener?.onCancel() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ResultScreen(text: String, textSize: Int, onCancel: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            fontSize = textSize.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancel")
        }
    }
} 