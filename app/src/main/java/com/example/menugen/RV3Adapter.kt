package com.example.menugen

//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView

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
//class RV3Adapter(val context: Context, val recommendMenuList: ArrayList<Recommend_Menu>) :
//    RecyclerView.Adapter<RV3Adapter.Holder>() {
//    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
//        val view = LayoutInflater.from(context).inflate(R.layout.rv_item3, parent, false)
//        return Holder(view)
//    }
//
//    override fun getItemCount(): Int {
//        return recommendMenuList.size
//    }
//
//    override fun onBindViewHolder(holder: Holder?, position: Int) {
//        holder?.bind(recommendMenuList[position], context)
//    }
//
//    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
//        val largeMenuPhotoImg = itemView?.findViewById<ImageView>(R.id.largeMenuPhotoImg)
//        val largeMenuTv = itemView?.findViewById<TextView>(R.id.largeMenuTv)
//        val smallMenuTv = itemView?.findViewById<TextView>(R.id.smallMenuTv)
//
//        fun bind (recommendMenuList: Recommend_Menu, context: Context) {
//            /* recommendMenuListPhoto의 setImageResource에 들어갈 이미지의 id를 파일명(String)으로 찾고,
//          이미지가 없는 경우 안드로이드 기본 아이콘을 표시*/
//            if (recommendMenuList.photo != "") {
//                val resourceId = context.resources.getIdentifier(recommendMenuList.photo, "drawable", context.packageName)
//                largeMenuPhotoImg?.setImageResource(resourceId)
//            } else {
//                largeMenuPhotoImg?.setImageResource(R.mipmap.ic_launcher)
//            }
//            largeMenuTv?.text = recommendMenuList.largeMenuName
//            smallMenuTv?.text = recommendMenuList.smallMenuName
//        }
//    }
//}
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


// ProfileAdpater에 ViewHolder를 같이 inner class로 만들어주었으며, ProfileData를 만들어 data를 view와 연결
class RV3Adapter(private val context: Context) : RecyclerView.Adapter<RV3Adapter.ViewHolder>() {

    var datas = mutableListOf<RecommendData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_item3,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val largeMenuTv: TextView = itemView.findViewById(R.id.largeMenuTv)
        private val smallMenuTv: TextView = itemView.findViewById(R.id.smallMenuTv)
        private val imgProfile: ImageView = itemView.findViewById(R.id.largeMenuPhotoImg)

        fun bind(item: RecommendData) {
            largeMenuTv.text = item.largeMenuName
            smallMenuTv.text = item.smallMenuName
            Glide.with(itemView).load(item.photo).into(imgProfile)

            itemView.setOnClickListener {
                Intent(context, EvaluationActivity::class.java).apply {
//                    putExtra("data", item)
//                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { context.startActivity(this) }
            }

        }
    }


}