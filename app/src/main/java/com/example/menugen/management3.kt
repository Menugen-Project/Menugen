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
import androidx.room.Room
import com.example.menugen.databinding.ActivityManagement3Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate

class Management3Activity : AppCompatActivity() {
    // 데이터 바인딩
    private lateinit var binding: ActivityManagement3Binding
    private lateinit var adapter: RVDAdapter
    private lateinit var adapter2: RV2DAdapter

    private var items = mutableListOf<String>()
    private var items2 = mutableListOf<String>()

    private var user_choice_time = ""
    private var user_choice_date = ""

    // 오늘 날짜를 담을 변수
    var date: LocalDate = LocalDate.now()
    var strDate = date.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_management3)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_management3)

        // 서버 연동코드 <---
        val url = "여기에 서버주소"
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var server = retrofit.create(ManageFoodList::class.java)
        // <---

        // 어댑터 연결
        adapter = RVDAdapter(items)
        adapter2 = RV2DAdapter(items)
        binding.finallist.adapter = adapter2

        // DB 연결
        val db = Room.databaseBuilder(
            applicationContext, AppDatabase::class.java, "database"
        ).allowMainThreadQueries().build()

        // 사용자가 선택한 식단의 시간대가 아침/점심/저녁 중 어느것인지에 대한 정보가 담긴 변수
        val time = intent.getStringExtra("time")
        val date = intent.getStringExtra("date")

        if(time != null && time != ""){
            user_choice_time = time.toString()
        }
        if(date != null && date != ""){
            user_choice_date = date.toString()
        }
        Log.d("날짜/시간 확인", "$user_choice_date, $user_choice_time")


        // 처음 대분류 & 중분류에 들어갈 음식들
        var Large_food = ""
        val Large_food_list = mutableListOf<String>("선택", "밥", "면 및 만두", "국(탕)", "찌개", "찜", "조림", "구이", "전", "튀김", "볶음", "무침", "김치", "떡",
            "우유 및 유제품", "음료", "젓갈", "죽", "회", "과자 및 빵", "술", "원재료", "장","절임")
        var Middle_food = ""
        var Middle_food_list = mutableListOf<String>()
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

                    }
                    else if(position != 0){
                        Large_food = Large_food_list[position].toString()

                        if(Large_food == "밥"){
                            Middle_food_list = mutableListOf<String>("선택","쌀밥", "잡곡밥", "채소밥", "비빔밥", "덮밥", "김(초)밥")
                        }
                        else if(Large_food == "면 및 만두"){
                            Middle_food_list = mutableListOf<String>("선택","면", "만두")
                        } else if(Large_food == "국(탕)"){
                            Middle_food_list = mutableListOf<String>("선택","맑은국", "된장국", "곰국 및 탕", "냉국")
                        } else if(Large_food == "찌개"){
                            Middle_food_list = mutableListOf<String>("선택","어패류찌개", "육류찌개", "된장찌개", "전골", "기타찌개")
                        } else if(Large_food == "찜"){
                            Middle_food_list = mutableListOf<String>("선택","어패류찜", "육류찜", "채소류찜", "기타 찜")
                        } else if(Large_food == "조림"){
                            Middle_food_list = mutableListOf<String>("선택","어패류조림", "육류 및 난류조림", "채소류조림", "기타 조림")
                        } else if(Large_food == "구이"){
                            Middle_food_list = mutableListOf<String>("선택","어패류구이", "육류구이","기타 구이")
                        } else if(Large_food == "전"){
                            Middle_food_list = mutableListOf<String>("선택","어패류전", "육류전","채소류전", "기타 전")
                        } else if(Large_food == "튀김"){
                            Middle_food_list = mutableListOf<String>("선택","어패류튀김", "육류튀김","채소류튀김", "기타 튀김")
                        } else if(Large_food == "볶음"){
                            Middle_food_list = mutableListOf<String>("선택","어패류볶음", "육류 및 난류볶음","채소 및 해조류볶음", "곡류 및 두류볶음", "기타 볶음")
                        } else if(Large_food == "무침"){
                            Middle_food_list = mutableListOf<String>("선택","숙채", "어패류무침","육류무침", "생채(샐러드)")
                        } else if(Large_food == "김치"){
                            Middle_food_list = mutableListOf<String>("선택","김치류")
                        } else if(Large_food == "떡"){
                            Middle_food_list = mutableListOf<String>("선택","떡류")
                        } else if(Large_food == "우유 및 유제품"){
                            Middle_food_list = mutableListOf<String>("선택","우유 및 유제품류")
                        } else if(Large_food == "음료"){
                            Middle_food_list = mutableListOf<String>("선택","과채", "기타 음료")
                        } else if(Large_food == "젓갈"){
                            Middle_food_list = mutableListOf<String>("선택","젓갈류")
                        } else if(Large_food == "죽"){
                            Middle_food_list = mutableListOf<String>("선택","죽류")
                        } else if(Large_food == "회"){
                            Middle_food_list = mutableListOf<String>("선택","어패회", "육류회", "채소류회")
                        } else if(Large_food == "과자 및 빵"){
                            Middle_food_list = mutableListOf<String>("선택","빵", "과자")
                        } else if(Large_food == "술"){
                            Middle_food_list = mutableListOf<String>("선택","주류")
                        } else if(Large_food == "원재료"){
                            Middle_food_list = mutableListOf<String>("선택","감자 및 전분", "견과 및 종실", "과일 및 과일가공품", "난류", "당류", "두류", "소스",
                                "어패류 및 수산물가공품", "유지류", "조리가공식품", "조미료", "채소", "해조류")
                        } else if(Large_food == "장"){
                            Middle_food_list = mutableListOf<String>("선택","양념")
                        } else if(Large_food == "절임"){
                            Middle_food_list = mutableListOf<String>("선택","장아찌", "절임류")
                        }

                        // 스피너에 추가된 중분류가 보이도록 설정
                        val middle_food_adapter = ArrayAdapter(this@Management3Activity, android.R.layout.simple_list_item_1,Middle_food_list)
                        binding.middleMenuSpinner.adapter = middle_food_adapter
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
// 중분류 스피너
        binding.middleMenuSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position == 0) {

                    } else if (position != 0) {
                        Middle_food = Middle_food_list[position].toString()

                        if(Middle_food == "쌀밥"){
                            items.clear()
                            items.add("눌은밥")
                            items.add("쌀밥")
                            items.add("찰밥")
                            items.add("현미밥")
                            // adapter.notifyDataSetChanged()

                            // RecyclerView 활용을 위한 코드
                            val recycler = findViewById<RecyclerView>(R.id.foodlist)
                            val rvAdapter = RVDAdapter(items)
                            recycler.adapter = rvAdapter

                            recycler.layoutManager = LinearLayoutManager(this@Management3Activity)
                        }
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        // 음식 목록에서 사용자가 선택한 음식에 대한 정보
        val f_list = intent.getStringExtra("list")
        // 최종 선택 메뉴에서 사용자가 제거한 음식에 대한 정보
        val f_final = intent.getStringExtra("final")


        // 사용자가 음식 목록에서 음식을 추가했을 때
        if (f_list != null){
            db.dao().insert(Entity(f_list, user_choice_date, user_choice_time))
            Log.d("DB 확인", "추가: ${db?.dao()?.getTitle().toString()}, $user_choice_date, $user_choice_time")
        }

        // 사용자가 최종 음식 목록에서 음식을 제거했을 때
        if (f_final != null){
            db.dao().deleteUserByName(f_final)
            Log.d("DB 확인", f_final)
            Log.d("DB 확인", "삭제: ${db?.dao()?.getTitle().toString()}")
        }

        // 리스트에 사용자가 담은 식단 저장
        if(db.dao().getEmptyTitle().isNotEmpty()){
            var index = 0

            while (index < db.dao().getEmptyTitle().size) {
                val foodtext = db.dao().getEmptyTitle()[index]
                items2.add(foodtext)
                index++
            }

            val recycler2 = findViewById<RecyclerView>(R.id.finallist)
            val rv2Adapter = RV2DAdapter(items2)
            recycler2.adapter = rv2Adapter

            recycler2.layoutManager = LinearLayoutManager(this@Management3Activity)
        }


        // DB 초기화 + 식단 저장 + 화면 전환(management1으로)
        binding.SettingFinBtn.setOnClickListener{
            var uid = AutoLogin.getUserId(this)

            server.requestMng(uid, strDate, db.dao().getEmptyTitle())
                .enqueue(object : Callback<Join> {
                    override fun onFailure(call: Call<Join>, t: Throwable) {
                        Log.d("실패", "정보: $uid, $strDate, ${db?.dao()?.getEmptyTitle().toString()}")
                    }

                    override fun onResponse(call: Call<Join>, response: Response<Join>) {
                        val serverCheck = response.body()
                        if(serverCheck?.code==200){
                            val nextintent = Intent(this@Management3Activity,SettingActivity::class.java)
                            Log.d("성공!", "정보: $uid, $strDate, ${db?.dao()?.getEmptyTitle().toString()}")
                            nextintent.putExtra("time", "저녁")
                            items2.clear()
                            startActivity(nextintent)
                        }
                        else{
                            Log.d("연결 실패", "else : ${response.body()}")
                            Toast.makeText(this@Management3Activity, "실패! $serverCheck.code", Toast.LENGTH_LONG).show()
                        }
                    }
                })


//            val nextintent = Intent(this@Management3Activity,SettingActivity::class.java)
//            nextintent.putExtra("time", "저녁")
//
//            items2.clear()
//            startActivity(nextintent)
        }
    }
}