package com.example.networktesting

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.Socket

class MainActivity : AppCompatActivity() {

    lateinit var matrNr: EditText //lateinit: value does not need to be assigned yet
    lateinit var button: Button
    lateinit var responseField: EditText
    lateinit var buttonBerechnung: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        matrNr = findViewById(R.id.matrNr)
        responseField = findViewById(R.id.responseField)
        button = findViewById(R.id.button)
        buttonBerechnung = findViewById(R.id.buttonBerechnung)

        button.setOnClickListener() {
            val matrNr = matrNr.text.toString()
            Thread(Runnable{
                sendToServer(matrNr)
            }).start()

        }
        buttonBerechnung.setOnClickListener() {
            //findCommonFactor(matrNr)
        }
    }

    fun sendToServer(matrNr: String){
        run() {
            try {
                val socket = Socket("se2-isys.aau.at", 53212)
                val outputStream = socket.getOutputStream()

                //write matrNr to byte array and send to server
                //val ByteArrayOutputStream = ByteArrayOutputStream()
                //ByteArrayOutputStream.write(matrNr.toByteArray())
                //ByteArrayOutputStream.writeTo(outputStream)
                val matrNrBytes = matrNr.toByteArray()
                outputStream.write(matrNrBytes)

                //read response from server
                val inputStream = socket.getInputStream()
                val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                val response = bufferedReader.readLine()

                runOnUiThread{
                    responseField.setText(response)
                }

                socket.close()

            } catch (e: Exception) {
                "Error occurred"
            }
        }
    }
}//).start()






    //private fun findCommonFactor(matrNr: Int){
      //  matrNr.toInt()


    //}
