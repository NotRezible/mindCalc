package com.example.mindcalc

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.mindcalc.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    class NumberExample(val firstOperand: Int, val secondOperand: Int, val operand: String)
    private var correctAnswers = 0
    private var wrongAnswers = 0

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    @SuppressLint("SetTextI18n")
    fun btnStart(view: View) {
        binding.answerEditText.text.clear()
        var firstOperand = (10..99).random()
        var secondOperand = (10..99).random()
        val operators = arrayOf('*', '/', '-', '+')
        val operator = operators.random()
        if (operator == '/')
        {
            while(firstOperand  % secondOperand != 0)
            {
                firstOperand = (10..99).random()
                secondOperand = (10..99).random()
            }
        }

        binding.taskTextView.text = "$firstOperand $operator $secondOperand"
        binding.startButton.isEnabled = false
        binding.answerEditText.isEnabled = true
        binding.checkButton.isEnabled = true
        binding.answerEditText.requestFocus()
    }

    fun btnCheck(view: View) {
        try {
            val userAnswer = findViewById<View>(R.id.answerEditText) as EditText
            if (userAnswer.text.toString().isEmpty()) userAnswer.error = "Ответ не может быть пустым!"
            val equationString = binding.taskTextView.text
            val values = equationString.split(" ")
            val firstOperand = values[0].toInt()
            val secondOperand = values[2].toInt()
            val operand = values[1]
            val equation = NumberExample(firstOperand, secondOperand, operand)
            if(checkEquation(equation, userAnswer.text.toString().toInt())){
                correctAnswers++
            } else{
                wrongAnswers++
            }
            binding.answerEditText.isEnabled = false
            binding.startButton.isEnabled = true
            binding.checkButton.isEnabled = false
            updateStatistics()
        }
        catch (ex:Exception){

        }
    }

    private fun checkEquation(example: NumberExample, userAnswer: Int): Boolean{
        var result = 0

        when (example.operand){
            "/" -> result = example.firstOperand / example.secondOperand
            "*" -> result = example.firstOperand * example.secondOperand
            "+" -> result = example.firstOperand + example.secondOperand
            "-" -> result = example.firstOperand - example.secondOperand
        }
        return (result == userAnswer)
    }

    @SuppressLint("SetTextI18n")
    private fun updateStatistics() {
        binding.correctAnswersTextView.text = "Правильных: $correctAnswers"
        binding.wrongAnswersTextView.text = "Неправильных: $wrongAnswers"
        val totalAnswers = correctAnswers + wrongAnswers
        val percent = if (totalAnswers > 0) {
            "%.2f".format(correctAnswers * 100.0 / totalAnswers)
        } else {
            "0.00"
        }
        binding.percentCorrectAnswersTextView.text = "Процент правильных ответов: $percent%"
    }
}