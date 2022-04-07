package me.lonelyday.derpibooru.util

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

fun Date.toLocalDateTime(): LocalDateTime = LocalDateTime.ofEpochSecond(this.time, 0, ZoneOffset.UTC)
