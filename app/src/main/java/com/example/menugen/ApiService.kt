package com.example.menugen

// 서버에 데이터 전송을 위한 코드
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.*
import java.lang.reflect.Array

data class ResponseDC(var result:String? = null)

public interface ApiService {
    @POST("/users/signUp") //회원가입
    @FormUrlEncoded
    fun logUp(@Field("name") name : String, @Field("id") id : String, @Field("pw") pw : String) :  Call<Void>
}
// 로그인
public interface LoginService{
    @FormUrlEncoded
    @POST("/users/login")
    fun requestLogin(
        @Field("userId") userid:String,
        @Field("userPassword") userPassword:String
    ) : Call<Login>
}

// 아이디 중복 확인
public interface LoginReduple{
    @FormUrlEncoded
    @POST("/users/signin")
    fun requestLoginReduple(
        @Field("userId") userid:String
    ) : Call<Reduple>
}

// 회원가입
public interface JoinService{
    @FormUrlEncoded
    @POST("/users/signup")
    fun requestJoin(
        @Field("userId") id:String,
        @Field("userPassword") pw:String,
        @Field("name") name:String,
        @Field("email") email:String,
        @Field("sex") sex: Int,
        @Field("age") age: Int,
        @Field("height") height: Float,
        @Field("weight") weight: Float,
        @Field("basal") basal: Double,
        @Field("allergy") allergy: List<String>
        //순서대로 ID, PW, 이름, 이메일, 성별, 나이, 키, 몸무게, 대사량 계산식
        //@Field("Uallergy") Uallergy:String,
        //@Field("Udia") Udia:String
    ) : Call<Join>
}

// 사용자 식단 정보
public interface ManageFoodList{
    @FormUrlEncoded
    @POST("/diets")
    fun requestMng(
        @Field("userId") id:String,
        @Field("date") time:String,
        @Field("break_diet") breakDiet:List<String>
    ) : Call<Join>
}

// 식단 검색 시
public interface SearchFoodList{
    @FormUrlEncoded
    @POST("/foods/search")
    fun requestSearch(
        @Field("lr_ctg") lr_ctg:String,
        @Field("md_ctg") md_ctg:String
    ) : Call<Join>
}