package ru.skillbranch.devintensive.model

import java.util.*

class TextMessage(
    id: String,
    from: User?,
    chat: Chat,
    isIncoming: Boolean,
    date: Date,
    var text: String
) : BaseMessage(id, from, chat, isIncoming, date) {

    override fun formatMessage(): String = "${from?.firstName} ${if (isIncoming) "получил" else "отправил"} $text"
}