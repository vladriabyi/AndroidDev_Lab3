package com.example.textsizeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity

class MainActivity : FragmentActivity(), InputFragment.OnInputListener, ResultFragment.OnCancelListener {
    private lateinit var inputFragment: InputFragment
    private lateinit var resultFragment: ResultFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        resultFragment.updateText(text, size)
        
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, resultFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onCancel() {
        supportFragmentManager.popBackStack()
    }
}