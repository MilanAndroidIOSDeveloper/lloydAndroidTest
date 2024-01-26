package com.androidtest.bymilanchothani.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.androidtest.bymilanchothani.LloydAndroidTestApplication
import com.androidtest.bymilanchothani.api.ApiService
import com.androidtest.bymilanchothani.models.Nomination
import com.androidtest.bymilanchothani.models.Nominee
import com.cube.cubeacademy.lib.dao.NomineeDao
import com.cube.cubeacademy.lib.dao.NomineeDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val apiService: ApiService) {
    fun getNominationList(): LiveData<List<Nomination>> = liveData {
        val response = withContext(Dispatchers.IO) {
            apiService.getNomination()
        }
        if (response.isSuccessful) {
            val nominations = response.body()?.data
            nominations?.let {
                emit(nominations)
            }
        }
    }

    fun getNomineeList(): LiveData<List<Nominee>> = liveData {

        withContext(Dispatchers.IO) {

            val response = apiService.getNominee()

            if (response.isSuccessful) {
                val nominees = response.body()?.data
                nominees?.let {
                    nomineeDao.deleteAllNominees()
                    nomineeDao.insertAll(nominees)
                    emit(nominees)
                }
            }
        }
    }

    suspend fun createNomination(nomineeId: String, reason: String, process: String): Nomination? {
        return withContext(Dispatchers.IO) {
            val response = apiService.createNomination(
                nomineeId = nomineeId,
                reason = reason,
                process = process
            )
            if (response.isSuccessful) {
                return@withContext response.body()!!.data
            } else {
                return@withContext null
            }
        }
    }

    private val nomineeDao: NomineeDao by lazy {
        NomineeDatabase.getDatabase(LloydAndroidTestApplication.instance).nomineeDao()
    }


}



