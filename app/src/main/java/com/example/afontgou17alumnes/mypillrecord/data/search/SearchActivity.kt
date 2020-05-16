package com.example.afontgou17alumnes.mypillrecord.data.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.R.id.search_src_text
import com.example.afontgou17alumnes.mypillrecord.R.layout.activity_search
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.Frequency
import com.example.afontgou17alumnes.mypillrecord.ui.Pill.PillMedication
import com.example.afontgou17alumnes.mypillrecord.ui.today.AddUnplannedMedicine
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.pill_child.view.*

class SearchActivity : AppCompatActivity(),
    OnPillsListener {
    var currentText: CharSequence = ""
    var displayList: MutableList<String> = ArrayList()
    var mother_activity = ""
    //values from PillMedication
    var Medicine = ""
    var DoseIn = 0
    var Units = ""
    var Notes = ""
    var Hours = listOf<String>()
    var frequencyClass : Frequency? = null

    // from AddUnplannedMedicine
    /*var dateUnplanned = ""
    var hourUnplanned = ""*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_search)

        val bundle:Bundle? = intent.extras
        mother_activity = bundle?.get("mother_activity") as String

        val Medicine = bundle?.get("Medicine")
        if(Medicine!= null){
            this.Medicine= Medicine as String
        }
        val Hours = bundle?.get("Hours")
        if(Hours != null){
            this.Hours = (Hours as Array<String>).toList()
        }
        val Dose = bundle?.get("Dose")
        if(Dose!= 0){
            this.DoseIn = Dose as Int
        }
        val Units = bundle?.get("Units")
        if(Units != null){
            this.Units = Units as String
        }
        val Notes = bundle?.get("Notes")
        if(Notes!= null){
            this.Notes = Notes as String
        }
        val Frequency = bundle?.get("Frequency")
        if(Frequency!= null){
            this.frequencyClass = Frequency as Frequency
        }
        /*val Date = bundle?.get("DateAdd")
        if(Date!= null){
            this.dateUnplanned = Date as String
        }
        val HourAdd = bundle?.get("HourAdd")
        if(HourAdd!= null){
            this.hourUnplanned = HourAdd as String
        }*/

        back_arrow_search.setOnClickListener {
            onBackPressed()
        }

        pills_list.layoutManager = LinearLayoutManager(this)
        pills_list.adapter = PillAdapter(displayList, this, this)
        val url = "https://api.fda.gov/drug/ndc.json?search=brand_name:"
        val limit = "&limit=100"

        doMySearch(url, limit)
    }

    fun doMySearch(url: String, limit: String) {
        //val finder = searchItem.actionView as SearchView
        val finder: SearchView = findViewById(R.id.searchEngine)
        val editext: EditText = finder.findViewById(search_src_text)
        editext.hint = "Search here..."
        finder.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                var asyncTask = AsyncTaskSearch()
                asyncTask.setContext(this@SearchActivity)
                asyncTask.execute(url + query + limit)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                displayList.clear()
                if (newText!!.isNotEmpty()) {
                    displayList.add(newText)
                    /*val search = newText.toLowerCase()
                    drugs.forEach {
                        if (it.toLowerCase().contains(search)) {
                            displayList.add(it)
                        }
                    }*/
                } else {
                    // displayList.addAll(drugs)
                }
                pills_list.adapter?.notifyDataSetChanged()
                return true
            }
        })
    }
    // Valid Function
    fun getSearchResults(result: ArrayList<String>) {
        displayList.addAll(result)
        pills_list.adapter?.notifyDataSetChanged()
    }

    fun resultsNotFound() {
        Toast.makeText(this, "No matches found!", Toast.LENGTH_SHORT).show()
    }

    class PillAdapter(items : List<String>, ctx : Context, listener: OnPillsListener) : RecyclerView.Adapter<PillAdapter.ViewHolder>(){
        private var list = items
        private var context = ctx
        private var mlistener = listener

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.pill_child, parent, false),
                mlistener
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.button.text = list[position]
        }

        override fun getItemCount(): Int {
            return list.size
        }
        class ViewHolder (itemView : View, listener: OnPillsListener) : RecyclerView.ViewHolder(itemView), OnClickListener{
            val button: Button = itemView.pill_name
            var mlistener = listener
            init {
                button.setOnClickListener(this)
            }
            override fun onClick(p: View?) {
                mlistener.onPillClick(adapterPosition)
            }
        }
    }
    override fun onPillClick(position: Int) {
        val pill = displayList[position]
        Toast.makeText(this, pill, Toast.LENGTH_SHORT).show()
        searchEngine.setQuery(pill, true)
        if (mother_activity == "pill") {
            val intent = Intent(this, PillMedication::class.java)
            intent.putExtra("Medicine", pill)
            intent.putExtra("Hours",this.Hours.toTypedArray())
            // Falta frequency
            if (DoseIn != 0) intent.putExtra("Dose",this.DoseIn)
            intent.putExtra("Units",this.Units)
            intent.putExtra("Notes",this.Notes)
            startActivity(intent)
        }
        else if (mother_activity == "today") {
            val intent = Intent(this, AddUnplannedMedicine::class.java)
            intent.putExtra("Medicine", pill)
            if (DoseIn != 0) intent.putExtra("Dose",this.DoseIn)
            if (Units != null) intent.putExtra("UnitsAdd",this.Units)
            startActivity(intent)
        }
    }
}
