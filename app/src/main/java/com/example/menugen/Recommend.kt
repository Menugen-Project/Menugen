package com.example.menugen

// 식단추천 & 식단관리 & 내정보 Fragment 정보를 담을 창
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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

    // RecyclerView를 사용하기 위해서는 위에서 만들어준 Adpater와 RecyclerView를 연결해주어야함.
    // 또한 서버와 연결하는 것이 아니라면 더미 data를 Adapter에 넣어줘야함.
    private fun initRecycler() {
        RV3Adapter = RV3Adapter(this)
        rv_menu.adapter = RV3Adapter


        datas.apply {
            add(RecommendData(photo = R.drawable.rice, largeMenuName = "대분류: 밥류", smallMenuName = "쌀밥"))
            add(RecommendData(photo = R.drawable.jjigae, largeMenuName = "대분류: 찌개류", smallMenuName = "된장찌개"))
            add(RecommendData(photo = R.drawable.kimchi, largeMenuName = "대분류: 김치류", smallMenuName = "배추김치"))
            add(RecommendData(photo = R.drawable.muchim, largeMenuName = "대분류: 무침류", smallMenuName = "봄나물무침"))
            add(RecommendData(photo = R.drawable.gui, largeMenuName = "대분류: 구이류", smallMenuName = "조기구이"))

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
}