package com.example.testovoe15

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

class HelpActivity : AppCompatActivity() {

    private lateinit var imageViewFon2: ImageView
    private lateinit var editTextAsk: EditText
    private lateinit var imageViewSend: ImageView
    private lateinit var textViewServerResponses: TextView

    private val url = "http://135.181.248.237/15/cryptocurrency_response.php"

    private var allText: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        editTextAsk = findViewById(R.id.editTextAsk)
        imageViewSend = findViewById(R.id.imageViewSend)
        textViewServerResponses = findViewById(R.id.textViewServerResponses)
        imageViewFon2 = findViewById(R.id.imageViewFon2)

        Glide.with(this)
            .load("http://135.181.248.237/15/fon2.png")
            .into(imageViewFon2)

        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        textViewServerResponses.text = sharedPreferences.getString("allText","")

        imageViewSend.setOnClickListener {
            val question = editTextAsk.text.toString().trim()
            if (question.isNotEmpty()) {
                ask(question)
            }
        }
    }
    private fun ask(question: String) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = URL("$url").readText()
                withContext(Dispatchers.Main) {
                    if (response.isNotEmpty()) {
                        val jsonResponse = JSONObject(response)
                        val answer = jsonResponse.getString("response")
                        val message = "$question\n\nAnswer: $answer\n\n"
                        val spannable: Spannable = SpannableString(message)

                        spannable.setSpan(
                            ForegroundColorSpan(Color.WHITE),
                            question.length,
                            (message).length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        val newSpannable = SpannableStringBuilder(spannable)
                        newSpannable.append(textViewServerResponses.text)
                        textViewServerResponses.text = newSpannable
                        allText = textViewServerResponses.text.toString()
                        val editor = getSharedPreferences("my_preferences", MODE_PRIVATE).edit()
                        editor.putString("allText", allText)
                        editor.apply()
                        editTextAsk.text.clear()
                    } else {
                        textViewServerResponses.text = "The response is being processed"
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}