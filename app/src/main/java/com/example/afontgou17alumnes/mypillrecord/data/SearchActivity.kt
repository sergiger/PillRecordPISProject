package com.example.afontgou17alumnes.mypillrecord.data

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.R.id.search_src_text
import com.example.afontgou17alumnes.mypillrecord.R.layout.activity_search
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.pill_child.view.*

class SearchActivity : AppCompatActivity() {
    var drugs: MutableList<String> = ArrayList()
    var displayList: MutableList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_search)
        pills_list.layoutManager = LinearLayoutManager(this)
        pills_list.adapter = PillAdapter(displayList, this)
        // doMySearch()
        loadData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        val searchItem = menu.findItem(R.id.options_search)
        if (searchItem != null) {
            val finder = searchItem.actionView as SearchView
            //val finder:SearchView = findViewById(R.id.searchEngine)
            val editext:EditText = finder.findViewById(search_src_text)
            editext.hint = "Enter a medicine here..."
            finder.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    displayList.clear()
                    if (newText!!.isNotEmpty()) {
                        val search = newText.toLowerCase()
                        println(search)
                        drugs.forEach {
                            if (it.toLowerCase().contains(search)) {
                                displayList.add(it)
                            }
                        }
                    }else {
                        displayList.addAll(drugs)
                    }
                    pills_list.adapter?.notifyDataSetChanged()
                    return true
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    fun loadData() {
        drugs.add("Advil")
        drugs.add("Bayer")
        drugs.add("Ibuprofeno")
        drugs.add("Nasa")
        drugs.add("Cabrito")
    }

    /*fun doMySearch() {
    }*/

    class PillAdapter(items : List<String>, ctx : Context) : RecyclerView.Adapter<PillAdapter.ViewHolder>(){
        private var list = items
        private var context = ctx

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.pill_child, parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.name?.text = list[position]
        }

        override fun getItemCount(): Int {
            return list.size
        }

        class ViewHolder (v : View) : RecyclerView.ViewHolder(v) {
            val name = v.pill_name
        }

    }
}
