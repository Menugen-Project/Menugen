package com.example.menugen

// 로그인 창
import android.content.Intent
import android.content.SyncRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.menugen.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // 서버 연동
        val url = "여기에 서버주소"
        // val url = "여기에 서버주소"

        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var server = retrofit.create(LoginService::class.java)


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.doLogin.setOnClickListener {
            val intent: Intent = Intent(this, Recommend::class.java)

            // 로그인 시 ID & PW 받아오기
            val uid: String = findViewById<EditText>(R.id.idEdit).text.toString()
            val upw: String = findViewById<EditText>(R.id.pwEdit).text.toString()

            // 서버로 ID & PW 보내기
            server.requestLogin(uid, upw).enqueue(object : Callback<Login> {
                override fun onFailure(call: Call<Login>, t: Throwable) {
                    Log.d("로그인 실패", "로그인 실패")
                    Toast.makeText(this@LoginActivity, "서버 오류! 로그인 실패", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<Login>, response: Response<Login>) {
                    val userlogin = response.body()

                    //추천 식단 정보가 담긴 변수 <- 서버에서 받아옴
                    val Rfd1 = userlogin?.Rfd1.toString()
                    val fd1 = userlogin?.fd1.toString()
                    val Rfd2 = userlogin?.Rfd2.toString()
                    val fd2 = userlogin?.fd2.toString()
                    val Rfd3 = userlogin?.Rfd3.toString()
                    val fd3 = userlogin?.fd3.toString()
                    val Rfd4 = userlogin?.Rfd4.toString()
                    val fd4 = userlogin?.fd4.toString()

                    // 가입된 계정이 아니면 X, 맞으면 다음 화면
                    if (userlogin?.code == 200) {
                        Log.d("로그인 성공", "로그인 성공 $uid, $upw, $Rfd1, $fd1, $Rfd2, $fd2, $Rfd3, $fd3, $Rfd4, $fd4")
                        AutoLogin.setUserId(this@LoginActivity,uid)
                        intent.putExtra("largeFood1", Rfd1)
                        intent.putExtra("food1", fd1)
                        intent.putExtra("largeFood2", Rfd2)
                        intent.putExtra("food2", fd2)
                        intent.putExtra("largeFood3", Rfd3)
                        intent.putExtra("food3", fd3)
                        intent.putExtra("largeFood4", Rfd4)
                        intent.putExtra("food4", fd4)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@LoginActivity, "가입된 계정이 아닙니다!", Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
    }
}