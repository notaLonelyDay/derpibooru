package me.lonelyday.derpibooru.util

import java.time.LocalDateTime
import java.time.ZoneOffset

fun Long.toLocalDateTime(): LocalDateTime = LocalDateTime.ofEpochSecond(this, 0, ZoneOffset.UTC)
