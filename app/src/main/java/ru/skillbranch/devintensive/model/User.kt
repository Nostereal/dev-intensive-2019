package ru.skillbranch.devintensive.model

import java.util.*

data class User(
    val id: String,
    var firstName: String?,
    var lastName: String?,
    var avatar: String?,
    var rating: Int = 0,
    var respect: Int = 0,
    var lastVisit: Date? = Date(),
    var isOnline: Boolean = false
) {
    companion object Factory {
        private var nextId: Int = 0

        fun makeUser(fullname: String?): User {
            val nameParts = fullname?.split(" ")
            val firstName: String? = nameParts?.get(0)
            val lastName: String? = nameParts?.get(1)

            return User(id = "${nextId++}", firstName = firstName, lastName = lastName, avatar = null)
        }
    }

    data class Builder(
        var id: String,
        var firstName: String?,
        var lastName: String?,
        var avatar: String?,
        var rating: Int = 0,
        var respect: Int = 0,
        var lastVisit: Date? = Date(),
        var isOnline: Boolean = false
    ) {
        fun id(s: String) = apply { this@Builder.id = s}
        fun firstName(fn: String?) = apply { this@Builder.firstName = fn }
        fun lastName(ln: String?) = apply { this@Builder.lastName = ln }
        fun avatar(avatar: String?) = apply { this@Builder.avatar = avatar }
        fun rating(rating: Int) = apply { this@Builder.rating = rating }
        fun respect(respect: Int) = apply { this@Builder.respect = respect }
        fun lastVisit(date: Date?) = apply { this@Builder.lastVisit = date}
        fun isOnline(isOnline: Boolean) = apply { this@Builder.isOnline = isOnline }
        fun build() = User(id, firstName, lastName, avatar, rating, respect, lastVisit, isOnline)
    }
}