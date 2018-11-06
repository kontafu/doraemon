package com.android.doraemon.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.doraemon.onClick
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.onClick { }
    }
}
