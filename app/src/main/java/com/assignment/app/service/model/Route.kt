package com.assignment.app.service.model

data class Route(
    val start: String,
    val end: String
) {
    override fun toString(): String {
        return "Route(start='$start', end='$end')"
    }
}