package me.lonelyday.derpibooru.util

import me.lonelyday.derpibooru.db.vo.Tag

const val ARTIST_PREFIX = "artist:"

fun Tag.isArtistTag(): Boolean {
    return name.startsWith(ARTIST_PREFIX)
}

fun filterArtistTags(tags: List<Tag>): List<Tag> {
    return tags.filter { it.isArtistTag() }
}

fun Tag.extractArtistName(): String {
    return name.removePrefix(ARTIST_PREFIX)
}

