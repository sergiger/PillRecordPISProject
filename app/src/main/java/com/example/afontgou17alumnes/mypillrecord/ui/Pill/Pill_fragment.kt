package com.example.afontgou17alumnes.mypillrecord.ui.Pill

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.controller.Controller
import com.example.afontgou17alumnes.mypillrecord.data.model.therapy.Therapy
import kotlinx.android.synthetic.main.pill_fragment_fragment.*

class Pill_fragment : Fragment() {
    lateinit var actualTherapy : Therapy

    companion object {
        fun newInstance() =
            Pill_fragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Controller.main_activity_fragment=4
        //Button btn1 = (Button) view.findViewById()
        return inflater.inflate(R.layout.pill_fragment_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        createTherapyList()

        btn_add_therapy.setOnClickListener{
            val intent = Intent(activity,Pillplanificar::class.java)
            startActivity(intent)
        }

        therapy_list.setOnItemClickListener { adapterView, view, i, l ->
            var therapy : Therapy = adapterView.adapter.getItem(i) as Therapy
            actualTherapy = therapy
            val intent = Intent(context, TherapyInformation::class.java)
            intent.putExtra("Therapy", therapy)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 1){
            if (resultCode == Activity.RESULT_OK) {
                val result = data?.getSerializableExtra("Therapy") as Therapy
                //Modify actualTherapy
                actualTherapy.deleteReminders()
                Controller.deleteTherapyByID(actualTherapy.id)
                Controller.addTherapy(result)
                result.createReminders()
            }
            else if (resultCode == 2){
                //Eliminar therapy
                actualTherapy.deleteReminders()
                Controller.deleteTherapyByID(actualTherapy.id)
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //do nothing
            }
        }
        createTherapyList()
    }

    fun createTherapyList(){
        val therapyListView = view?.findViewById<ListView>(R.id.therapy_list)
        val therapyAdapter = TherapyListAdapter(this, Controller.user.therapies)
        if(therapyListView != null){
            therapyListView.adapter = therapyAdapter
        }
    }
}
