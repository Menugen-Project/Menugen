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

    @Insert
    fun insert(entity: Entity)

    @Query("SELECT * FROM entity ORDER BY id ASC")
    fun readAllData() : LiveData<List<Entity>>

//    @Delete
//    fun deleteUser(entity: Entity)

    @Query("DELETE FROM entity WHERE title =:foodList")
    fun deleteUserByName(foodList : String)

    @Query("DELETE FROM entity")
    fun deleteAllUsers()
}