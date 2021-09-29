package com.arbaelbarca.foodapp.viewmodel

import com.arbaelbarca.foodapp.repository.RepositoryExample
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModelExample @Inject constructor(repositoryExample: RepositoryExample) {
}