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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.skydoves.landscapist.glide.GlideImage
import me.lonelyday.derpibooru.R
import me.lonelyday.derpibooru.db.vo.Image
import me.lonelyday.derpibooru.db.vo.ImageRepresentations
import me.lonelyday.derpibooru.db.vo.ImageWithTags
import me.lonelyday.derpibooru.db.vo.Tag
import me.lonelyday.derpibooru.util.extractArtistName
import me.lonelyday.derpibooru.util.filterArtistTags
import java.time.LocalDateTime
import java.time.ZoneOffset

@Composable
fun ImageWithTagsItem(image: ImageWithTags) {
    Column {
        ArtistsList(tags = filterArtistTags(image.tags))
        ImageRaw(image = image.image)
        ImageRating(image = image.image)
        Tags(tags = image.tags) // todo add onclick
        ImageDescription(image = image.image)
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

//@Preview
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
fun ImageRaw(image: Image) {
    GlideImage(
        imageModel = image.representations.getUrlBySize(ImageRepresentations.Size.TALL),
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
fun ImageRating(image: Image) {
    Row {
        Image(painterResource(R.drawable.ic_round_favorite),"")
        Text(text = image.faves.toString())
        Image(painterResource(R.drawable.ic_round_arrow_up),"")
        Text(text = image.upvotes.toString())
        Image(painterResource(R.drawable.ic_arrow_down),"")
        Text(text = image.downvotes.toString())
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

//@Preview
@Composable
fun TagItemPreview() {
    TagItem(
        "test",
        Color.Red,
        Color.Black
    )
}

//@Preview
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

@Composable
fun ImageDescription(image: Image) {
    Column {
        Row {
            image.uploader?.let {
                Text(text = it)
            }
            Text(text = "${image.createdAt?.toString()} ")
            Text(text = "${image.width}x${image.height} ${image.format} ${image.size}")
        }
        Row {
            Text(text = "Source: ")
            image.source_url?.let { Text(text = it) }
        }
        Text(text = image.description)
    }
}

@Composable
@Preview
fun ImageItemPreview() {
    val imageWithTags = ImageWithTags(
        image = Image(
            id = 1,
            source_url = "https://derpibooru.org/images/1",
            description = "description",
            upvotes = 1,
            downvotes = 1,
            faves = 1,
            representations = ImageRepresentations(
                small = "https://derpibooru.org/images/1/small",
                large = "https://derpibooru.org/images/1/large",
                thumb = "https://derpibooru.org/images/1/thumb",
                tall = "https://derpicdn.net/img/view/2022/4/8/2842165.jpg",
                full = "https://derpibooru.org/images/1/full",
                medium = "https://derpibooru.org/images/1/medium",
                thumb_small = "https://derpibooru.org/images/1/thumb_small",
                thumb_tiny = "https://derpibooru.org/images/1/thumb_tiny",
            ),
            animated = false,
            aspectRatio = 1.0f,
            commentCount = 1,
            createdAt = LocalDateTime.ofEpochSecond(100000, 0, ZoneOffset.UTC),
            first_seen_at = LocalDateTime.ofEpochSecond(100000, 0, ZoneOffset.UTC),
            updated_at = LocalDateTime.ofEpochSecond(100000, 0, ZoneOffset.UTC),
            deletion_reason = null,
            wilson_score = 123f,
            duplicate_of = null,
            duration = 0f,
            format = "png",
            height = 2000,
            hidden_from_users = false,
            width = 1000,
            mime_type = "image/png",
            name = "name",
            orig_sha512_hash = "orig_sha512_hash",
            processed = true,
            score = 1,
            sha512_hash = "sha512_hash",
            size = 256000,
            spoilered = false,
            tag_count = 1,
            tag_ids = listOf(1),
            tag_names = listOf("tag1"),
            thumbnails_generated = true,
            uploader = "uploader",
            uploader_id = 1,
            view_url = "view_url"
        ),
        tags = listOf(
            Tag(id = 1, name = "tag1"),
            Tag(id = 2, name = "tag2"),
            Tag(id = 3, name = "tag3"),
            Tag(id = 4, name = "tag4"),
            Tag(id = 5, name = "tag5"),
        )
    )

    ImageWithTagsItem(imageWithTags)
}
