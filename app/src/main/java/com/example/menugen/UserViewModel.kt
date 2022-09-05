package com.example.menugen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers

// https://hanyeop.tistory.com/204

class UserViewModel(application: Application): AndroidViewModel(application) {

    private val readAllData: LiveData<List<Entity>>
    private val repository : UserRepository

    init{
        val userDao = AppDatabase.getInstance(application)!!.dao()
        repository = UserRepository(userDao)
        readAllData = repository.readAllData
    }

    /*
    fun addUser(entity: Entity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(entity)
        }
    }
     */

    /*
    class Factory(val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UserViewModel(application) as T
        }
    }
     */
}