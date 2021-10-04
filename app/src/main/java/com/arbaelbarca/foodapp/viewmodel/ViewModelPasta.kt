package com.arbaelbarca.foodapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arbaelbarca.foodapp.datasource.remote.response.ResponseMenuPasta
import com.arbaelbarca.foodapp.repository.RepositoryMenuPasta
import com.arbaelbarca.foodapp.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelPasta @Inject constructor(val repositoryMenuPasta: RepositoryMenuPasta) :
    ViewModel() {

    val stateMenuPasta = MutableLiveData<UiState<ResponseMenuPasta>>()
    val stateMenuPastaNext = MutableLiveData<UiState<ResponseMenuPasta>>()

    fun observerMenuPasta() = stateMenuPasta
    fun observerMenuPastaNext() = stateMenuPastaNext

    fun getMenuPasta(textQuery: String, page: Int, perPage: Int) {
        stateMenuPasta.value = UiState.Loading()
        viewModelScope.launch {
            runCatching {
                repositoryMenuPasta.callApiPasta(textQuery, page, perPage)
            }.onSuccess {
                stateMenuPasta.value = UiState.Success(it)
            }.onFailure {
                stateMenuPasta.value = UiState.Failure(it)
            }
        }
    }

    fun getMenuPastaNext(textQuery: String, page: Int, perPage: Int) {
        stateMenuPastaNext.value = UiState.Loading()
        viewModelScope.launch {
            runCatching {
                repositoryMenuPasta.callApiPasta(textQuery, page, perPage)
            }.onSuccess {
                stateMenuPastaNext.value = UiState.Success(it)
            }.onFailure {
                stateMenuPastaNext.value = UiState.Failure(it)
            }
        }
    }
}