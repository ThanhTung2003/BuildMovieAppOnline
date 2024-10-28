package com.example.buildmovieapponline.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buildmovieapponline.Model.Category
import com.example.buildmovieapponline.Model.MovieRepository
import kotlinx.coroutines.launch

class MainViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

//    init {
//        fetchCategories()
//    }
//
//    private fun fetchCategories() {
//        _loading.value = true
//        viewModelScope.launch {
//            try {
//                val response = movieRepository.getCategories()
//                _loading.value = false
//                if (response?.status == "success") {
//                    _categories.value = response.body
//                } else {
//                    if (response != null) {
//                        Log.e("MainActivity", "Error fetching categories: ${response.body}")
//                    }
//                }
//            } catch (e: Exception) {
//                _loading.value = false
//                Log.d("MainActivity","API call failed:")
//            }
//        }
//    }
}
