package com.androidtest.bymilanchothani.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.androidtest.bymilanchothani.models.Nomination
import com.androidtest.bymilanchothani.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateNominationViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    suspend fun createNomination(
        nomineeId: String,
        reason: String,
        process: String
    ): Nomination? {
        return repository.createNomination(nomineeId, reason, process)
    }
}