package me.lonelyday.derpibooru

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.TransitionOptions
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import android.graphics.drawable.Drawable





const val BASE_URL = "https://derpibooru.org/api/v1/json/"

// Do not change
const val FIRST_PAGE_NUMBER = 1

const val DEFAULT_PAGE_SIZE = 3

const val APP_PREFERENCES = "me.lonelyday.derpibooru.preferences"


@GlideModule
class DerpibooruAppGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        builder.setDefaultRequestOptions(requestOptions(context))
        builder.setDefaultTransitionOptions(
            Drawable::class.java,
            DrawableTransitionOptions.withCrossFade()
        )
    }

    // Use this function to change glide default settings
    private fun requestOptions(context: Context): RequestOptions {
        return RequestOptions()
    }
}