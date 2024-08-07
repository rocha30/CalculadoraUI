package com.example.calculadoraapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var txt1: TextView
    private lateinit var txt2: TextView
    private var input = ""
    private var operator = ""
    private var value1 = Double.NaN
    private var value2: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txt1 = findViewById(R.id.txt_1)
        txt2 = findViewById(R.id.txt_2)

        setNumericButtonListeners()
        setOperatorButtonListeners()
    }

    private fun setNumericButtonListeners() {
        val numericButtons = arrayOf(
            R.id.bot_0, R.id.bot_1, R.id.bot_2, R.id.bot_3,
            R.id.bot_4, R.id.bot_5, R.id.bot_6, R.id.bot_7,
            R.id.bot_8, R.id.bot_9, R.id.bot_punto
        )

        val listener = View.OnClickListener { v ->
            val b = v as Button
            input += b.text.toString()
            txt1.text = input
        }

        for (id in numericButtons) {
            findViewById<Button>(id).setOnClickListener(listener)
        }
    }

    private fun setOperatorButtonListeners() {
        val operatorButtons = arrayOf(
            R.id.bot_mas, R.id.bot_menos, R.id.bot_mult, R.id.bot_div, R.id.bot_igual
        )

        val listener = View.OnClickListener { v ->
            val b = v as Button
            if (!value1.isNaN()) {
                value2 = input.toDouble()
                compute()
                txt2.text = "$value1 $operator $value2 = $value1"
            } else {
                value1 = input.toDouble()
            }
            operator = b.text.toString()
            input = ""
        }

        for (id in operatorButtons) {
            findViewById<Button>(id).setOnClickListener(listener)
        }

        findViewById<Button>(R.id.button).setOnClickListener {
            value1 = Double.NaN
            value2 = Double.NaN
            input = ""
            operator = ""
            txt1.text = ""
            txt2.text = ""
        }
    }

    private fun compute() {
        when (operator) {
            "+" -> value1 += value2
            "-" -> value1 -= value2
            "*" -> value1 *= value2
            "/" -> value1 /= value2
        }
    }
}


