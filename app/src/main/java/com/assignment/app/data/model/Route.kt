package com.assignment.app.data.model

data class Route(
    val start: String,
    val end: String
) {
    override fun toString(): String {
        return "Route(start='$start', end='$end')"
    }
}