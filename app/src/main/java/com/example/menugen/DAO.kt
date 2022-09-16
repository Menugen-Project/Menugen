package com.example.menugen

import androidx.lifecycle.LiveData
import androidx.room.*
import retrofit2.http.DELETE

@Dao
interface DAO {
    @Update
    fun update(entity: Entity)

    @Query("select title from entity")
    fun getTitle(): List<String>

    @Query("select title from entity WHERE time=''")
    fun getEmptyTitle(): List<String>

    @Query("select title from entity WHERE time=:time and date=:date")
    fun getTimeTitle(date:String, time: String): List<String>

//    @Query("select title from entity WHERE date =:date and time =:time")
//    fun getDTtitle(date:String, time:String): String
//
//    @Query("select title from entity WHERE time =: time")
//    fun getALL(): String

    @Insert
    fun insert(entity: Entity)

    @Query("SELECT * FROM entity ORDER BY id ASC")
    fun readAllData() : LiveData<List<Entity>>

//    @Delete
//    fun deleteUser(entity: Entity)

    @Query("DELETE FROM entity WHERE title =:foodList")
    fun deleteUserByName(foodList : String)

    @Query("DELETE FROM entity WHERE time =''")
    fun deleteAllUsers()

    @Query("DELETE FROM entity WHERE time =:time")
    fun deleteFood(time:String)

    @Query("DELETE FROM entity")
    fun deleteAll()
}