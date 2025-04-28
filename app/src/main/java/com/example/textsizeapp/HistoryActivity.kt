package com.example.textsizeapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryActivity : ComponentActivity() {
    private lateinit var database: TextEntryDatabase
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = TextEntryDatabase(this)

        setContent {
            var entries by remember { mutableStateOf<List<TextEntry>>(emptyList()) }
            
            LaunchedEffect(Unit) {
                entries = database.getAllEntries()
            }

            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HistoryScreen(
                        entries = entries,
                        onBack = { finish() },
                        onClearDatabase = {
                            coroutineScope.launch {
                                val success = database.clearDatabase()
                                if (success) {
                                    entries = emptyList()
                                    Toast.makeText(
                                        this@HistoryActivity,
                                        "Історія очищена",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        this@HistoryActivity,
                                        "Помилка при очищенні бази даних",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HistoryScreen(
    entries: List<TextEntry>,
    onBack: () -> Unit,
    onClearDatabase: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Історія записів",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (entries.isEmpty()) {
            Text(
                text = "Записи не знайдено",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(entries) { entry ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = entry.text,
                                fontSize = entry.size.sp,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = "Розмір: ${entry.size}sp",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = "Додано: ${entry.timestamp}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onBack,
                modifier = Modifier.weight(1f)
            ) {
                Text("Назад")
            }

            Button(
                onClick = onClearDatabase,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Очистити історію")
            }
        }
    }
} 