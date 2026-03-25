package ru.arturmineev9.avitotraineeassignment.core.common.datastore.userbalance

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.billingDataStore by preferencesDataStore("user_billing")

@Singleton
class UserBalanceManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private const val DEFAULT_TOKENS_BALANCE = 10000
    }

    private fun getTokensKey(uid: String) = intPreferencesKey("tokens_$uid")

    suspend fun getUserTokens(uid: String): Int {
        val preferences = context.billingDataStore.data.first()
        return preferences[getTokensKey(uid)] ?: DEFAULT_TOKENS_BALANCE
    }

    fun observeUserTokens(uid: String): Flow<Int> {
        return context.billingDataStore.data.map { preferences ->
            preferences[getTokensKey(uid)] ?: DEFAULT_TOKENS_BALANCE
        }
    }

    suspend fun spendTokens(uid: String, amountToSpend: Int) {
        context.billingDataStore.edit { preferences ->
            val currentTokens = preferences[getTokensKey(uid)] ?: DEFAULT_TOKENS_BALANCE
            preferences[getTokensKey(uid)] = maxOf(0, currentTokens - amountToSpend)
        }
    }
}
