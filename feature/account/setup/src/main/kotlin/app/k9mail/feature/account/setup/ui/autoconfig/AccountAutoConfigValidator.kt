package app.k9mail.feature.account.setup.ui.autoconfig

import app.k9mail.core.common.domain.usecase.validation.ValidationResult
import app.k9mail.feature.account.setup.domain.DomainContract.UseCase
import app.k9mail.feature.account.setup.domain.usecase.ValidateConfigurationApproval
import app.k9mail.feature.account.setup.domain.usecase.ValidateEmailAddress
import app.k9mail.feature.account.setup.domain.usecase.ValidatePassword

internal class AccountAutoConfigValidator(
    private val emailAddressValidator: UseCase.ValidateEmailAddress = ValidateEmailAddress(),
    private val passwordValidator: UseCase.ValidatePassword = ValidatePassword(),
    private val configurationApprovalValidator: UseCase.ValidateConfigurationApproval = ValidateConfigurationApproval(),
) : AccountAutoConfigContract.Validator {

    override fun validateEmailAddress(emailAddress: String): ValidationResult {
        return emailAddressValidator.execute(emailAddress)
    }

    override fun validatePassword(password: String): ValidationResult {
        return passwordValidator.execute(password)
    }

    override fun validateConfigurationApproval(
        isApproved: Boolean?,
        isAutoDiscoveryTrusted: Boolean?,
    ): ValidationResult {
        return configurationApprovalValidator.execute(isApproved, isAutoDiscoveryTrusted)
    }
}
