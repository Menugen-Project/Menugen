package com.example.menugen

// 로그인과 관련
// 사용자 알러지 정보를 바탕으로 추천받은 식단 정보 가져옴
data class Login(
    var code: Int,
//    var identify: String,
//    var password: String,
//    var foodList: Array<String>,
    var fd1: String,
    var fd2: String,
    var fd3: String,
    var fd4: String
)