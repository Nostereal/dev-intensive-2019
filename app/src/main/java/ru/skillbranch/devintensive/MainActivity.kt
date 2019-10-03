package ru.skillbranch.devintensive

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.extensions.hideKeyboard
import ru.skillbranch.devintensive.models.Bender

class MainActivity : AppCompatActivity() {

    private lateinit var bender: Bender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val status = savedInstanceState?.getString("STATUS") ?: Bender.Status.NORMAL.name
        val question = savedInstanceState?.getString("QUESTION") ?: Bender.Question.NAME.name

        bender = Bender(Bender.Status.valueOf(status), Bender.Question.valueOf(question))
        tv_text.text = bender.askQuestion()

        Log.d("M_MainActivity", "onCreate $status, $question")

        val (red, green, blue) = bender.status.color
        iv_bender.setColorFilter(Color.rgb(red, green, blue), PorterDuff.Mode.MULTIPLY)


        iv_send.setOnClickListener {
            onMessageSent()
        }

        et_message.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onMessageSent()
            }
            return@setOnEditorActionListener true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("STATUS", bender.status.name)
        outState.putString("QUESTION", bender.question.name)
        Log.d(
            "M_MainActivity",
            "onSaveInstanceState ${bender.status.name}, ${bender.question.name}"
        )
    }

    private fun onMessageSent() {
        val (phrase, color) = bender.listenAnswer(et_message.text.toString())
        Log.d("M_MainActivity", "${et_message.text}, ${et_message.text.toString() in bender.question.answers}")
        et_message.setText("")
        val (r, g, b) = color
        iv_bender.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
        tv_text.text = phrase

        this.hideKeyboard()
    }
}
