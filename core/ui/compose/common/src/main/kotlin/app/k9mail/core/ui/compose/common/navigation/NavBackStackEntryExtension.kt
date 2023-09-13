package app.k9mail.core.ui.compose.common.navigation

import androidx.navigation.NavBackStackEntry

fun NavBackStackEntry.getArgument(key: String): String {
    return arguments?.getString(key) ?: "Missing argument: $key"
}
