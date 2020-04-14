package com.example.afontgou17alumnes.mypillrecord.data.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
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

class SearchActivity : AppCompatActivity(),
    OnPillsListener {
    var currentText: CharSequence = ""
    var drugs: MutableList<String> = ArrayList()
    var displayList: MutableList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_search)

        back_arrow_search.setOnClickListener{
            onBackPressed()
        }
        pills_list.layoutManager = LinearLayoutManager(this)
        pills_list.adapter =
            PillAdapter(
                displayList,
                this,
                this
            )
        loadData()
        doMySearch()
    }

    fun loadData() {
        drugs.add("Advil")
        drugs.add("Advil")
        drugs.add("Advil1")
        drugs.add("Advil2")
        drugs.add("Advil3")
        drugs.add("Advil4")
        drugs.add("Bayer")
        drugs.add("Ibuprofeno")
        drugs.add("Nasa")
        drugs.add("Cabrito")
    }

    fun doMySearch() {
        //val finder = searchItem.actionView as SearchView
        val finder:SearchView = findViewById(R.id.searchEngine)
        val editext:EditText = finder.findViewById(search_src_text)
        editext.hint = "Enter medicine name..."
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
                println(100000)
                button.setOnClickListener(this)
            }
            override fun onClick(p: View?) {
                mlistener.onPillClick(adapterPosition)
            }
        }
    }
    override fun onPillClick(position: Int) {
        println("The position is " + position)
        println(displayList[position])
        searchEngine.setQuery(displayList[position], true)
    }
}
