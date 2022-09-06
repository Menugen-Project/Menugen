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

class RVAdapter(val items:MutableList<String>) : RecyclerView.Adapter<RVAdapter.ViewHolder>(){
    val recommend: Recommend ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVAdapter.ViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RVAdapter.ViewHolder, position: Int) {
        holder.bindItem(items[position])

        // https://mechacat.tistory.com/7 <---
        // (1) 리스트 내 항목 클릭 시 onClick() 호출
//        holder.itemView.setOnClickListener {
//            itemClickListener.onClick(it, position)
//        }
    }
    // Activity로 값 가져가기
    // (2) 리스너 인터페이스
//    interface OnItemClickListener {
//        fun onClick(v: View, position: Int)
//    }
    // (3) 외부에서 클릭 시 이벤트 설정
//    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
//        this.itemClickListener = onItemClickListener
//    }
    // (4) setItemClickListener로 설정한 함수 실행
//    private lateinit var itemClickListener : OnItemClickListener
    // <---

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        // DB 연결
//        val db = Room.databaseBuilder(
//            App.instance, AppDatabase::class.java, "database"
//        ).allowMainThreadQueries().build()

        fun bindItem(item : String){
            val rv_text = itemView.findViewById<TextView>(R.id.rvItem)
            rv_text.text = item
            val rv_btn = itemView.findViewById<Button>(R.id.addbtn)

            rv_btn.setOnClickListener{
                var foodlist=item

                // db.dao().insert(Entity(edit.text.toString()))

                // Activity로 사용자가 선택한 음식명 전달
                val rv1intent = Intent(itemView.context, Management1Activity::class.java)
                rv1intent.putExtra("list", foodlist)
                itemView.context.startActivity(rv1intent)
            }
        }
    }
}

