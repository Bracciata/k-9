package app.k9mail.feature.launcher.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import app.k9mail.core.ui.compose.common.activity.LocalActivity
import app.k9mail.feature.account.edit.navigation.accountEditRoute
import app.k9mail.feature.account.setup.navigation.accountSetupRoute
import app.k9mail.feature.account.setup.navigation.navigateToAccountSetup
import app.k9mail.feature.launcher.FeatureLauncherExternalContract.AccountSetupFinishedLauncher
import app.k9mail.feature.launcher.FeatureLauncherExternalContract.ImportSettingsLauncher
import app.k9mail.feature.onboarding.navigation.NAVIGATION_ROUTE_ONBOARDING
import app.k9mail.feature.onboarding.navigation.onboardingRoute
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf
import org.koin.compose.koinInject

@Composable
fun FeatureLauncherNavHost(
    navController: NavHostController,
    startDestination: String?,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    startDestinationArguments: ImmutableMap<String, String> = persistentMapOf(),
    importSettingsLauncher: ImportSettingsLauncher = koinInject(),
    accountSetupFinishedLauncher: AccountSetupFinishedLauncher = koinInject(),
) {
    val activity = LocalActivity.current

    NavHost(
        navController = navController,
        startDestination = startDestination ?: NAVIGATION_ROUTE_ONBOARDING,
        modifier = modifier,
    ) {
        onboardingRoute(
            onStart = { navController.navigateToAccountSetup() },
            onImport = { importSettingsLauncher.launch() },
        )
        accountSetupRoute(
            onBack = onBack,
            onFinish = { accountSetupFinishedLauncher.launch(it) },
        )
        accountEditRoute(
            startDestinationArguments = startDestinationArguments,
            onBack = onBack,
            onFinish = { activity.finish() },
        )
    }
}
