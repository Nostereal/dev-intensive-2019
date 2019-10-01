package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts = fullName?.trim()?.split(" ")
        val firstName = parts?.getOrNull(0)?.ifEmpty { null }
        val lastName = parts?.getOrNull(1)?.ifEmpty { null }

        return firstName to lastName
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val firstInitial: Char? = if (firstName == null || firstName.replace(" ", "") == "") {
            null
        } else {
            firstName[0].toUpperCase()
        }
        val lastInitial: Char? = if (lastName == null || lastName.replace(" ", "") == "") {
            null
        } else {
            lastName[0].toUpperCase()
        }
        return if (firstInitial == null) {
            lastInitial?.toString()
        } else
            "$firstInitial${lastInitial ?: ""}"
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val dict: Map<String, String> = mapOf(
            "а" to "a",
            "б" to "b",
            "в" to "v",
            "г" to "g",
            "д" to "d",
            "е" to "e",
            "ё" to "e",
            "ж" to "zh",
            "з" to "z",
            "и" to "i",
            "й" to "i",
            "к" to "k",
            "л" to "l",
            "м" to "m",
            "н" to "n",
            "о" to "o",
            "п" to "p",
            "р" to "r",
            "с" to "s",
            "т" to "t",
            "у" to "u",
            "ф" to "f",
            "х" to "h",
            "ц" to "c",
            "ч" to "ch",
            "ш" to "sh",
            "щ" to "sh'",
            "ъ" to "",
            "ы" to "i",
            "ь" to "",
            "э" to "e",
            "ю" to "yu",
            "я" to "ya",
            " " to divider
        )

        var transliteratedName = ""
        for (char in payload) {
            transliteratedName += dict[char.toString()] ?: char
        }

        return transliteratedName
    }
}