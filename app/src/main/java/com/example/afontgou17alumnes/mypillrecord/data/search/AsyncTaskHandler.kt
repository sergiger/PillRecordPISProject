package com.example.afontgou17alumnes.mypillrecord.data.search

import android.os.AsyncTask
import com.example.afontgou17alumnes.mypillrecord.data.pills.Active_ingredients
import com.example.afontgou17alumnes.mypillrecord.data.pills.MyData
import com.example.afontgou17alumnes.mypillrecord.ui.Pill.PillMedication
import com.example.afontgou17alumnes.mypillrecord.ui.today.AddUnplannedMedicine
import org.json.JSONArray
import org.json.JSONObject
import java.io.FileNotFoundException
import java.net.HttpURLConnection
import java.net.URL

class AsyncTaskHandler: AsyncTask<String, String, String>() {

    var activity: PillMedication? = null
    var activity2: AddUnplannedMedicine? = null
    val listOfPills = ArrayList<MyData>()

    fun setContext(act : PillMedication) {
        this.activity = act
    }
    fun setContextUnplanned(act : AddUnplannedMedicine) {
        this.activity2 = act
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

                val jsonAI: JSONArray = currentPill.getJSONArray("active_ingredients")
                val listOfAI = ArrayList<Active_ingredients>()
                var j = 0
                while (j < jsonAI.length()) {
                    val currentAI = jsonAI.getJSONObject(j)

                    val nameAI: String = currentAI.getString("name")
                    val strengthAI: String = currentAI.getString("strength")
                    val active_ingredients = Active_ingredients(nameAI, strengthAI)
                    listOfAI.add(active_ingredients)
                    j++
                }
                val pill = MyData(brand_name, listOfAI)
                listOfPills.add(pill)
                i++
            }
            if (activity != null) activity?.getSearchResults(listOfPills) // Valid (current)
            else if (activity2 != null) activity2?.getSearchResults(listOfPills)
        } else {
            if (activity != null) activity?.resultsNotFound() // Error case
            else if (activity2 != null) activity2?.resultsNotFound()
        }
        //println(listOfPills)
    }
} // Final of implementation