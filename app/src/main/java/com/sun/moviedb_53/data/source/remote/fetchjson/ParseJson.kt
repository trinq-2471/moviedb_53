package com.sun.moviedb_53.data.source.remote.fetchjson

import com.sun.moviedb_53.data.model.HotMovie
import com.sun.moviedb_53.data.model.HotMovieEntry
import org.json.JSONObject

class ParseJson {

    fun movieParseJson(jsonObject: JSONObject) = jsonObject.run {
        HotMovie(
            getInt(HotMovieEntry.ID),
            getString(HotMovieEntry.TITLE),
            getString(HotMovieEntry.URL_IMAGE),
            getDouble(HotMovieEntry.VOTE)
        )
    }
}
