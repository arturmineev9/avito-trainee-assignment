package ru.arturmineev9.avitotraineeassignment.core.network.api

interface TokenProvider {
    suspend fun getAccessToken(): String?
}
