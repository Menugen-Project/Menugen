package com.example.menugen

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.IDNA
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.room.Room
import com.example.menugen.databinding.ActivityInfoBinding
import com.example.menugen.databinding.ActivityManagement2Binding
import com.example.menugen.databinding.ActivityRecommendBinding
import com.example.menugen.databinding.ActivitySettingBinding
import org.w3c.dom.Text
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.util.*


class SettingActivity : AppCompatActivity() {

    // 데이터 바인딩
    private lateinit var binding: ActivitySettingBinding

    // 식단 관리 시 아침/점심/저녁 알림 변수
    var time:String = ""
    var changeDate = ""
    // 오늘 날짜를 담을 변수
    var date:LocalDate = LocalDate.now()
    var strDate = date.toString()
    // 사용자가 캘린더로 바꿀 날짜에 대한 변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        // 서버 연동코드 <---
        val url = "여기에 서버주소"
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var server = retrofit.create(ManageFoodList::class.java)
        // <---
        val userChangeDate = intent.getStringExtra("changeDate")
        if (userChangeDate != null){
            changeDate = userChangeDate.toString()
        }else{
            changeDate = strDate
        }

        // 캘린더 사용을 위한 변수
        val cal = Calendar.getInstance()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting)

        // 캘린더 날짜를 항상 오늘의 날짜로 설정
        binding.btnCalendar.text = changeDate


//        val userFoodList = intent.getStringExtra("userFoodList")
//        val userFoodTime = intent.getStringExtra("userTime")

        // DB 연결
        val db = Room.databaseBuilder(
            applicationContext, AppDatabase::class.java, "database"
        ).allowMainThreadQueries().build()
//        db.dao().deleteAll()

        // 캘린더 로드 (달력버튼을 눌러 날짜별 설정 -> 날짜 선택 시 각 날짜의 식단 다르게 보여주는 기능 추가 필요)
        binding.btnCalendar.setOnClickListener {
            DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ datePicker, y, m, d ->
                changeDate = "$y-${m+1}-$d"
                binding.btnCalendar.text = changeDate
                val intent = Intent(this, SettingActivity::class.java)
                intent.putExtra("changeDate", changeDate)
                startActivity(intent)
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        // DB에 데이터 저장
        val userTime = intent.getStringExtra("time")

        if(userTime != null && db.dao().getEmptyTitle().isNotEmpty()){
            var index = 0

            while (index < db.dao().getEmptyTitle().size) {
                val foodtext = db.dao().getEmptyTitle()[index]
                db.dao().insert(Entity(foodtext, changeDate, userTime))
                index++
            }
            db.dao().deleteAllUsers()
        }

        if(changeDate == strDate){
            if(db.dao().getTimeTitle(changeDate,"아침").isNotEmpty()){
                binding.morning.text = db.dao().getTimeTitle(changeDate,"아침").toString()
            } else {
                binding.morning.text = "여기를 클릭하고 아침 식단을 관리해보세요!"
            }

            if(db.dao().getTimeTitle(changeDate,"점심").isNotEmpty()) {
                binding.lunch.text = db.dao().getTimeTitle(changeDate,"점심").toString()
            } else {
                binding.lunch.text = "여기를 클릭하고 점심 식단을 관리해보세요!"
            }

            if(db.dao().getTimeTitle(changeDate,"저녁").isNotEmpty()) {
                binding.dinner.text = db.dao().getTimeTitle(changeDate,"저녁").toString()
            } else {
                binding.dinner.text = "여기를 클릭하고 저녁 식단을 관리해보세요!"
            }
        }else{
            binding.morning.text = "$changeDate 날의 아침 식단은 비어있습니다."
            binding.lunch.text = "$changeDate 날의 점심 식단은 비어있습니다."
            binding.dinner.text = "$changeDate 날의 저녁 ㅕ식단은 비어있습니다."
        }

        // 아침/점심/저녁 식단관리 클릭 시
        // 현재 날짜와 달력의 날짜가 다르면 클릭 이벤트 발생X
        binding.morning.setOnClickListener{
            val intent = Intent(this, Management1Activity::class.java)
            if(changeDate == strDate){
                startActivity(intent)
            }
        }
        binding.lunch.setOnClickListener{
            val intent = Intent(this, Management2Activity::class.java)
            if(changeDate == strDate){
                startActivity(intent)
            }
        }
        binding.dinner.setOnClickListener{
            val intent = Intent(this, Management3Activity::class.java)
            if(changeDate == strDate){
                startActivity(intent)
            }
        }



        binding.btn1.setOnClickListener{
            val intent = Intent(this, SettingActivity::class.java)
            db.dao().deleteFood("아침")
            startActivity(intent)
        } // 아침 식단 X 버튼 클릭 시
        binding.btn2.setOnClickListener{
            val intent = Intent(this, SettingActivity::class.java)
            db.dao().deleteFood("점심")
            startActivity(intent)
        } // 점심 식단 X 버튼 클릭 시
        binding.btn3.setOnClickListener{
            val intent = Intent(this, SettingActivity::class.java)
            db.dao().deleteFood("저녁")
            startActivity(intent)
        } // 저녁녁 식단 X 버 클릭 시

        // 하단바 각각 액티비티로 이동
        binding.btnRecommend.setOnClickListener {
            val intent = Intent(this, Recommend::class.java)
            startActivity(intent)
        }

        binding.btnInfo.setOnClickListener {
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
        }

        // 임시
        binding.btnTemp.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            db.dao().deleteAll()
            startActivity(intent)
        }
    }
}