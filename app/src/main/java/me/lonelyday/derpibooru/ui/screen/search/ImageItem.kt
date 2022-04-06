package me.lonelyday.derpibooru.ui.screen.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.lonelyday.derpibooru.R
import me.lonelyday.derpibooru.db.vo.Image
import java.time.LocalDateTime

@Composable
fun ImageItem(image: Image) {
    Column {
        ArtistsList(image = image)
        ImageRaw(image = image)
        ImageRating(image = image)
    }
}

@Composable
fun ArtistsList(image: Image) {
    Row {
        for (artist in image.tag_names) {
            Text(modifier = Modifier.padding(3.dp), text = artist)
        }
    }
}

@Composable
fun ImageRaw(image: Image) {
    val imagePainter: Painter = painterResource(id = R.drawable.ic_menu_gallery)
    Image(painter = imagePainter, contentDescription = "")

}

@Composable
fun ImageRating(image: Image) {
    Row {
        Text(text = "300")
        Text(text = "300")
        Text(text = "300")
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
        createdAt = LocalDateTime.ofEpochSecond(123123, 0, null),
        deletion_reason = "asd asd",
        description = "description",
        downvotes = 1231231,
        duplicate_of = 1231231,
        duration = 1231231f,
        faves = 1231231,
        first_seen_at = LocalDateTime.ofEpochSecond(123123, 0, null),
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