package com.example.textsizeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : FragmentActivity(), InputFragment.OnInputListener, ResultFragment.OnCancelListener {
    private lateinit var inputFragment: InputFragment
    private lateinit var resultFragment: ResultFragment
    private lateinit var database: TextEntryDatabase
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = TextEntryDatabase(this)
        inputFragment = InputFragment()
        resultFragment = ResultFragment()

        inputFragment.setOnInputListener(this)
        resultFragment.setOnCancelListener(this)

        // Add input fragment initially
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, inputFragment)
            .commit()
    }

    override fun onInputSubmitted(text: String, size: Int) {
        coroutineScope.launch {
            // Save to database
            val id = database.addEntry(text, size)
            if (id != -1L) {
                Toast.makeText(this@MainActivity, "Entry saved successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "Failed to save entry", Toast.LENGTH_SHORT).show()
            }

            resultFragment.updateText(text, size)
            
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, resultFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onCancel() {
        supportFragmentManager.popBackStack()
    }

    fun openHistory() {
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
    }
}