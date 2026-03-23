package ru.arturmineev9.avitotraineeassignment.core.network.api

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST
import ru.arturmineev9.avitotraineeassignment.core.network.dto.auth.TokenResponse

interface GigaChatAuthApi {
    @FormUrlEncoded
    @POST("api/v2/oauth")
    suspend fun getAccessToken(
        @Header("Authorization") authorization: String,
        @Header("RqUID") rqUid: String,
        @Field("scope") scope: String = "GIGACHAT_API_PERS"
    ): TokenResponse
}
