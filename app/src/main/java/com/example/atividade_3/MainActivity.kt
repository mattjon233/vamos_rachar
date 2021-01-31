package com.example.atividade_3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import java.text.DecimalFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var mTTS: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val reais: EditText = findViewById(R.id.reais)
        val pessoas: EditText = findViewById(R.id.pessoas)
        val resultado: TextView = findViewById(R.id.resultado)
        val btnShare: Button = findViewById(R.id.buttonShare)
        val btnVoice: Button = findViewById(R.id.buttonVoice)

        mTTS = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
            if (status != TextToSpeech.ERROR){
                mTTS.language = Locale.US
            }
        })

        reais.setText("50.00")
        pessoas.setText("2")
        resultado.text = "25.00"

        reais.doAfterTextChanged{
            try
            {
                val valor: Double = reais.text.toString().toDouble()
                val qtd: Int = pessoas.text.toString().toInt()

                val decimal = DecimalFormat("#.00")
                resultado.text = decimal.format(valor / qtd).toString()
            } catch (e: Exception) {
                resultado.text = "0.00"
            }
        }

        pessoas.doAfterTextChanged{
            try
            {
                val valor: Double = reais.text.toString().toDouble()
                val qtd: Int = pessoas.text.toString().toInt()

                val decimal = DecimalFormat("#.00")
                resultado.text = decimal.format(valor / qtd).toString()
            } catch (e: Exception) {
                resultado.text = "0.00"
            }
        }

        btnShare.setOnClickListener{
            val share = Intent(Intent.ACTION_SEND)
            share.type = "text/plain"
            val text = "Você deve pagar: " + resultado.text + " (Total da conta: " + reais.text + " para " + pessoas.text + " pessoas" + ")"
            share.putExtra(Intent.EXTRA_TEXT, text)
            startActivity(Intent.createChooser(share, "Compartilhe a conta!"))
        }

        btnVoice.setOnClickListener{
            mTTS.speak(resultado.text.toString(), TextToSpeech.QUEUE_FLUSH, null, "")
        }

    }
}