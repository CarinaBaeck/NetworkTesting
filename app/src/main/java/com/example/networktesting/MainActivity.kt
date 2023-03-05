package com.example.networktesting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.net.Socket

class MainActivity : AppCompatActivity() {

    lateinit var matrNr: EditText //lateinit: value does not need to be assigned yet
    lateinit var button: Button
    lateinit var responseField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        matrNr = findViewById(R.id.matrNr)
        responseField = findViewById(R.id.responseField)
        button = findViewById(R.id.button)

        button.setOnClickListener(){
            sendToServer(matrNr.toString())
        }
    }
    private fun sendToServer(matrNr: String){
        Thread(Runnable{
            try {
                val socket = Socket("se2-isys.aau.at", 53212)
                val outputStream = socket.getOutputStream()

                //write matrNr to byte array and send to server
                val ByteArrayOutputStream = ByteArrayOutputStream()
                ByteArrayOutputStream.write(matrNr.toByteArray())
                ByteArrayOutputStream.writeTo(outputStream)

                //read response from server
                val inputStream = socket.getInputStream()
                val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                val response = bufferedReader.readLine()

                //update UI with response
                runOnUiThread {
                    responseField.setText(response)
                }

                socket.close()
            } catch (e: Exception){
                "Error occurred"
            }

        }).start()
    }
}