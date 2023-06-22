package com.example.testovoe15

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import com.bumptech.glide.Glide

class ConverterActivity : AppCompatActivity() {

    private lateinit var imageViewFon2: ImageView
    private lateinit var textViewConverter: TextView
    private lateinit var editTextAmount: EditText
    private lateinit var spinnerFromCurrency: Spinner
    private lateinit var spinnerToCurrency: Spinner
    private lateinit var buttonConvert: AppCompatButton
    private lateinit var textViewResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_converter)

        imageViewFon2 = findViewById(R.id.imageViewFon2)
        textViewConverter = findViewById(R.id.textViewAdvice)
        editTextAmount = findViewById(R.id.editTextAmount)
        spinnerFromCurrency = findViewById(R.id.spinnerFromCurrency)
        spinnerToCurrency = findViewById(R.id.spinnerToCurrency)
        buttonConvert = findViewById(R.id.buttonConvert)
        textViewResult = findViewById(R.id.textViewResult)

        val currencies = resources.getStringArray(R.array.currencies)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFromCurrency.adapter = adapter
        spinnerToCurrency.adapter = adapter

        Glide.with(this)
            .load("http://135.181.248.237/15/fon2.png")
            .into(imageViewFon2)

        spinnerFromCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Обработка выбора исходной валюты
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Обработка отсутствия выбора исходной валюты
            }
        }
        spinnerToCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Обработка выбора целевой валюты
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Обработка отсутствия выбора целевой валюты
            }
        }
        buttonConvert.setOnClickListener {
            val amount = editTextAmount.text.toString().toDoubleOrNull()
            if (amount != null) {
                val fromCurrency = spinnerFromCurrency.selectedItem.toString()
                val toCurrency = spinnerToCurrency.selectedItem.toString()
                val result = convertCurrency(amount, fromCurrency, toCurrency)
                textViewResult.text = "$result"
            } else {
                textViewResult.text = "Invalid amount"
            }
        }
    }
    private fun convertCurrency(amount: Double, fromCurrency: String, toCurrency: String): Double {

        val currencyValues = mapOf(
            "BTC" to 30000.0,
            "ETH" to 2000.0,
            "XRP" to 0.5,
            "LTC" to 100.0,
            "BCH" to 150.0,
            "ADA" to 0.5
        )

        val fromValue = currencyValues[fromCurrency] ?: 0.0
        val toValue = currencyValues[toCurrency] ?: 0.0

        return amount * (toValue / fromValue)
    }
}