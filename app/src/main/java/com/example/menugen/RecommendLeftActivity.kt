package com.example.menugen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.menugen.databinding.ActivityRecommendBinding
import com.example.menugen.databinding.ActivityRecommendLeftBinding
import kotlinx.android.synthetic.main.activity_recommend.*

class RecommendLeftActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecommendLeftBinding
    lateinit var RV3Adapter: RV3Adapter
    val datas = mutableListOf<RecommendData>()

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, RecommendLeftActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommend_left)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_recommend_left)

        // 리사이클러뷰
        initRecycler()

        // 하단바 각각 액티비티로 이동
        binding.btnRecommend.setOnClickListener {
            val intent = Intent(this, Recommend::class.java)
            startActivity(intent)
        }
        binding.btnSetting.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
        binding.btnInfo.setOnClickListener {
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
        }
    }

    // RecyclerView를 사용하기 위해서는 위에서 만들어준 Adpater와 RecyclerView를 연결해주어야함.
    // 또한 서버와 연결하는 것이 아니라면 더미 data를 Adapter에 넣어줘야함.
    private fun initRecycler() {
        // 서버에서 보내준 추천 식단 확인
        // var foodList = intent.getSerializableExtra("foodList") as ArrayList<String>
        // Log.d("확인", foodList.toString())

//        val food1 = intent.getStringExtra("food1")
//        val food2 = intent.getStringExtra("food2")
//        val food3 = intent.getStringExtra("food3")
//        val food4 = intent.getStringExtra("food4")

        RV3Adapter = RV3Adapter(this)
        rv_menu.adapter = RV3Adapter

        datas.apply {
//            add(RecommendData(photo = R.drawable.rice, largeMenuName = "대분류: 밥류", smallMenuName = food1.toString()))
//            add(RecommendData(photo = R.drawable.jjigae, largeMenuName = "대분류: 찌개류", smallMenuName = food2.toString()))
//            add(RecommendData(photo = R.drawable.kimchi, largeMenuName = "대분류: 김치류", smallMenuName = food3.toString()))
//            add(RecommendData(photo = R.drawable.muchim, largeMenuName = "대분류: 무침류", smallMenuName = food4.toString()))

            add(RecommendData(photo = R.drawable.juk, largeMenuName = "대분류: 죽류", smallMenuName = "임시"))
            add(RecommendData(photo = R.drawable.jerim, largeMenuName = "대분류: 절임류", smallMenuName = "임시"))
            add(RecommendData(photo = R.drawable.kimchi, largeMenuName = "대분류: 김치류", smallMenuName = "임시"))
            add(RecommendData(photo = R.drawable.jorim, largeMenuName = "대분류: 조림류", smallMenuName = "임시"))

            RV3Adapter.datas = datas
            RV3Adapter.notifyDataSetChanged()

        }
    }


    // 뒤로가기를 일정시간 내에 두번 입력하는 것 감지하는 함수
    fun runDelayed(millis: Long, function: () -> Unit){
        Handler(Looper.getMainLooper()).postDelayed(function, millis)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit)
    }
}