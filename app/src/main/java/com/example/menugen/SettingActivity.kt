package com.example.menugen

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.IDNA
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.room.Room
import com.example.menugen.databinding.ActivityInfoBinding
import com.example.menugen.databinding.ActivityRecommendBinding
import com.example.menugen.databinding.ActivitySettingBinding
import java.time.LocalDate
import java.util.*


class SettingActivity : AppCompatActivity() {

    // 데이터 바인딩
    private lateinit var binding: ActivitySettingBinding

    // 식단 관리 시 아침/점심/저녁 알림 변수
    var time:String = ""

    // 오늘 날짜를 담을 변수
    var date:LocalDate = LocalDate.now()
    // 사용자가 캘린더로 바꿀 날짜에 대한 변수
    var changeDate:String = ""
    var strDate = date.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        // 캘린더 사용을 위한 변수
        val cal = Calendar.getInstance()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting)


        val userFoodList = intent.getStringExtra("userFoodList")
        val userFoodTime = intent.getStringExtra("userTime")
        if(userFoodTime == "아침"){
            val morningText = findViewById<TextView>(R.id.morning)
            morningText.setText(userFoodList)
        } else if(userFoodTime == "점심"){
            val LunchText = findViewById<TextView>(R.id.lunch)
            LunchText.setText(userFoodList)
        } else if(userFoodTime == "저녁"){
            val DinnerText = findViewById<TextView>(R.id.dinner)
            DinnerText.setText(userFoodList)
        }


        // 캘린더 로드 (달력버튼을 눌러 날짜별 설정 -> 날짜 선택 시 각 날짜의 식단 다르게 보여주는 기능 추가 필요)
        binding.btnCalendar.setOnClickListener {
            DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ datePicker, y, m, d ->
                changeDate = "$y-${m+1}-$d"
                binding.btnCalendar.text = changeDate
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.morning.setOnClickListener{
            time = "아침"
            val intent = Intent(this, Management1Activity::class.java)
            intent.putExtra("time", time)
            if(changeDate==""){
                intent.putExtra("date", strDate)
            }else{
                intent.putExtra("date", changeDate)
            }
            startActivity(intent)
        }

        binding.lunch.setOnClickListener{
            time = "점심"
            val intent = Intent(this, Management1Activity::class.java)
            intent.putExtra("time", time)
            if(changeDate==""){
                intent.putExtra("date", strDate)
            }else{
                intent.putExtra("date", changeDate)
            }
            startActivity(intent)
        }

        binding.dinner.setOnClickListener{
            time = "저녁"
            val intent = Intent(this, Management1Activity::class.java)
            intent.putExtra("time", time)
            if(changeDate==""){
                intent.putExtra("date", strDate)
            }else{
                intent.putExtra("date", changeDate)
            }
            startActivity(intent)
        }

        // 버튼 클릭을 통한 Management1 액티비티로 이동
//        binding.btnManagement1.setOnClickListener {
//            val intent = Intent(this, Management1Activity::class.java)
//            startActivity(intent)
//        }

        // 하단바 각각 액티비티로 이동
        binding.btnRecommend.setOnClickListener {
            val intent = Intent(this, Recommend::class.java)
            startActivity(intent)
        }
        /*
        binding.btnSetting.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }*/
        binding.btnInfo.setOnClickListener {
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
        }
    }
}