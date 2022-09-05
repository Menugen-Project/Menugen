package com.example.menugen

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DAO {
    // 데이터 베이스 불러오기
    @Query("SELECT * from entity")
    abstract fun getAll(): LiveData<List<Entity>>

    @Update
    fun update(entity: Entity)

    // 데이터 베이스 추가
    @Insert
    fun insert(entity: Entity)

    @Query("SELECT * FROM entity ORDER BY id ASC")
    fun readAllData() : LiveData<List<Entity>>

    //모두지웁니다.
    @Query("DELETE FROM entity")
    fun deleteAllUsers()
}