package app.k9mail.feature.account.common

import app.k9mail.feature.account.common.domain.entity.Account

interface AccountCommonExternalContract {

    fun interface AccountLoader {
        suspend fun loadAccount(accountUuid: String): Account?
    }
}
