package com.example.afontgou17alumnes.mypillrecord.data.search

import android.app.ProgressDialog
import android.os.AsyncTask
import org.json.JSONArray
import org.json.JSONObject
import java.io.FileNotFoundException
import java.net.HttpURLConnection
import java.net.URL

class AsyncTaskSearch: AsyncTask<String, String, String>() {
    // API Implementation
    lateinit var pDialog: ProgressDialog
    // Final of implementation

    var activity: SearchActivity? = null
    val listOfPills = ArrayList<String>()

    fun setContext(act : SearchActivity) {
        this.activity = act
    }

    override fun onPreExecute() {
        super.onPreExecute()
        pDialog = ProgressDialog(activity)
        pDialog.setMessage("Loading...")
        pDialog.setCancelable(false)
        pDialog.show()
    }

    override fun doInBackground(vararg url: String?): String {
        var res:String = ""
        val connection= URL(url[0]).openConnection() as HttpURLConnection
        try {
            connection.connect()
            res=connection.inputStream.use { it.reader().use { reader->reader.readText() } }
        } catch (e: FileNotFoundException) {
            res = "0"
        } finally {
            connection.disconnect()
        }
        return res
    }
    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        if (pDialog.isShowing) pDialog.dismiss()
        jsonParser(result)
    }

    private fun jsonParser(result:String?) {
        if (result != "0") {
            val results = JSONObject(result)
            val jsonResults: JSONArray = results.getJSONArray("results")

            var i = 0
            while (i < jsonResults.length()) {
                val currentPill = jsonResults.getJSONObject(i)
                val brand_name: String = currentPill.getString("brand_name")
                listOfPills.add(brand_name)
                i++
            }
            activity?.getSearchResults(listOfPills)// Valid (current)
        } else {
            activity?.resultsNotFound() // Error case
        }
        //println(listOfPills)
    }
} // Final of implementation