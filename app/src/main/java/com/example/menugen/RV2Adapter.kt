package com.example.menugen

import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.menugen.databinding.RvItemBinding
import kotlinx.android.synthetic.main.rv_item.view.*
import org.w3c.dom.Text

class RV2Adapter(val items:MutableList<String>) : RecyclerView.Adapter<RV2Adapter.ViewHolder>(){
    val recommend: Recommend ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RV2Adapter.ViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item2, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RV2Adapter.ViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindItem(item : String){
            val rv_text = itemView.findViewById<TextView>(R.id.rv2text)
            rv_text.text = item
            val rv_btn = itemView.findViewById<Button>(R.id.rv2delBtn)

            rv_btn.setOnClickListener{
                var foodlist=item

                // Activity로 사용자가 선택한 음식명 전달
                val rv2intent = Intent(itemView.context, Management1Activity::class.java)
                rv2intent.putExtra("final", foodlist)
                itemView.context.startActivity(rv2intent)
            }
        }
    }
}
