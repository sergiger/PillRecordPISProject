package com.example.afontgou17alumnes.mypillrecord.ui.calendar.List

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.ActivityReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.MeasurementReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.MedicineReminder
import com.example.afontgou17alumnes.mypillrecord.data.model.reminder.Reminder
import java.time.LocalDate
import java.util.*

/**
 * Created by P.Thinesh on 30/11/2016.
 */
class CustomAdapter(context: Context?, reminderList: ArrayList<Any>) : BaseAdapter() { // pass activity.context
    companion object {
        private const val TYPE_ITEM = 0
        private const val TYPE_HEADER = 1
        private const val TYPE_OCURRENCE = 2
    }
    private val mData: ArrayList<Any> = reminderList
    //private val sectionHeader = TreeSet<Int>()
    private val mInflater: LayoutInflater

    init {
        mInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    /** Adiciona row item a la lista*/
    fun addItem(item: Any) {
        mData.add(item)
        notifyDataSetChanged()
    }

    /** Adiciona header item a la lista*/
    fun addSectionHeaderItem(item: Any) {
        mData.add(item)
        //sectionHeader.add(mData.size - 1)
        notifyDataSetChanged()
    }

    /** Retorna el tipo del item */
    override fun getItemViewType(position: Int): Int {
        return if (mData[position] is LocalDate) TYPE_HEADER else TYPE_ITEM
    }

    /** Retorna el numero de tipos de la lista */
    override fun getViewTypeCount(): Int {
        return 2
    }

    /** Retorna el numero de row items de la lista */
    override fun getCount(): Int {
        return mData.size
    }

    /** Retorna el row item espeficado */
    override fun getItem(position: Int): Any {
        return mData[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
        var holder: ViewHolder? = null
        val rowType = getItemViewType(position)
        if (convertView == null) {
            holder = ViewHolder()
            when (rowType) {
                TYPE_ITEM -> {
                    convertView = mInflater.inflate(R.layout.historic_row_item, null)
                    holder.textPillDate = convertView.findViewById<View>(R.id.txt_PillDate) as TextView
                    holder.textPillName = convertView.findViewById<View>(R.id.txt_PillName) as TextView
                    holder.textPillInfo = convertView.findViewById<View>(R.id.txt_PillInfo) as TextView
                    holder.textPillCheck = convertView.findViewById<View>(R.id.txt_Check) as TextView
                    holder.imageReminder = convertView.findViewById<View>(R.id.image_reminder_list) as ImageView

                }
                TYPE_HEADER -> {
                    convertView = mInflater.inflate(R.layout.historic_header_item, null)
                    holder.dateSeparator = convertView.findViewById<View>(R.id.dateSeparator) as TextView
                }
                TYPE_OCURRENCE -> {
                    TODO()
                }
            }
            convertView?.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        if (rowType == TYPE_ITEM) {
            var i = mData[position]
            if (i is Reminder) {
                holder.textPillDate?.text = i.getReminderDate().toString()
                holder.textPillName?.text = i.getReminderName()
                holder.textPillHour?.text = i.getHour().toString()
                if (i is MedicineReminder) {
                    holder.textPillInfo?.text = i.dose.toString() + " " + i.doseUnit
                    holder.imageReminder?.setImageResource(R.drawable.pill)
                } else if (i is MeasurementReminder) {
                    holder.textPillInfo?.visibility = View.INVISIBLE
                    holder.imageReminder?.setImageResource(R.drawable.pulse)
                } else if (i is ActivityReminder) {
                    holder.textPillInfo?.text = i.duration.toString()
                    holder.imageReminder?.setImageResource(R.drawable.olimpic)
                }
            }

        } else if (rowType == TYPE_HEADER) {
            holder.dateSeparator?.text = mData[position].toString()
        }
        return convertView
    }

    class ViewHolder {
        var dateSeparator: TextView? = null
        var textPillDate: TextView? = null
        var textPillName: TextView? = null
        var textPillInfo: TextView? = null
        var textPillHour: TextView? = null
        var textPillCheck: TextView? = null
        var imageReminder: ImageView? = null
    }
}