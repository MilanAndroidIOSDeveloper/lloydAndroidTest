package com.androidtest.bymilanchothani.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.androidtest.bymilanchothani.models.Nominee
import com.androidtest.bymilanchothani.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NominationViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun getNomineeList(): LiveData<List<Nominee>> {
        return repository.getNomineeList()
    }
}