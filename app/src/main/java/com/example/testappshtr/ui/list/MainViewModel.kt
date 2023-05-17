package com.example.testappshtr.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testappshtr.service.BackendResult
import com.example.testappshtr.service.FoodService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val service: FoodService,
) : ViewModel() {

    val productsFlow = MutableStateFlow<BackendResult>(BackendResult.Initial)

    private var loadingJob: Job? = null

    init {
        onRefresh()
    }

    fun onRefresh() {
        if (loadingJob?.isActive == true) return

        loadingJob = viewModelScope.launch(Dispatchers.IO) {
            service.getProducts()
                .collect {
                    productsFlow.value = it
                }
        }
    }
}