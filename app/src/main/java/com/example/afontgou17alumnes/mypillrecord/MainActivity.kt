package com.example.afontgou17alumnes.mypillrecord

import android.app.ProgressDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.data.pills.Active_ingredients
import com.example.afontgou17alumnes.mypillrecord.data.pills.MyData
import com.example.afontgou17alumnes.mypillrecord.ui.Pill.Pill_fragment
import com.example.afontgou17alumnes.mypillrecord.ui.calendar.Calendar_fragment
import com.example.afontgou17alumnes.mypillrecord.ui.settings.ajustes_activity
import com.example.afontgou17alumnes.mypillrecord.ui.statistics.Statistics_fragment
import com.example.afontgou17alumnes.mypillrecord.ui.today.Today_Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    lateinit var pDialog: ProgressDialog
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener{
       item->
       when(item.itemId){
           R.id.action_Today->{
               replaceFragment(Today_Fragment())
               Toast.makeText(this,"Today",Toast.LENGTH_SHORT).show()
               toolbar.title = "TODAY"
               return@OnNavigationItemSelectedListener true
           }
           R.id.action_calendar->{
               replaceFragment(Calendar_fragment())
               Toast.makeText(this,"Calendar",Toast.LENGTH_SHORT).show()
               toolbar.title ="CALENDAR"
               return@OnNavigationItemSelectedListener true
           }
           R.id.action_team->{
               Toast.makeText(this,"Team (In next version",Toast.LENGTH_SHORT).show()
               /*replaceFragment(Team_fragment())

               toolbar.title ="TEAM"*/
               return@OnNavigationItemSelectedListener true
           }
           R.id.action_pills->{
               replaceFragment(Pill_fragment())
               Toast.makeText(this,"Add",Toast.LENGTH_SHORT).show()
               toolbar.title ="THERAPY"
               return@OnNavigationItemSelectedListener true
           }
           R.id.action_statistcs-> {
               if(Controller.ja_iniciat==false){
                   replaceFragment(Statistics_fragment())
                   Controller.ja_iniciat=true
               }else{
                   go_To_Statistics(0)
               }
               Toast.makeText(this,"Statistics",Toast.LENGTH_SHORT).show()
               toolbar.title ="STATISTICS"
               return@OnNavigationItemSelectedListener true
           }
       }
       false

   }

    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // API Implementation
        val url = "https://api.fda.gov/drug/ndc.json?search=active_ingredients.name:%22ibuprofen%22+AND+brand_name:%22Advil%22&limit=100"
        //TODO senyal posadq
        AsyncTaskHandler().execute(url)  // Final of implementation

        setSupportActionBar(toolbar)

        navigationBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val bundle:Bundle? = intent.extras
        //medicine
        val goTo = bundle?.get("goTo")
        if(goTo!= null){
            if(goTo == "Pill"){
                replaceFragment(Pill_fragment())
            }
        }
        else{
            replaceFragment(Today_Fragment())
        }
    }

    // API Implementation
    inner class AsyncTaskHandler: AsyncTask<String, String, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            pDialog = ProgressDialog(this@MainActivity)
            pDialog.setMessage("Please Wait")
            pDialog.setCancelable(false)
            pDialog.show()
        }

        override fun doInBackground(vararg url: String?): String {
            val res:String
            val connection=URL(url[0]).openConnection() as HttpURLConnection
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
            if (pDialog.isShowing)
                pDialog.dismiss()
            jsonParser(result)
        }

        private fun jsonParser(result:String?) {
            val results = JSONObject(result)
            val jsonResults: JSONArray = results.getJSONArray("results")

            val listOfPills = ArrayList<MyData>()
            var i = 0
            while (i<jsonResults.length()) {
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
            println(listOfPills)
        }

    } // Final of implementation

    private fun replaceFragment(fragment: Fragment){

        val fragmentTransaction  = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit()

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if(id == R.id.options_item){
            Toast.makeText(this,"opcions",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ajustes_activity::class.java)
            startActivity(intent)
        }
        if(id == R.id.pdf_item){
            Toast.makeText(this,"PDF is being generated",Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }
    fun go_To_Statistics(id:Int){

        val bundle = Bundle()
        bundle.putInt("start",id)

        val transaction = this.supportFragmentManager.beginTransaction()
        val statisticsFragment = Statistics_fragment()
        statisticsFragment.arguments = bundle

        transaction.replace(R.id.fragmentContainer, statisticsFragment)
        transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
    }
}
