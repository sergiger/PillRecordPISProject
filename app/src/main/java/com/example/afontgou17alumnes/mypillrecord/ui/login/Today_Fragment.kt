package com.example.afontgou17alumnes.mypillrecord.ui.login

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.activity_Register4
import kotlinx.android.synthetic.main.today__fragment.*

class Today_Fragment : Fragment() {

    companion object {
        fun newInstance() = Today_Fragment()

    }

    private lateinit var viewModel: TodayViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.today__fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TodayViewModel::class.java)
        // TODO: Use the ViewModel
        btnProvetes.setOnClickListener(object: View.OnClickListener {
            //Toast.makeText(this,"asdfasfdsf",Toast.LENGTH_SHORT).show()
            override fun onClick(view: View): Unit {
                // Handler code here.
                val intent = Intent(context, activity_Register4::class.java);
                startActivity(intent);
            }
        })

    }


}
