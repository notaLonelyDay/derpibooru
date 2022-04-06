package me.lonelyday.derpibooru.ui.screen.search

import androidx.compose.animation.core.animate
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.lonelyday.derpibooru.db.vo.Image

@Composable
fun ImageItem(image: Image) {
    Row {
        ArtistsList()
    }
}

@Composable
fun ArtistsList() {
    Column {
        Text(text = "Artists")
    }
}


@Preview
@Composable
fun ImageItemPreview() {
    val image = Image(
        id = 1231231,
        animated = false,
        aspectRatio = 1f,
        commentCount = 1231231,
        createdAt = 1231231,
        deletion_reason = "asd asd",
        description = "description",
        downvotes = 1231231,
        duplicate_of = 1231231,
        duration = 1231231f,
        faves = 1231231,
        first_seen_at = 1231231,
        format = "png",
        height = 1231231,
        hidden_from_users = false,
        mime_type = "png",
        name = "name",
        orig_sha512_hash = "asdasdasd",
        processed = true,
        representations = mapOf(),
        score = 1231231,
        sha512_hash = "asdasdasdas",
        size = 1231231,
        source_url = "zxczxczx",
        spoilered = false,
        tag_count = 1231231,
        tag_ids = listOf(1231231),
        tag_names = listOf("asdasd", "zxczxc"),
        thumbnails_generated = true,
        updated_at = 1231231,
        uploader = "zxczxc",
        uploader_id = 1231231,
        upvotes = 1231231,
        view_url = "zxczxc",
        width = 1231231,
        wilson_score = 1231231f
    )
    ImageItem(image = image)
}