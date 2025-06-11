package br.com.sigmaonline.echojournal.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import br.com.sigmaonline.echojournal.echos.presentation.create_echo.CreateEchoRoot
import br.com.sigmaonline.echojournal.echos.presentation.echos.EchosRoot
import br.com.sigmaonline.echojournal.echos.presentation.settings.SettingsRoot
import br.com.sigmaonline.echojournal.echos.presentation.util.toCreateEchoRoute

const val ACTION_CREATE_ECHO = "br.com.sigmaonline.CREATE_ECHO"

@Composable
fun NavigationRoot(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.Echos(
            startRecording = false
        )
    ) {
        composable<NavigationRoute.Echos>(
            deepLinks = listOf(
                navDeepLink<NavigationRoute.Echos>(
                    basePath = "https://echojournal.com/echos"
                ) {
                    action = ACTION_CREATE_ECHO
                }
            )
        ) {
            EchosRoot(
                onNavigateToCreateEcho = { details ->
                    navController.navigate(details.toCreateEchoRoute())
                },
                onNavigateToSettings = {
                    navController.navigate(NavigationRoute.Settings)
                }
            )
        }
        composable<NavigationRoute.CreateEcho> {
            CreateEchoRoot(
                onConfirmLeave = navController::navigateUp
            )
        }
        composable<NavigationRoute.Settings> {
            SettingsRoot(
                onGoBack = navController::navigateUp
            )
        }
    }
}