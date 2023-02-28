package com.myapp.drawapp

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView

class Myadapter(private val newsList: ArrayList<Field_update>):
    RecyclerView.Adapter<Myadapter.MyViewHolder>()      {
    private var isOnTextChanged : Boolean = false

    class MyViewHolder (itemView: View):RecyclerView.ViewHolder(itemView){

        //val lvtvsn:
        val tvquestion : TextView = itemView.findViewById(R.id.tv_question)
        val etanswer : EditText = itemView.findViewById(R.id.etHeading)
        //adding a checkbox
        var check1 : CheckBox = itemView.findViewById(R.id.check1)



    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_items ,
            parent ,false)
        return MyViewHolder(itemView)

    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem= newsList[position]


        holder.tvquestion.text=currentItem.question

        Log.d("checkboxb","$newsList")
            //set on clicklisterner to pass a value from checkbox inside ArrayList of recyclerview
        holder.check1.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?){
                    if(holder.check1.isChecked){
                        newsList[holder.adapterPosition].check1="Pass"
                    }
                    else{
                        newsList[holder.adapterPosition].check1="Fail"

                    }
                    notifyDataSetChanged()
                    Log.d("checkboxlistener","$newsList")

                }
            })





        holder.etanswer.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            val id = holder.adapterPosition
                newsList[id].heading=holder.etanswer.text.toString()





            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })




    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }





    }






