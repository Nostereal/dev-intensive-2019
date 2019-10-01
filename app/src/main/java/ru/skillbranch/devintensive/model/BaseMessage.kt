package ru.skillbranch.devintensive.model

import java.util.*

abstract class BaseMessage(
    val id: String,
    val from: User?,
    val chat: Chat,
    val isIncoming: Boolean = false,
    val date: Date = Date()
) {
    abstract fun formatMessage(): String

    companion object AbstractFactory {
        var prevId = -1
        fun makeMessage(from: User?, chat: Chat, date: Date = Date(), type: String = "text",
                        payload: String, isIncoming: Boolean = false): BaseMessage {
            prevId++
            return when (type) {
                "image" -> ImageMessage(
                    "$prevId",
                    from = from,
                    chat = chat,
                    date = date,
                    img = payload,
                    isIncoming = isIncoming
                )
                else -> TextMessage(
                    "$prevId",
                    from = from,
                    chat = chat,
                    date = date,
                    text = payload,
                    isIncoming = isIncoming
                )
            }
        }
    }
}