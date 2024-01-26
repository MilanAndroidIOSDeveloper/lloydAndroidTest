package com.androidtest.bymilanchothani.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.androidtest.bymilanchothani.models.Nomination
import com.androidtest.bymilanchothani.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class YourNominationViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun getNominationList(): LiveData<List<Nomination>> {
        return repository.getNominationList()
    }
}

