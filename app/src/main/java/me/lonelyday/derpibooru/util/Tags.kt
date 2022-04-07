package me.lonelyday.derpibooru.util

import me.lonelyday.derpibooru.db.vo.Tag

fun isArtistTag(tag: Tag): Boolean {
    return tag.name.startsWith("artist:")
}

fun filterArtistTags(tags: List<Tag>): List<Tag> {
    return tags.filter { isArtistTag(it) }
}