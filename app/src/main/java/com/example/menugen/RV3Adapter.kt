package com.example.menugen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//import android.content.Context
//import android.view.View
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//
//class RV3Adapter(val context: Context, val recommendMenuList, ArrayList<Recommend_Menu>) :
//    RecyclerView.Adapter<>() {
//
//        inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
//            val largeMenuPhotoImg = itemView?.findViewById<ImageView>(R.id.largeMenuPhotoImg)
//            val largeMenuTv = itemView?.findViewById<TextView>(R.id.largeMenuTv)
//            val smallMenuTv = itemView?.findViewById<TextView>(R.id.smallMenuTv)
//
//            fun bind (recommendMenu: Recommend_Menu, context: Context) {
//                if (Recommend_Menu.photo)
//            }
//
//        }
//}
class RV3Adapter(val context: Context, val recommendMenuList: ArrayList<Recommend_Menu>) :
    RecyclerView.Adapter<RV3Adapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_item3, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return recommendMenuList.size
    }

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        holder?.bind(recommendMenuList[position], context)
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val largeMenuPhotoImg = itemView?.findViewById<ImageView>(R.id.largeMenuPhotoImg)
        val largeMenuTv = itemView?.findViewById<TextView>(R.id.largeMenuTv)
        val smallMenuTv = itemView?.findViewById<TextView>(R.id.smallMenuTv)

        fun bind (recommendMenuList: Recommend_Menu, context: Context) {
            /* recommendMenuListPhoto의 setImageResource에 들어갈 이미지의 id를 파일명(String)으로 찾고,
          이미지가 없는 경우 안드로이드 기본 아이콘을 표시*/
            if (recommendMenuList.photo != "") {
                val resourceId = context.resources.getIdentifier(recommendMenuList.photo, "drawable", context.packageName)
                largeMenuPhotoImg?.setImageResource(resourceId)
            } else {
                largeMenuPhotoImg?.setImageResource(R.mipmap.ic_launcher)
            }
            largeMenuTv?.text = recommendMenuList.largeMenuName
            smallMenuTv?.text = recommendMenuList.smallMenuName
        }
    }
}