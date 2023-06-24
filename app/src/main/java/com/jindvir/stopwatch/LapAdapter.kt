package com.jindvir.stopwatch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class LapAdapter(private val itemlist : ArrayList<LapModel>):RecyclerView.Adapter<LapAdapter.ViewHolder>() {

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.lap_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = itemlist[position]


        // sets the text to the textview from our itemHolder class
        holder.lapNumView.text = itemsViewModel.lapNum
        holder.lapValView.text = itemsViewModel.lapVal
        holder.lapRl.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, itemsViewModel )

            }

        }
    }

    internal interface SendMessage {
        fun sendData(message: String?)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    // onClickListener Interface
    interface OnClickListener {
        fun onClick(position: Int, model: LapModel)
    }

    override fun getItemCount(): Int {
        return itemlist.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val lapNumView: TextView = itemView.findViewById(R.id.lap_num)
        val lapValView: TextView = itemView.findViewById(R.id.lap_val)
        val lapRl: RelativeLayout = itemView.findViewById(R.id.rl)

    }

}