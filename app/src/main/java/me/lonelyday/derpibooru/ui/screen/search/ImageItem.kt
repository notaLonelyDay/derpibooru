package me.lonelyday.derpibooru.ui.screen.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import me.lonelyday.derpibooru.R
import me.lonelyday.derpibooru.db.vo.ImageWithTags
import me.lonelyday.derpibooru.db.vo.Tag
import me.lonelyday.derpibooru.util.extractArtistName
import me.lonelyday.derpibooru.util.filterArtistTags
import java.time.LocalDateTime

@Composable
fun ImageWithTagsItem(image: ImageWithTags) {
    Column {
        ArtistsList(tags = filterArtistTags(image.tags))
        ImageRaw(image = image)
        ImageRating(image = image)
        Tags(tags = image.tags) // todo add onclick
    }
}

@Composable
fun ArtistsList(
    tags: List<Tag>,
    onClick: (Tag) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .horizontalScroll(ScrollState(0), true),
    ) {
        Text(modifier = Modifier.align(Alignment.CenterVertically), text = "Artist: ")
        for (tag in tags) {
            TagItem(
                text = tag.extractArtistName(),
                backgroundColor = Color.Black,
                foregroundColor = Color.Gray,
                onClick = { onClick(tag) },
            )
            Spacer(modifier = Modifier.width(5.dp))
        }
    }
}

@Preview
@Composable
fun ArtistsListPreview() {
    ArtistsList(tags = listOf(
        Tag(id = 1, name = "artist:hiroshiru"),
        Tag(id = 2, name = "artist:kazuki"),
        Tag(id = 3, name = "artist:almux"),
        Tag(id = 3, name = "artist:almux"),
        Tag(id = 3, name = "artist:almux"),
        Tag(id = 3, name = "artist:almux"),
        Tag(id = 3, name = "artist:almux"),
    ))
}


@Composable
fun ImageRaw(image: ImageWithTags) {
    val imagePainter: Painter = painterResource(id = R.drawable.ic_menu_gallery)
    Image(painter = imagePainter, contentDescription = "")

}

@Composable
fun ImageRating(image: ImageWithTags) {
    Row {
        Text(text = "300")
        Text(text = "300")
        Text(text = "300")
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Tags(
    tags: List<Tag>,
    onTagClick: (Tag) -> Unit = {}
) {
    FlowRow(
        crossAxisSpacing = 0.dp,
        mainAxisSpacing = 0.dp,
        modifier = Modifier.padding(0.dp)
    ) {
        tags.forEach {
            TagItem(text = it.name, backgroundColor = Color.Cyan, foregroundColor = Color.White, onClick = { onTagClick(it) })
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TagItem(
    text: String,
    backgroundColor: Color,
    foregroundColor: Color,
    onClick: () -> Unit = {},
) {
    Card(
        shape = RoundedCornerShape(40.dp),
        onClick = { onClick() }
    ) {
        Text(
            modifier = Modifier
                .background(color = backgroundColor)
                .padding(vertical = 1.dp, horizontal = 6.dp),
            color = foregroundColor,
            text = text
        )
    }
}

@Preview
@Composable
fun TagItemPreview() {
    TagItem(
        "test",
        Color.Red,
        Color.Black
    )
}

@Preview
@Composable
fun TagsPreview() {
    Tags(
        listOf(
            Tag(id = 1, name = "tag1"),
            Tag(id = 2, name = "tag2"),
            Tag(id = 3, name = "tag3"),
            Tag(id = 4, name = "tag4"),
            Tag(id = 5, name = "tag5"),
        )
    )
}


//@Preview
//@Composable
//fun ImageItemPreview() {
//    val image = Image(
//        id = 1231231,
//        animated = false,
//        aspectRatio = 1f,
//        commentCount = 1231231,
//        createdAt = LocalDateTime.ofEpochSecond(123123, 0, ZoneOffset.UTC),
//        deletion_reason = "asd asd",
//        description = "description",
//        downvotes = 1231231,
//        duplicate_of = 1231231,
//        duration = 1231231f,
//        faves = 1231231,
//        first_seen_at = LocalDateTime.ofEpochSecond(123123, 0, ZoneOffset.UTC),
//        format = "png",
//        height = 1231231,
//        hidden_from_users = false,
//        mime_type = "png",
//        name = "name",
//        orig_sha512_hash = "asdasdasd",
//        processed = true,
//        representations = mapOf(),
//        score = 1231231,
//        sha512_hash = "asdasdasdas",
//        size = 1231231,
//        source_url = "zxczxczx",
//        spoilered = false,
//        tag_count = 1231231,
//        tag_ids = listOf(1231231),
//        tag_names = listOf("asdasd", "zxczxc"),
//        thumbnails_generated = true,
//        updated_at = 1231231,
//        uploader = "zxczxc",
//        uploader_id = 1231231,
//        upvotes = 1231231,
//        view_url = "zxczxc",
//        width = 1231231,
//        wilson_score = 1231231f
//    )
//    ImageItem(image = image)
//}