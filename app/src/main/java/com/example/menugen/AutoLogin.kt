package com.example.menugen

// 자동 로그인 서비스 제공 코드 창
import android.content.Context
import android.content.SharedPreferences
import retrofit2.Callback

object AutoLogin {
    private val MY_ACCOUNT : String = "account"

    fun setUserId(context: Context, input: String) {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_ID", input)
        editor.commit()
    }

    fun getUserId(context: Context): String {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString("MY_ID", "").toString()
    }

    fun setUserPass(context: Context, input: String) {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_PASS", input)
        editor.commit()
    }

    fun getUserPass(context: Context): String {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString("MY_PASS", "").toString()
    }
    fun setUserCheck(context: Context, input: Boolean) {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putBoolean("MY_CHECK", input)
        editor.commit()
    }
    fun setUserName(context: Context, input: String){
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_NAME", input)
        editor.commit()
    }
    fun getUserName(context: Context): String {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, android.content.Context.MODE_PRIVATE)
        return prefs.getString("MY_NAME", "").toString()
    }

    fun setUserSerial(context: Context, input: String){
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_SERIAL", input)
        editor.commit()
    }
    fun getUserSerial(context: Context): String {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, android.content.Context.MODE_PRIVATE)
        return prefs.getString("MY_SERIAL", "").toString()
    }
    fun setUserTime(context: Context, input: String){
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_TIME", input)
        editor.commit()
    }
    fun getUserTime(context: Context): String {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, android.content.Context.MODE_PRIVATE)
        return prefs.getString("MY_TIME", "").toString()
    }

    fun setPhoneNum(context: Context, input: String){
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_PHONE", input)
        editor.commit()
    }

    fun getPhoneNum(context: Context): String {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, android.content.Context.MODE_PRIVATE)
        return prefs.getString("MY_PHONE", "").toString()
    }

    fun getUserCheck(context: Context) : Boolean{
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getBoolean("MY_CHECK", false)
    }

    fun setUserMeal(context: Context, input: Array<Array<String>>){
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_MEAL","")
        editor.commit()
    }

    fun getUserMeal(context: Context): String {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString("MY_MEAL", "").toString()
    }

    fun removeUser(context: Context) {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.remove("MY_ID")
        editor.remove("MY_PASS")
        editor.remove("MY_NAME")
        editor.remove("MY_SERIAL")
        editor.commit()
    }

    fun clearUser(context: Context) {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.clear()
        editor.commit()
    }
}

