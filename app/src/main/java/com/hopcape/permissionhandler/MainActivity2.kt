package com.hopcape.permissionhandler

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity2: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, ExampleFragment())
            .addToBackStack(null)
            .commit()
    }
}