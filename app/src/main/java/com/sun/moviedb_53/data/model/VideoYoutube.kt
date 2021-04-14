package com.sun.moviedb_53.data.model

data class VideoYoutube(
    val id: String,
    val key: String,
    val type: String
)

object VideoYoutubeEntry {
    const val LIST_VIDEOS = "results"
    const val ID = "id"
    const val KEY_YOUTUBE = "key"
    const val TYPE = "type"
}
