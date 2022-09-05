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
        var server = retrofit.create(JoinService::class.java)
        // <---

        // 뷰모델 선언 및 연결
        // val userViewModel = ViewModelProvider(this,UserViewModel.Factory(application)).get(UserViewModel::class.java)

        // 어댑터 연결
        adapter = RVAdapter(items)
        adapter2 = RV2Adapter(items)
        binding.finallist.adapter = adapter2

        /*//<---
        binding.finallist.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter.setItemClickListener(object : RVAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                Toast.makeText(this@Management1Activity, "제발요", Toast.LENGTH_SHORT).show()
            }
        }) */

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
                        // Toast.makeText(this@Management1Activity, "대분류를 선택해주세요", Toast.LENGTH_SHORT).show()
                    }
                    else if(position != 0){
                        Large_food = Large_food_list[position].toString()
                        // Toast.makeText(this@Management1Activity, Large_food, Toast.LENGTH_SHORT).show()

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


        val f_list = intent.getStringExtra("list")

        var final_list = ""
        if (f_list != null){
            final_list = f_list.toString()
            Log.d("범인 검거","${db?.dao()?.getTitle().toString()}, $final_list")
        }

        // db에 저장된 데이터 불러오기
//        db.dao().getAll().observe(this, Observer { todos ->
//            //binding.finalfood.text = todos.toString()
//            items2.add(todos.toString())
//
//            val recycler = findViewById<RecyclerView>(R.id.finallist)
//            val rvAdapter = RVAdapter(items2)
//            recycler.adapter = rvAdapter
//
//            recycler.layoutManager = LinearLayoutManager(this@Management1Activity)
//        })

        if(db.dao().getTitle().isEmpty() == false){
            var index = 0
            while (index < db.dao().getTitle().size) {
                val foodtext = db.dao().getTitle()[index]
                items2.add(foodtext)
                index++
                Log.d("while 테스트", items2.toString())
                // binding.finalfood.text = foodtext
            }
            // binding.finalfood.text = db.dao().getTitle().toString()

            val recycler2 = findViewById<RecyclerView>(R.id.finallist)
            val rv2Adapter = RV2Adapter(items2)
            recycler2.adapter = rv2Adapter

            recycler2.layoutManager = LinearLayoutManager(this@Management1Activity)
        }

/*
        val recycler = findViewById<RecyclerView>(R.id.finallist)
        val rv2Adapter = RV2Adapter(items2)
        recycler.adapter = rv2Adapter

        recycler.layoutManager = LinearLayoutManager(this@Management1Activity)
 */

        /*
        val rvAdapter = RVAdapter(items)
        rvAdapter.setOnItemClickListener(object : RVAdapter.OnItemClickListener{
            override fun onItemClick(v: View, items: List<String>) {
                Toast.makeText(this@Management1Activity, "제발요", Toast.LENGTH_SHORT).show()
                Log.d("제발", "gg")
            }
        })
         */

        /*
        val recycler2 = findViewById<RecyclerView>(R.id.finallist)
        val rvAdapter2 = RVAdapter(items2)
        recycler2.adapter = rvAdapter2
        */

        /*
        rvAdapter2.setOnItemClickListener(object : RVAdapter.OnItemClickListener{
            override fun onItemClick(v: View, items: List<String>) {
                val finallist = items.toString()
                items2.add(finallist)
                Toast.makeText(this@Management1Activity, "테스트", Toast.LENGTH_LONG).show()
            }
        })
         */

        // recycler2.layoutManager = LinearLayoutManager(this@Management1Activity)



        // 목록 초기화 + 데이터 넘기기기
        binding.SettingFinBtn.setOnClickListener{
            db.dao().deleteAllUsers()
            items2.clear()
            Log.d("로그","delete후 : ${db?.dao()?.getTitle().toString()}")

            val recycler = findViewById<RecyclerView>(R.id.finallist)
            val rvAdapter = RVAdapter(items2)
            recycler.adapter = rvAdapter

            recycler.layoutManager = LinearLayoutManager(this@Management1Activity)
        }

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
        // 버튼 클릭을 통한 Management1 액티비티로 이동
//        binding.SettingFinBtn.setOnClickListener {
//            val intent = Intent(this, Management1Activity::class.java)
//            startActivity(intent)
//        }
    }

    fun OnItemClick(items: List<String>){
        var lastlist: List<String> = items
    }
/*
    fun addMember(member: Member, position: Int){
        items[position]
    }
 */
}