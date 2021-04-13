package com.sun.moviedb_53.data.source.remote.fetchjson

import android.os.AsyncTask
import com.sun.moviedb_53.data.source.remote.OnFetchDataJsonListener
import com.sun.moviedb_53.utils.KeyEntityTpye
import org.json.JSONObject

@Suppress("DEPRECATION")
class GetJsonFromUrl<T> constructor(
    private val listener: OnFetchDataJsonListener<T>,
    private val keyEntityType: KeyEntityTpye
) : AsyncTask<String?, Void?, String?>() {

    private var exception: Exception? = null

    override fun doInBackground(vararg strings: String?): String? {
        var data = ""
        try {
            val parseDataWithJson = ParseDataWithJson()
            data = parseDataWithJson.getJsonFromUrl(strings[0]).toString()
        } catch (e: Exception) {
            exception = e
        }
        return data
    }

    override fun onPostExecute(data: String?) {
        super.onPostExecute(data)
        if (data != null && !data.isBlank()) {
            val jsonObject = JSONObject(data)
            @Suppress("UNCHECKED_CAST")
            listener.onSuccess(ParseDataWithJson().parseJsonToData(jsonObject, keyEntityType) as T)
        } else
            listener.onError(exception)
    }
}
