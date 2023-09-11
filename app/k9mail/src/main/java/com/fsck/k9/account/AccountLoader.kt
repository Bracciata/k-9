package com.fsck.k9.account

import app.k9mail.feature.account.common.AccountCommonExternalContract
import app.k9mail.feature.account.common.domain.entity.Account
import app.k9mail.feature.account.common.domain.entity.AccountOptions
import com.fsck.k9.Preferences
import com.fsck.k9.logging.Timber
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.fsck.k9.Account as K9Account

class AccountLoader(
    private val preferences: Preferences,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : AccountCommonExternalContract.AccountLoader {

    @Suppress("TooGenericExceptionCaught")
    override suspend fun loadAccount(accountUuid: String): Account? {
        return try {
            withContext(coroutineDispatcher) {
                load(accountUuid)
            }
        } catch (e: Exception) {
            Timber.e(e, "Error while loading account")

            null
        }
    }

    private fun load(accountUuid: String): Account? {
        val existingAccount = preferences.getAccount(accountUuid)

        return if (existingAccount != null) {
            mapToAccount(existingAccount)
        } else {
            null
        }
    }

    private fun mapToAccount(account: K9Account): Account {
        return Account(
            uuid = account.uuid,
            emailAddress = account.email,
            incomingServerSettings = account.incomingServerSettings,
            outgoingServerSettings = account.outgoingServerSettings,
            authorizationState = account.oAuthState,
            options = AccountOptions(
                accountName = account.name ?: "Undefined",
                displayName = account.senderName ?: "Undefined",
                emailSignature = account.signature ?: "",
                checkFrequencyInMinutes = account.automaticCheckIntervalMinutes,
                showNotification = account.isNotifyNewMail,
                messageDisplayCount = account.displayCount,
            ),
        )
    }
}
