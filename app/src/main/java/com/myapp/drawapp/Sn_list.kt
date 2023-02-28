package com.myapp.drawapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.NonDisposableHandle.parent

class Sn_list(private val newsList3: ArrayList<serial_class>):
    RecyclerView.Adapter<Sn_list.MyViewHolder>()
{
    class MyViewHolder (itemView: View):RecyclerView.ViewHolder(itemView){

        val lvtvsn : TextView = itemView.findViewById(R.id.lv_tv_sn)
        val lvtvmodel : TextView = itemView.findViewById(R.id.lv_tv_model)
        //adding a checkbox
        val lvtvtype : TextView = itemView.findViewById(R.id.lv_tv_type)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.serials ,
            parent ,false)
        return Sn_list.MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem= newsList3[position]
        holder.lvtvsn.text=currentItem.number
        holder.lvtvmodel.text=currentItem.model
        holder.lvtvtype.text=currentItem.status
    }

    override fun getItemCount(): Int {
        return newsList3.size
    }


}


