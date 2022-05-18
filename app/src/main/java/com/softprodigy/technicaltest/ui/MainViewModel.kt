package com.softprodigy.technicaltest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.softprodigy.technicaltest.data.entities.User
import com.softprodigy.technicaltest.data.repository.UsersRepository
import com.softprodigy.technicaltest.ui.screens.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: UsersRepository) : ViewModel() {

    var job: Job? = null
    val movieList = MutableLiveData<List<User>>()

    fun getUsers() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getUsers()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    movieList.postValue(response.body()!!.data)
                }
            }
        }

    }

    private val _currentScreen = MutableLiveData<Screens>(Screens.DrawerScreens.Home)
    val currentScreen: LiveData<Screens> = _currentScreen

    fun setCurrentScreen(screen: Screens) {
        _currentScreen.value = screen
    }
}