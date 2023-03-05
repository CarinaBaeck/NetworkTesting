package com.example.networktesting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    lateinit var matrNr: EditText //lateinit: value does not need to be assigned yet
    lateinit var button: Button
    lateinit var response: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        matrNr = findViewById(R.id.matrNr)
        response = findViewById(R.id.response)
        button = findViewById(R.id.button)

        button.setOnClickListener(){
            sendToServer(matrNr.toString())
        }
    }
    private fun sendToServer(matrNr: String){
        Thread(Runnable{


        })
    }
}