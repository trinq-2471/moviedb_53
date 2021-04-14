package com.sun.moviedb_53.data.model

data class Actor(
    val id: Int,
    val name: String,
    val imageUrl: String
)

object ActorEntry {
    const val LIST_ACTOR = "cast"
    const val ID = "id"
    const val NAME = "name"
    const val IMAGE_URL = "profile_path"
}
