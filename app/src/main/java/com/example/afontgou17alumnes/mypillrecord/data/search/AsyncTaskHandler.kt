package com.example.afontgou17alumnes.mypillrecord.data.search

import android.os.AsyncTask
import com.example.afontgou17alumnes.mypillrecord.data.pills.Active_ingredients
import com.example.afontgou17alumnes.mypillrecord.data.pills.MyData
import com.example.afontgou17alumnes.mypillrecord.ui.Pill.PillMedication
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class AsyncTaskHandler: AsyncTask<String, String, String>() {
    // API Implementation
    // lateinit var pDialog: ProgressDialog
    // val url = "https://api.fda.gov/drug/ndc.json?search=active_ingredients.name:%22ibuprofen%22+AND+brand_name:%22Advil%22&limit=100"
    // AsyncTaskHandler().execute(url)
    // Final of implementation

    var activity: PillMedication? = null
    val listOfPills = ArrayList<MyData>()

    fun setContext(act : PillMedication) {
        this.activity = act
    }

    override fun doInBackground(vararg url: String?): String {
        val res:String
        val connection= URL(url[0]).openConnection() as HttpURLConnection
        try {
            connection.connect()
            res=connection.inputStream.use { it.reader().use { reader->reader.readText() } }
        }
        finally {
            connection.disconnect()
        }
        return res
    }
    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        /*if (pDialog.isShowing)
            pDialog.dismiss()*/
        jsonParser(result)
    }

    private fun jsonParser(result:String?) {
        val results = JSONObject(result)
        val jsonResults: JSONArray = results.getJSONArray("results")

        var i = 0
        while (i < jsonResults.length()) {
            val currentPill = jsonResults.getJSONObject(i)
            val brand_name:String = currentPill.getString("brand_name")

            val jsonAI: JSONArray = currentPill.getJSONArray("active_ingredients")
            val listOfAI = ArrayList<Active_ingredients>()
            var j = 0
            while (j<jsonAI.length()) {
                val currentAI = jsonAI.getJSONObject(j)

                val nameAI:String = currentAI.getString("name")
                val strengthAI:String = currentAI.getString("strength")
                val active_ingredients = Active_ingredients(nameAI,strengthAI)
                listOfAI.add(active_ingredients)
                j++
            }
            val pill = MyData(brand_name, listOfAI)
            listOfPills.add(pill)
            i++
        }
        activity?.getSearchResults(listOfPills) // Valid (current)
        //println(listOfPills)
    }
} // Final of implementation