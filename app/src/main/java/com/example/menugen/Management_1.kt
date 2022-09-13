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
        val url = "http://172.25.244.84:27017/"
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

        // 사용자가 선택한 식단의 시간대가 아침/점심/저녁 중 어느것인지에 대한 정보가 담긴 변수
        val user_choice_time = intent.getStringExtra("time")

        // 처음 대분류 & 중분류에 들어갈 음식들
        var Large_food = ""
        val Large_food_list = mutableListOf<String>("선택", "밥", "면 및 만두", "국(탕)", "찌개", "찜", "조림", "구이", "전", "튀김", "볶음", "무침", "김치", "떡",
            "우유 및 유제품", "음료", "젓갈", "죽", "회", "과자 및 빵", "술", "원재료", "장","절임")
        // 추후 서버에서 받아오는 데이터로 변경 예정
        // 추가부분은 반드시 아래 ArrayAdapter보다 위에 있어야 함

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
                        // Toast.makeText(this@Management1Activity, "대분류를 선택해주세요", Toast.LENGTH_SHORT).show()
                    }
                    else if(position != 0){
                        Large_food = Large_food_list[position].toString()
                        // Toast.makeText(this@Management1Activity, Large_food, Toast.LENGTH_SHORT).show()

                        // 중분류를 담을 변수
                        var Middel_food_list = mutableListOf<String>()

                        if(Large_food == "밥"){
                            Middel_food_list = mutableListOf<String>("선택","쌀밥", "잡곡밥", "채소밥", "비빔밥", "덮밥", "김(초)밥")

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
                        else if(Large_food == "면 및 만두"){
                            Middel_food_list = mutableListOf<String>("선택","면", "만두")
                        } else if(Large_food == "국(탕)"){
                            Middel_food_list = mutableListOf<String>("선택","맑은국", "된장국", "곰국 및 탕", "냉국")
                        } else if(Large_food == "찌개"){
                            Middel_food_list = mutableListOf<String>("선택","어패류찌개", "육류찌개", "된장찌개", "전골", "기타찌개")
                        } else if(Large_food == "찜"){
                            Middel_food_list = mutableListOf<String>("선택","어패류찜", "육류찜", "채소류찜", "기타 찜")
                        } else if(Large_food == "조림"){
                            Middel_food_list = mutableListOf<String>("선택","어패류조림", "육류 및 난류조림", "채소류조림", "기타 조림")
                        } else if(Large_food == "구이"){
                            Middel_food_list = mutableListOf<String>("선택","어패류구이", "육류구이","기타 구이")
                        } else if(Large_food == "전"){
                            Middel_food_list = mutableListOf<String>("선택","어패류전", "육류전","채소류전", "기타 전")
                        } else if(Large_food == "튀김"){
                            Middel_food_list = mutableListOf<String>("선택","어패류튀김", "육류튀김","채소류튀김", "기타 튀김")
                        } else if(Large_food == "볶음"){
                            Middel_food_list = mutableListOf<String>("선택","어패류볶음", "육류 및 난류볶음","채소 및 해조류볶음", "곡류 및 두류볶음", "기타 볶음")
                        } else if(Large_food == "무침"){
                            Middel_food_list = mutableListOf<String>("선택","숙채", "어패류무침","육류무침", "생채(샐러드)")
                        } else if(Large_food == "김치"){
                            Middel_food_list = mutableListOf<String>("선택","김치류")
                        } else if(Large_food == "떡"){
                            Middel_food_list = mutableListOf<String>("선택","떡류")
                        } else if(Large_food == "우유 및 유제품"){
                            Middel_food_list = mutableListOf<String>("선택","우유 및 유제품류")
                        } else if(Large_food == "음료"){
                            Middel_food_list = mutableListOf<String>("선택","과채", "기타 음료")
                        } else if(Large_food == "젓갈"){
                            Middel_food_list = mutableListOf<String>("선택","젓갈류")
                        } else if(Large_food == "죽"){
                            Middel_food_list = mutableListOf<String>("선택","죽류")
                        } else if(Large_food == "회"){
                            Middel_food_list = mutableListOf<String>("선택","어패회", "육류회", "채소류회")
                        } else if(Large_food == "과자 및 빵"){
                            Middel_food_list = mutableListOf<String>("선택","빵", "과자")
                        } else if(Large_food == "술"){
                            Middel_food_list = mutableListOf<String>("선택","주류")
                        } else if(Large_food == "원재료"){
                            Middel_food_list = mutableListOf<String>("선택","감자 및 전분", "견과 및 종실", "과일 및 과일가공품", "난류", "당류", "두류", "소스",
                                "어패류 및 수산물가공품", "유지류", "조리가공식품", "조미료", "채소", "해조류")
                        } else if(Large_food == "장"){
                            Middel_food_list = mutableListOf<String>("선택","양념")
                        } else if(Large_food == "절임"){
                            Middel_food_list = mutableListOf<String>("선택","장아찌", "절임류")
                        }

                        // 스피너에 추가된 중분류가 보이도록 설정
                        val middle_food_adapter = ArrayAdapter(this@Management1Activity, android.R.layout.simple_list_item_1,Middel_food_list)
                        binding.middleMenuSpinner.adapter = middle_food_adapter

                        items.clear()
                        // RecyclerView 활용을 위한 코드
                        val recycler = findViewById<RecyclerView>(R.id.foodlist)
                        val rvAdapter = RVAdapter(items)
                        recycler.adapter = rvAdapter

                        recycler.layoutManager = LinearLayoutManager(this@Management1Activity)
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
            db.dao().insert(Entity(final_list))
        }

        var del_list = ""
        // 사용자가 최종 음식 목록에서 음식을 제거했을 때
//        if (f_final != null){
//            del_list = f_final.toString()
//            db.dao().deleteUser(Entity(del_list))
//        }

        if(db.dao().getTitle().isEmpty() == false){
            var index = 0

//            if (f_final != null){
//                del_list = f_final.toString()
//                db.dao().deleteUser(Entity(del_list))
//            }

            while (index < db.dao().getTitle().size) {
                val foodtext = db.dao().getTitle()[index]
                items2.add(foodtext)
                index++
                Log.d("while 테스트", index.toString())
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
            db.dao().deleteAllUsers()

//            var uid = AutoLogin.getUserId(this)
//            val user_choice_time = intent.getStringExtra("time")
            var uid = "user20"

            server.requestMng(uid, user_choice_time.toString(), 123)
                .enqueue(object : Callback<Join> {
                    override fun onFailure(call: Call<Join>, t: Throwable) {
                        Log.d("실패", "정보: $uid, $user_choice_time")
                    }

                    override fun onResponse(call: Call<Join>, response: Response<Join>) {
                        val serverCheck = response.body()
                        Log.d("??", "??: $serverCheck.code")
                        if(serverCheck?.code==200){
                            val recycler = findViewById<RecyclerView>(R.id.finallist)
                            val rvAdapter = RVAdapter(items2)
                            recycler.adapter = rvAdapter

                            recycler.layoutManager = LinearLayoutManager(this@Management1Activity)

                            val nextintent = Intent(this@Management1Activity,SettingActivity::class.java)
                            nextintent.putExtra("userFoodList", items2.toString())
                            nextintent.putExtra("userTime", user_choice_time)
                            Log.d("식단 시간 확인", user_choice_time.toString())
                            Log.d("성공!", "정보: $uid, $user_choice_time, $items2")
                            items2.clear()
                            startActivity(nextintent)
                        }
                        else{
                            Toast.makeText(this@Management1Activity, "실패! $serverCheck.code", Toast.LENGTH_LONG).show()
                        }
                    }
                })
        }
    }
}