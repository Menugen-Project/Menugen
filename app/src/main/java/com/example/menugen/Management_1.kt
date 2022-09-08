package com.example.menugen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.menugen.databinding.ActivityInfoBinding
import com.example.menugen.databinding.ActivityManagement1Binding
import com.example.menugen.databinding.ActivityRecommendBinding
import com.example.menugen.databinding.ActivitySettingBinding
import kotlinx.android.synthetic.main.activity_management1.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Management1Activity : AppCompatActivity() {
    // 데이터 바인딩
    private lateinit var binding: ActivityManagement1Binding
    private lateinit var adapter: RVAdapter
    private lateinit var adapter2: RV2Adapter

    private var items = mutableListOf<String>()
    private var items2 = mutableListOf<String>()

//    var db : AppDatabase?=null
//
//    companion object{
//        private var instance:MainActivity? = null
//        fun getInstance(): MainActivity? {
//            return instance
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_management1)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_management1)

//        Log.d("로그","${db?.dao()?.getTitle().toString()}")

        // 서버 연동코드 <---
        val url = "http://220.149.236.48:27017/"
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var server = retrofit.create(ManageFoodList::class.java)
        // <---

        // 뷰모델 선언 및 연결
        // val userViewModel = ViewModelProvider(this,UserViewModel.Factory(application)).get(UserViewModel::class.java)

        // 어댑터 연결
        adapter = RVAdapter(items)
        adapter2 = RV2Adapter(items)
        binding.finallist.adapter = adapter2

        // DB 연결
        val db = Room.databaseBuilder(
            applicationContext, AppDatabase::class.java, "database"
        ).allowMainThreadQueries().build()



        // 처음 대분류 & 중분류에 들어갈 음식들
        var Large_food = ""
        val Large_food_list = mutableListOf<String>()
        // 추후 서버에서 받아오는 데이터로 변경 예정
        // 추가부분은 반드시 아래 ArrayAdapter보다 위에 있어야 함
        Large_food_list.add("선택")
        Large_food_list.add("밥")
        Large_food_list.add("면 및 만두")
        Large_food_list.add("국")

        val food_adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, Large_food_list)
        binding.largeMenuSpinner.adapter = food_adapter

        binding.largeMenuSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if(position == 0){

                    }
                    else if(position != 0){
                        Large_food = Large_food_list[position].toString()

                        if(Large_food == "밥"){
                            items.clear()
                            items.add("감자밥")
                            items.add("검은콩밥")
                            items.add("계란덮밥")
                            // adapter.notifyDataSetChanged()

                            // RecyclerView 활용을 위한 코드
                            val recycler = findViewById<RecyclerView>(R.id.foodlist)
                            val rvAdapter = RVAdapter(items)
                            recycler.adapter = rvAdapter

                            recycler.layoutManager = LinearLayoutManager(this@Management1Activity)
                        }
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        // 음식 목록에서 사용자가 선택한 음식에 대한 정보
        val f_list = intent.getStringExtra("list")
        // 최종 선택 메뉴에서 사용자가 제거한 음식에 대한 정보
        val f_final = intent.getStringExtra("final")

        var final_list = ""
        // 사용자가 음식 목록에서 음식을 추가했을 때
        if (f_list != null){
            final_list = f_list.toString()
            db.dao().insert(Entity(f_list.toString()))
        }

        var del_list = ""

        if(db.dao().getTitle().isEmpty() == false){
            var index = 0

            if (f_final != null){
                del_list = f_final.toString()
                db.dao().deleteUser(Entity(f_final.toString()))
                Log.d("DB확인", "${db?.dao()?.getTitle().toString()}")
//                items2.remove(del_list)
            }

            while (index < db.dao().getTitle().size) {
                val foodtext = db.dao().getTitle()[index]
                items2.add(foodtext)
                index++
                // binding.finalfood.text = foodtext
            }
            // binding.finalfood.text = db.dao().getTitle().toString()

            val recycler2 = findViewById<RecyclerView>(R.id.finallist)
            val rv2Adapter = RV2Adapter(items2)
            recycler2.adapter = rv2Adapter

            recycler2.layoutManager = LinearLayoutManager(this@Management1Activity)
        }

        // DB 초기화 + 식단 저장 + 화면 전환(management1으로)
        binding.SettingFinBtn.setOnClickListener{
            var uid = AutoLogin.getUserId(this)
            var utime:String = "아침"
            val UserFoodList = db.dao().getTitle()

            val recycler = findViewById<RecyclerView>(R.id.finallist)
            val rvAdapter = RVAdapter(items2)
            recycler.adapter = rvAdapter

            recycler.layoutManager = LinearLayoutManager(this@Management1Activity)

            val nextintent = Intent(this,SettingActivity::class.java)
            nextintent.putExtra("user_morning", items2.toString())
            Log.d("최종 음식목록 테스트", items2.toString())
            items2.clear()

            server.requestMng(uid, utime, UserFoodList)
                .enqueue(object :Callback<Join>{
                    override fun onFailure(call: Call<Join>, t: Throwable) {
                        Log.d(
                            "연결 실패", "정보 $uid, $utime, $UserFoodList")
                    }

                    override fun onResponse(call: Call<Join>, response: Response<Join>) {
                        val surveyCheck = response.body()
                        if(surveyCheck?.code==200) {
                            Log.d(
                                "성공",
                                "정보 $uid, $utime, $UserFoodList"
                            )
                            startActivity(nextintent)
                        }
                        else{
                            Toast.makeText(this@Management1Activity, "아이디 중복검사를 진행해주세요!",
                                Toast.LENGTH_LONG).show()
                        }
                    }
                })
            startActivity(nextintent)
        }
    }
}