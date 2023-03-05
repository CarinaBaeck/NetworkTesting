package com.example.networktesting

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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
            val matrNrString = matrNr.text.toString()
            Thread(Runnable {
                sendToServer(matrNrString)
            }).start()

        }
        buttonBerechnung.setOnClickListener() {
            Thread(Runnable {
                val matrNrCharArray = matrNr.text.toString().toCharArray()
                findDigitsWithCommonFactor(matrNrCharArray)
            }).start()
        }
    }

    fun sendToServer(matrNr: String) {
        fun run() {
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

                runOnUiThread {
                    responseField.setText(response)
                }

                socket.close()

            } catch (e: Exception) {
                "Error occurred"
            }
        }
    }

    fun gcd(a: Int, b: Int): Int{
        return if(b == 0) a else gcd(b, a % b)
    }
    private fun findDigitsWithCommonFactor(nr: CharArray) {
        for(i in 0 until nr.size-1){
            for(j in i +1 until nr.size){
                val digit1 = nr[i].toString().toInt()
                val digit2 = nr[j].toString().toInt()

                if(gcd(digit1, digit2)>1){
                    runOnUiThread {
                        responseField.setText("Indexes $i und $j")
                    }
                }
                else{
                    runOnUiThread {
                        responseField.setText("No digits found")
                    }
                }
            }
        }
    }
}