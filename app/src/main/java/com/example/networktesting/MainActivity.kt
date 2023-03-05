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
    lateinit var response: EditText
    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        matrNr = findViewById(R.id.matrNr)
        response = findViewById(R.id.response)
        textView = findViewById(R.id.textView)
        button = findViewById(R.id.button)

        button.setOnClickListener(){
            sendToServer(matrNr.toString())
        }
    }
    private fun sendToServer(matrNr: String){
        Thread(Runnable{
            val socket = Socket("se2-isys.aau.at",53212)
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
            runOnUiThread{
                textView.setText(response)
            }

            socket.close()
        }).start()
    }
}