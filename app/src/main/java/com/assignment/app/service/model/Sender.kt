package com.assignment.app.service.model

data class Sender(
    val phone: String,
    val name: String,
    val email: String
) {
    override fun toString(): String {
        return "Sender(phone='$phone', name='$name', email='$email')"
    }
}