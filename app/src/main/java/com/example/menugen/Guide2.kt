package com.example.menugen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Guide2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide2)

        // 가이드 마치고 식단추천 페이지로 넘어가기
        val btnFinishGuide = findViewById<Button>(R.id.btnFinishGuide)

        btnFinishGuide.setOnClickListener {
            val intent = Intent(this, Recommend::class.java)
            startActivity(intent)
        }

    }
}