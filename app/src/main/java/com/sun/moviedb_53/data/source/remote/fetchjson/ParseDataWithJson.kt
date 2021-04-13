package com.sun.moviedb_53.data.source.remote.fetchjson

import com.sun.moviedb_53.data.model.HotMovieEntry
import com.sun.moviedb_53.utils.KeyEntityTpye
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.jvm.Throws

class ParseDataWithJson {

    fun getJsonFromUrl(urlString: String?): String? {
        try {
            val url = URL(urlString)
            val httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.apply {
                connectTimeout = TIME_OUT
                readTimeout = TIME_OUT
                requestMethod = METHOD_GET
                doOutput = true
                connect()
            }
            val bufferedReader = BufferedReader(InputStreamReader(url.openStream()))
            val stringBuilder = StringBuilder()
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
            bufferedReader.close()
            httpURLConnection.disconnect()
            return stringBuilder.toString()
        } catch (e: JSONException) {
            e.printStackTrace()
            return null
        }
    }

    fun parseJsonToData(jsonObject: JSONObject?, keyEntityType: KeyEntityTpye): Any? {
        return try {
            when (keyEntityType) {
                KeyEntityTpye.MOVIE_ITEM -> {
                    return parseJsonToList(
                        jsonObject?.getJSONArray(HotMovieEntry.MOVIE),
                        keyEntityType
                    )
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun parseJsonToList(jsonArray: JSONArray?, typeModel: KeyEntityTpye): Any? {
        try {
            val data = mutableListOf<Any?>()
            for (i in 0 until (jsonArray?.length() ?: 0)) {
                val jsonObject = jsonArray?.getJSONObject(i)
                data.add(parseJsonToObject(jsonObject, typeModel))
            }
            return data.filterNotNull()
        } catch (e: JSONException) {
            e.printStackTrace()
            return null
        }
    }

    private fun parseJsonToObject(jsonObject: JSONObject?, keyEntityType: KeyEntityTpye): Any? {
        try {
            jsonObject?.let {
                return when (keyEntityType) {
                    KeyEntityTpye.MOVIE_ITEM -> {
                        ParseJson().movieParseJson(it)
                    }
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }

    companion object {
        private const val TIME_OUT = 15000
        private const val METHOD_GET = "GET"
    }
}
