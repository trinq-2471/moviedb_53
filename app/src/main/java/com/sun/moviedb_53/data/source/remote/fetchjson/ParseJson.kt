package com.sun.moviedb_53.data.source.remote.fetchjson

import com.sun.moviedb_53.data.model.*
import com.sun.moviedb_53.utils.KeyEntityType
import org.json.JSONObject

@Suppress("UNCHECKED_CAST")
class ParseJson {

    fun movieParseJson(jsonObject: JSONObject) = jsonObject.run {
        HotMovie(
            getInt(HotMovieEntry.ID),
            getString(HotMovieEntry.TITLE),
            getString(HotMovieEntry.URL_IMAGE),
            getDouble(HotMovieEntry.VOTE)
        )
    }

    fun movieDetailParseJson(jsonObject: JSONObject) = jsonObject.run {
        val listGenres = ParseDataWithJson().parseJsonToList(
            getJSONArray(GenresEntry.LIST_GENRES),
            KeyEntityType.GENRES_ITEM
        ) as List<Genre>

        MovieDetail(
            id = getInt(MovieDetailEntry.ID),
            title = getString(MovieDetailEntry.TITLE),
            photoUrl = getString(MovieDetailEntry.PHOTO_URL),
            rate = getDouble(MovieDetailEntry.RATE),
            description = getString(MovieDetailEntry.DESCRIPTION),
            photoPoster = getString(MovieDetailEntry.PHOTO_POSTER),
            releaseDate = getString(MovieDetailEntry.RELEASE_DATE),
            tagLine = getString(MovieDetailEntry.TAG_LINE),
            genres = listGenres
        )
    }

    fun genresParseJson(jsonObject: JSONObject?) = jsonObject?.run {
        Genre(
            getInt(GenresEntry.ID),
            getString(GenresEntry.NAME)
        )
    }

    fun actorParseJson(jsonObject: JSONObject?) = jsonObject?.run {
        Actor(
            getInt(ActorEntry.ID),
            getString(ActorEntry.NAME),
            getString(ActorEntry.IMAGE_URL)
        )
    }

    fun videoYoutubeParseJson(jsonObject: JSONObject?) = jsonObject?.run {
        VideoYoutube(
            getString(VideoYoutubeEntry.ID),
            getString(VideoYoutubeEntry.KEY_YOUTUBE),
            getString(VideoYoutubeEntry.TYPE)
        )
    }

    fun genresMovieParseJson(jsonObject: JSONObject) = jsonObject.run {
        GenresMovie(
            getInt(GenresMovieEntry.ID),
            getString(GenresMovieEntry.TITLE),
            getString(GenresMovieEntry.URL_IMAGE)
        )
    }
}
