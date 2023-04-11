package com.example.ghichu.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ghichu.Object.Note
import com.example.ghichu.R
import com.example.ghichu.RcvOnClick

class AdapterRcv(var list: ArrayList<Note>): RecyclerView.Adapter<AdapterRcv.ViewHolder>() {
    private lateinit var mListener:RcvOnClick
    private lateinit var mLongListener:RcvOnClick
    fun setOnClick(listener:RcvOnClick){
        this.mListener=listener
    }
    fun setOnLongClick(LongListener:RcvOnClick){
        this.mLongListener=LongListener
    }

    class ViewHolder(view : View,Listener:RcvOnClick,LongListener: RcvOnClick):RecyclerView.ViewHolder(view){
        init {
            view.setOnClickListener{
                Listener.ClickItem(adapterPosition)
            }
            view.setOnLongClickListener {
                LongListener.LongClickItem(adapterPosition)
                return@setOnLongClickListener true
            }
        }
        var items:TextView=view.findViewById(R.id.tv_item)
        var title:TextView=view.findViewById(R.id.tv_item_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view:View=LayoutInflater.from(parent.context).inflate(R.layout.items,parent,false)
        return ViewHolder(view,mListener,mLongListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.items.text=list[position].getContent()
        holder.title.text=list[position].getTitle()

    }
}

