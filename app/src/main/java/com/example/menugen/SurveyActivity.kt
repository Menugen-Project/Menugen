package com.example.menugen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class SurveyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)

        // 1. 다음  버튼 클릭 시, 중간 설문 했다치고 임시로 설문 완료알림 띄우기
        val btn_next = findViewById<Button>(R.id.btn_next)

        btn_next.setOnClickListener {
            Toast.makeText(this, "설문 완료 가정, 로그인 연동 화면으로!", Toast.LENGTH_LONG).show()

            // 2. 로그인 연동 화면으로 넘어가기
            var intent = Intent(this, LogActivity::class.java)
            startActivity(intent)

        }
    }
}