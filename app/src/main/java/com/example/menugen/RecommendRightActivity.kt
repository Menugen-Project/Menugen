package com.example.menugen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class RecommendRightActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, RecommendRightActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommend_right)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_left_enter, R.anim.slide_left_exit)
    }
}