package com.example.menugen

// 회원가입 후 설문조사 창
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.example.menugen.databinding.ActivitySurveyBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.round

class SurveyActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySurveyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // 서버 연동코드
        val url = "여기에 서버주소"
        // val url = "여기에 서버주소"

        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var server = retrofit.create(JoinService::class.java)

        // 사용자 성별을 담을 변수
        var sex =""
        // 사용자 나이를 담을 변수
        var uage=""
        // 알러지 수를 담을 변수
        var index = 0

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_survey)

        val sex_itemList = listOf("성별 선택!", "남자", "여자")
        val sex_adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, sex_itemList)
        binding.sexSpinner.adapter = sex_adapter

        // 사용자 성별
        binding.sexSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0) Toast.makeText(this@SurveyActivity, sex_itemList[position], Toast.LENGTH_SHORT).show()

                // 사용자가 선택한 성별을 남자=1, 여자=2로 변환해서 서버로 전송
                if(position != 0){
                    sex = position.toString()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

// ------------------------------------------기능------------------------------------------

        // 사용자 알러지 정보 가져오는 함수
        var listall = mutableListOf<String>()

        // 사용자 알러지 정보를 담을 변수
        val al1 = findViewById<CheckBox>(R.id.allergy0)
        val al2 = findViewById<CheckBox>(R.id.allergy1)
        val al3 = findViewById<CheckBox>(R.id.allergy2)
        val al4 = findViewById<CheckBox>(R.id.allergy3)
        val al5 = findViewById<CheckBox>(R.id.allergy4)
        val al6 = findViewById<CheckBox>(R.id.allergy5)
        val al7 = findViewById<CheckBox>(R.id.allergy6)
        val al8 = findViewById<CheckBox>(R.id.allergy7)
        val al9 = findViewById<CheckBox>(R.id.allergy8)
        val al10 = findViewById<CheckBox>(R.id.allergy9)
        val al11 = findViewById<CheckBox>(R.id.allergy10)
        val al12 = findViewById<CheckBox>(R.id.allergy11)
        val al13 = findViewById<CheckBox>(R.id.allergy12)
        val al14 = findViewById<CheckBox>(R.id.allergy13)
        val al15 = findViewById<CheckBox>(R.id.allergy14)
        val al16 = findViewById<CheckBox>(R.id.allergy15)
        val al17 = findViewById<CheckBox>(R.id.allergy16)
        val al18 = findViewById<CheckBox>(R.id.allergy17)

        // 칼로리 계산
        fun basal_cal(height:Float, weight:Float, sex:Int, age:Int): Double {
            if(sex==1){
                return round((66.47 + (13.75 * weight) + (5 * height) - (6.76 * age))*100)/100
            }
            else{
                return round((655.1 + (9.56 * weight) + (1.85 * height) - (4.68 * age))*100)/100
            }
        }

        var newintent = Intent(this, LoginActivity::class.java)

        binding.btnNext.setOnClickListener {
            // 순서대로 ID, PW, 이메일, 이름, 성별, 나이, 키, 체중, 키변환, 체중변환, 칼로리계산
            val uid = intent.getStringExtra("uid").toString()
            val upw = intent.getStringExtra("upw").toString()
            val uemail = intent.getStringExtra("uemail").toString()
            val uname = intent.getStringExtra("uname").toString()
            val uage : String = findViewById<EditText>(R.id.userage).text.toString()
            val uheight : String = findViewById<EditText>(R.id.user_height).text.toString()
            val uweight : String = findViewById<EditText>(R.id.user_weight).text.toString()

            // 빈칸 존재 시 예외처리
            if(uage == "" || sex == "" || uheight == "" || uweight == ""){
                Toast.makeText(this, "빈칸을 모두 채워주세요!", Toast.LENGTH_LONG).show()
            }
            else {
                val usersex = sex.toInt()
                val userage = uage.toInt()
                val userheight = uheight.toFloat()
                val userweight = uweight.toFloat()


                val userbasal = basal_cal(userheight, userweight, usersex, userage)

                // 알러지 정보 초기화
                listall.clear()

                // 1~14번 알러지 정보 가져오기
                if (al1.isChecked) {
                    val al0index = 1
                    index++
                    listall.add(al0index.toString())
                }
                if (al2.isChecked) {
                    val al1index = 2
                    index++
                    listall.add(al1index.toString())
                }
                if (al3.isChecked) {
                    val al2index = 3
                    index++
                    listall.add(al2index.toString())
                }
                if (al4.isChecked) {
                    val al3index = 4
                    index++
                    listall.add(al3index.toString())
                }
                if (al5.isChecked) {
                    val al4index = 5
                    index++
                    listall.add(al4index.toString())
                }
                if (al6.isChecked) {
                    val al5index = 6
                    index++
                    listall.add(al5index.toString())
                }
                if (al7.isChecked) {
                    val al6index = 7
                    index++
                    listall.add(al6index.toString())
                }
                if (al8.isChecked) {
                    val al7index = 8
                    index++
                    listall.add(al7index.toString())
                }
                if (al9.isChecked) {
                    val al8index = 9
                    index++
                    listall.add(al8index.toString())
                }
                if (al10.isChecked) {
                    val al9index = 10
                    index++
                    listall.add(al9index.toString())
                }
                if (al11.isChecked) {
                    val al10index = 11
                    index++
                    listall.add(al10index.toString())
                }
                if (al12.isChecked) {
                    val al11index = 12
                    index++
                    listall.add(al11index.toString())
                }
                if (al13.isChecked) {
                    val al12index = 13
                    index++
                    listall.add(al12index.toString())
                }
                if (al14.isChecked) {
                    val al13index = 14
                    index++
                    listall.add(al13index.toString())
                }
                if (al15.isChecked) {
                    val al14index = 15
                    index++
                    listall.add(al14index.toString())
                }
                if (al16.isChecked) {
                    val al15index = 16
                    index++
                    listall.add(al15index.toString())
                }
                if (al17.isChecked) {
                    val al16index = 17
                    index++
                    listall.add(al16index.toString())
                }
                if (al18.isChecked) {
                    val al17index = 18
                    index++
                    listall.add(al17index.toString())
                }
                // 회원가입 절차
                server.requestJoin(uid, upw, uname, uemail, usersex, userage, userheight, userweight, userbasal, listall)
                    .enqueue(object : Callback<Join> {
                        // 실패 시
                        override fun onFailure(call: Call<Join>, t: Throwable) {
                            Log.d(
                                "실패", "정보 $uid, $upw, $uemail, $uname, $usersex, $userage, $userbasal"
                            )
                        }

                        // 성공 시
                        override fun onResponse(call: Call<Join>, response: Response<Join>) {
                            val surveyCheck = response.body()
                            if(surveyCheck?.code==200) {
                                Log.d(
                                    "성공",
                                    "정보 $uid, $upw, $uemail, $uname, $usersex, $userage, $userbasal, $listall"
                                )
                                startActivity(newintent)
                            }
                            else{
                                Toast.makeText(this@SurveyActivity, "연결 실패", Toast.LENGTH_LONG).show()
                            }
                        }
                    })
                // Log.d("회원가입 성공", "회원가입 성공 $usersex, $userage, $userbasal, $listall")
                // startActivity(newintent)
            }
        }
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
}