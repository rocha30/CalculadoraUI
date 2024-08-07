package com.example.calculadoraapp




import Calculadora
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var entradaExpresion: EditText
    private lateinit var textoResultado: TextView
    private lateinit var calculadora: Calculadora

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        entradaExpresion = findViewById(R.id.entradaExpresion)
        textoResultado = findViewById(R.id.textoResultado)
        val botonEvaluar: Button = findViewById(R.id.botonEvaluar)

        calculadora = Calculadora()

        botonEvaluar.setOnClickListener {
            val expresion = entradaExpresion.text.toString()
            try {
                val resultado = calculadora.evaluate(expresion)
                textoResultado.text = "Resultado: $resultado"
            } catch (e: Exception) {
                textoResultado.text = "Error: ${e.message}"
            }
        }
    }
}


