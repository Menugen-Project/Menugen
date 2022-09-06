package com.example.menugen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Guide1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide1)

        // 가이드2로 넘어가기
        val btnGotoGuide2 = findViewById<Button>(R.id.btnGotoGuide2)

        btnGotoGuide2.setOnClickListener {
            val intent = Intent(this, Guide2Activity::class.java)
            startActivity(intent)
        }

    }
}