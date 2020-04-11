package com.example.afontgou17alumnes.mypillrecord.data

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.R.layout.activity_search

class SearchActivity : AppCompatActivity() {
    //lateinit var drugs_list: RecyclerView
    //var drugs: MutableList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_search)
        doMySearch()
        // No entra aqui
        /*if (Intent.ACTION_SEARCH == intent.action) {
            println(1)
            intent.getStringExtra(SearchManager.QUERY)?.also { query->
                doMySearch()
            }
        }*/
    }

    fun doMySearch() {
        val finder:SearchView = findViewById(R.id.searchEngine)
        finder.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //TODO("Not yet implemented")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //TODO("Not yet implemented")
                if (newText!!.isNotEmpty()) {
                    val search = newText.toLowerCase()
                    println(1111111111)
                    return true
                }else {
                    return false
                }
            }
        })
    }
}
