package com.example.menugen

// 식단추천 & 식단관리 & 내정보 Fragment 정보를 담을 창
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.menugen.databinding.ActivityInfoBinding
import com.example.menugen.databinding.ActivityNutrientBinding
import com.example.menugen.databinding.ActivityRecommendBinding
import com.example.menugen.databinding.ActivitySettingBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_recommend.*

class Recommend : AppCompatActivity() {
    private lateinit var binding: ActivityRecommendBinding
    lateinit var RV3Adapter: RV3Adapter
    val datas = mutableListOf<RecommendData>()

    private var item = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommend)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_recommend)

        // 리사이클러뷰
        initRecycler()

        // 액티비티 전환 시 startActivity() 이후 overridePendingTransition을 사용하여 애니메이션을 적용
        findViewById<Button>(R.id.btn_slide_left).setOnClickListener {
            val intent = RecommendLeftActivity.newIntent(this)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_left_enter, R.anim.slide_left_exit)
        }

        findViewById<Button>(R.id.btn_slide_right).setOnClickListener {
            val intent = RecommendRightActivity.newIntent(this)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit)
        }

        // 추천메뉴 아이템 클릭 시, 영양정보 액티비티로 이동
//        binding.largeMenu1.setOnClickListener {
//            val intent = Intent(this, NutrientActivity::class.java)
//            startActivity(intent)
//        }

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

        /*
        // 하단 탭이 눌렸을 때 화면을 전환하기 위한 객체 생성 -> Fragment
        var bnv_main = findViewById(R.id.bottom_navigationview) as BottomNavigationView

        // OnNavigationItemSelectedListener를 통해 탭 아이템 선택 시 이벤트를 처리
        // navi_menu.xml 에서 설정했던 각 아이템들의 id를 통해 알맞은 프래그먼트로 변경
        bnv_main.run { setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.home -> {
                    // 다른 프래그먼트 화면으로 이동하는 기능
                    val homeFragment = HomeFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.nav_container, homeFragment).commit()
                }
                R.id.setting -> {
                    val settingFragment = SettingFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.nav_container, settingFragment).commit()
                }
                R.id.info -> {
                    val infoFragment = Management_1()
                    supportFragmentManager.beginTransaction().replace(R.id.nav_container, infoFragment).commit()
                }
            }
            true
        }
            selectedItemId = R.id.home
        } */
    }

    // RecyclerView를 사용하기 위해서는 위에서 만들어준 Adpater와 RecyclerView를 연결해주어야함.
    // 또한 서버와 연결하는 것이 아니라면 더미 data를 Adapter에 넣어줘야함.
    private fun initRecycler() {
        // 서버에서 보내준 추천 식단 확인
        // var foodList = intent.getSerializableExtra("foodList") as ArrayList<String>
        // Log.d("확인", foodList.toString())

        val largeFood1 = intent.getStringExtra("largeFood1")
        val food1 = intent.getStringExtra("food1")
        val largeFood2 = intent.getStringExtra("largeFood2")
        val food2 = intent.getStringExtra("food2")
        val largeFood3 = intent.getStringExtra("largeFood3")
        val food3 = intent.getStringExtra("food3")
        val largeFood4 = intent.getStringExtra("largeFood4")
        val food4 = intent.getStringExtra("food4")

        item.add(largeFood1.toString())
        item.add(largeFood2.toString())
        item.add(largeFood3.toString())
        item.add(largeFood4.toString())

        RV3Adapter = RV3Adapter(this)
        rv_menu.adapter = RV3Adapter

        datas.apply {

            var tmp = R.drawable.rice

            if(item.isNotEmpty()){
                var index = 0
                while(index < item.size){
                    if(item[index] == "밥류"){
                        tmp = R.drawable.rice
                    } else if(item[index] == "면 및 만두류"){
                        tmp = R.drawable.noodle
                    } else if(item[index] == "국(탕)류"){
                        tmp = R.drawable.tang
                    } else if(item[index] == "찌개류"){
                        tmp = R.drawable.jjigae
                    } else if(item[index] == "찜류"){
                        tmp = R.drawable.jjim
                    } else if(item[index] == "조림류"){
                        tmp = R.drawable.jorim
                    } else if(item[index] == "구이류"){
                        tmp = R.drawable.gui
                    } else if(item[index] == "전류"){
                        tmp = R.drawable.jeon
                    } else if(item[index] == "튀김류"){
                        tmp = R.drawable.fried
                    } else if(item[index] == "볶음류"){
                        tmp = R.drawable.bokkeum
                    } else if(item[index] == "무침류"){
                        tmp = R.drawable.muchim
                    } else if(item[index] == "김치류"){
                        tmp = R.drawable.kimchi
                    } else if(item[index] == "떡류"){
                        tmp = R.drawable.tteok
                    } else if(item[index] == "우유 및 유제품류"){
                        tmp = R.drawable.milk
                    } else if(item[index] == "음료류"){
                        tmp = R.drawable.drink
                    } else if(item[index] == "젓갈류"){
                        tmp = R.drawable.jeotgal
                    } else if(item[index] == "죽류"){
                        tmp = R.drawable.juk
                    } else if(item[index] == "회류"){
                        tmp = R.drawable.sashimi
                    } else if(item[index] == "과자 및 빵류"){
                        tmp = R.drawable.cookie
                    } else if(item[index] == "주류"){
                        tmp = R.drawable.alcohol
                    } else if(item[index] == "원재료"){
                        tmp = R.drawable.raw
                    } else if(item[index] == "장류"){
                        tmp = R.drawable.jang
                    } else if(item[index] == "절임류"){
                        tmp = R.drawable.jerim
                    }

                    if(index == 0){
                        add(RecommendData(photo = tmp, largeMenuName = "$largeFood1", smallMenuName = food1.toString()))
                    }else if(index == 1){
                        add(RecommendData(photo = tmp, largeMenuName = "$largeFood2", smallMenuName = food2.toString()))
                    }else if(index == 2){
                        add(RecommendData(photo = tmp, largeMenuName = "$largeFood3", smallMenuName = food3.toString()))
                    }else if(index == 3){
                        add(RecommendData(photo = tmp, largeMenuName = "$largeFood4", smallMenuName = food4.toString()))
                    }

                    index++
                }
            }
//            add(RecommendData(photo = tmp, largeMenuName = "$largeFood1", smallMenuName = food1.toString()))
//            add(RecommendData(photo = tmp, largeMenuName = "$largeFood2", smallMenuName = food2.toString()))
//            add(RecommendData(photo = tmp, largeMenuName = "$largeFood3", smallMenuName = food3.toString()))
//            add(RecommendData(photo = tmp, largeMenuName = "$largeFood4", smallMenuName = food4.toString()))

            RV3Adapter.datas = datas
            RV3Adapter.notifyDataSetChanged()

        }
    }

    /*
    // Fragment간 이동을 위한 함수
    fun changeFragment(index: Int){
        management1 = Management_1()
        setting = SettingFragment()
        when(index){
            1 -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.nav_container, management1)
                    .commit()
            }

            2 -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.nav_container, setting)
                    .commit()
            }
        }
    }
     */

    // 뒤로가기 방지를 위한 변수
    private var doubleBackToExit = false

    // 뒤로가기 방지 및 두번 뒤로가기 시 종료
    override fun onBackPressed() {
        if(doubleBackToExit){
            finishAffinity()
        } else {
            Toast.makeText(this, "종료하시려면 뒤로가기를 한번 더 눌러주세요", Toast.LENGTH_SHORT).show()
            doubleBackToExit = true
            runDelayed(1500L){
                doubleBackToExit = false
            }
        }
    }

    // 뒤로가기를 일정시간 내에 두번 입력하는 것 감지하는 함수
    fun runDelayed(millis: Long, function: () -> Unit){
        Handler(Looper.getMainLooper()).postDelayed(function, millis)
    }
}