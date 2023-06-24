package com.jindvir.stopwatch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class RecordAdapter(private var itemlist : ArrayList<LapModel>):RecyclerView.Adapter<RecordAdapter.ViewHolder>() {

//    private var onClickListener: OnClickListener? = null

    fun setFilteredList(itemlist: ArrayList<LapModel>){
        this.itemlist = itemlist
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.lap_item_record, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = itemlist[position]


        // sets the text to the textview from our itemHolder class
        holder.lapNameView.text = itemsViewModel.lapNum
        holder.lapValRecordView.text = itemsViewModel.lapVal

    }

    override fun getItemCount(): Int {
        return itemlist.size
    }


    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val lapNameView: TextView = itemView.findViewById(R.id.lap_name)
        val lapValRecordView: TextView = itemView.findViewById(R.id.lap_val_record)
    }
}