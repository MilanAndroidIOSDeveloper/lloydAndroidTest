package com.androidtest.bymilanchothani.di

import com.androidtest.bymilanchothani.api.ApiService
import com.androidtest.bymilanchothani.api.CreateNominationResponse
import com.androidtest.bymilanchothani.api.NominationResponse
import com.androidtest.bymilanchothani.api.NomineeResponse
import com.androidtest.bymilanchothani.models.Nomination
import com.androidtest.bymilanchothani.models.Nominee
import retrofit2.Response


class MockApiService : ApiService {

    override suspend fun getNomination(): Response<NominationResponse> {
        val nominations = listOf(
            Nomination("1", "1", "reason", "process", "2023-10-11", "2023-11-11", ""),
            Nomination("2", "2", "reason", "process", "2023-10-11", "2023-11-11", ""),
            Nomination("1", "3", "reason", "process", "2023-10-11", "2023-11-11", ""),
            Nomination("2", "4", "reason", "process", "2023-10-11", "2023-11-11", ""),
        )
        val nominationResponse = NominationResponse(nominations)
        return Response.success(nominationResponse)
    }

    override suspend fun getNominee(): Response<NomineeResponse> {
        val nominees = listOf(
            Nominee("1", "FirstTest1", "LastTest1"),
            Nominee("2", "FirstTest2", "LastTest2"),
            Nominee("3", "FirstTest3", "LastTest3")
        )
        val nomineeResponse = NomineeResponse(nominees)
        return Response.success(nomineeResponse)
    }

    override suspend fun createNomination(
        nomineeId: String,
        reason: String,
        process: String
    ): Response<CreateNominationResponse> {
        val createdNomination = CreateNominationResponse(
            Nomination("1", nomineeId, reason, process, "2023-10-11", "2023-11-11", "")
        )
        return Response.success(createdNomination)
    }
}