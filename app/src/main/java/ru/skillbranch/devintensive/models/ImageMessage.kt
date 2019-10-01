package ru.skillbranch.devintensive.models

import java.util.*

class ImageMessage(
    id: String,
    from: User?,
    chat: Chat,
    isIncoming: Boolean,
    date: Date,
    var img: String
) : BaseMessage(id, from, chat, isIncoming, date) {

    override fun formatMessage(): String = "${from?.firstName} ${if (isIncoming) "получил" else "отправил"} $img"
}