package ru.skillbranch.devintensive.extensions

fun String.truncate(value: Int = 16): String = this.slice(0 until value).trimEnd() + if (value > 3) " " else ""

// "[^>]*" means any symbols except '>'
fun String.stripHtml(): String = this.replace("<[^>]*>|\\s{2,}".toRegex(), "")