package ru.skillbranch.devintensive.models

import android.util.Log
import java.lang.IllegalStateException

typealias Color = Triple<Int, Int, Int>

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Color> {
        Log.d("M_Bender", "answer: $answer")
        return if (!question.isAnswerValid(answer)) {
            when (question) {
                Question.NAME ->
                    "Имя должно начинаться с заглавной буквы\n${question.question}" to status.color
                Question.PROFESSION ->
                    "Профессия должна начинаться со строчной буквы\n${question.question}" to status.color
                Question.MATERIAL ->
                    "Материал не должен содержать цифр\n${question.question}" to status.color
                Question.BDAY ->
                    "Год моего рождения должен содержать только цифры\n${question.question}" to status.color
                Question.SERIAL ->
                    "Серийный номер содержит только цифры, и их 7\n${question.question}" to status.color
                Question.IDLE -> throw IllegalStateException("it cannot happen")
            }
        } else if (question.answers.contains(answer.toLowerCase())) {
            question = question.nextQuestion()
            "Отлично - это правильный ответ!\n${question.question}" to status.color
        } else {
            status = status.nextStatus()
            "Это неправильный ответ\n${question.question}" to status.color
        }
    }

    enum class Status(val color: Color) {
        NORMAL(Color(255, 255, 255)),
        WARNING(Color(255, 120, 0)),
        DANGER(Color(255, 60, 60)),
        CRITICAL(Color(255, 0, 0));

        fun nextStatus(): Status =
            if (this.ordinal < values().lastIndex)
                values()[this.ordinal + 1]
            else
                values()[0]
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("Бендер", "bender")) {
            override fun isAnswerValid(answer: String): Boolean =
                answer.isNotEmpty() && answer[0] == answer[0].toUpperCase()

            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun isAnswerValid(answer: String): Boolean =
                answer.isNotEmpty() && answer[0] == answer[0].toLowerCase()

            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun isAnswerValid(answer: String): Boolean =
                !Regex("\\d").containsMatchIn(answer)

            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun isAnswerValid(answer: String): Boolean =
                Regex("^\\d+$").matches(answer)

            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun isAnswerValid(answer: String): Boolean =
                Regex("^\\d{7}$").matches(answer)

            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun isAnswerValid(answer: String): Boolean = true

            override fun nextQuestion(): Question = IDLE
        };

        abstract fun nextQuestion(): Question
        abstract fun isAnswerValid(answer: String): Boolean
    }
}