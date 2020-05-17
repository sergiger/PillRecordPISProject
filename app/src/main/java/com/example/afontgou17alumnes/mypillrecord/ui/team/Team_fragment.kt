package com.example.afontgou17alumnes.mypillrecord.ui.team

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.afontgou17alumnes.mypillrecord.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.team_fragment_fragment.*

class Team_fragment : Fragment() {

    companion object {
        fun newInstance() =
            Team_fragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.team_fragment_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel

        btn_share.setOnClickListener {
            /*val intent = Intent(activity, share_team::class.java)
            startActivity(intent)*/
            ChoseShare()
            //Toast.makeText(activity,"Share With", Toast.LENGTH_SHORT).show()
        }
        btn_agenda.setOnClickListener {
            //val intent = Intent(activity, agenda_team::class.java)
            //startActivity(intent)
            Toast.makeText(activity,"Agenda", Toast.LENGTH_SHORT).show()
        }
    }
    fun ChoseShare(){
        val llista = arrayListOf<String>("Share with","Stop sharing with") /*, "Follow-up request to"*/
        val cs: Array<CharSequence> = llista.toArray(arrayOfNulls<CharSequence>(llista.size))
        val builder = AlertDialog.Builder(context)
        builder.setTitle("SHARE OPTIONS")
            .setItems(cs, DialogInterface.OnClickListener { dialog, which ->
                when(which){
                    0->{val intent = Intent(activity, share_team::class.java)
                        startActivity(intent)
                    }
                    1->{val intent = Intent(activity, agenda_team::class.java)
                        startActivity(intent)
                    }
                    else->{

                    }
                }
                })
        builder.create()
        builder.show()


        /*
        val db = FirebaseFirestore.getInstance()
                        db.collection("users")
                            .whereEqualTo("email", "sergiger00@gmail.com")
                            .get()
                            .addOnSuccessListener { documents ->
                                for (document in documents) {
                                    Log.e("DOCUMENTS", "${document.id} => ${document.data}")
                                }
                            }
                            .addOnFailureListener { exception ->
                                Log.e("DOCUMENTS", "Error getting documents: ", exception)
                            }
         */
    }




}
