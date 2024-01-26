package com.androidtest.bymilanchothani.api

import com.androidtest.bymilanchothani.models.Nomination
import com.androidtest.bymilanchothani.models.Nominee
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiService {

    @GET("api/nomination")
    suspend fun getNomination(): Response<NominationResponse>

    @GET("api/nominee")
    suspend fun getNominee(): Response<NomineeResponse>

    @POST("api/nomination")
    @FormUrlEncoded
    suspend fun createNomination(
        @Field("nominee_id") nomineeId: String,
        @Field("reason") reason: String,
        @Field("process") process: String
    ): Response<CreateNominationResponse>
}
data class CreateNominationResponse(val data: Nomination)
data class NominationResponse(val data: List<Nomination>)
data class NomineeResponse(val data: List<Nominee>)
