package com.uk.ac.tees.mad.hydrateme.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.uk.ac.tees.mad.hydrateme.presentation.auth.create_account.CreateAccountRoot
import com.uk.ac.tees.mad.hydrateme.presentation.auth.forgot.ForgotRoot
import com.uk.ac.tees.mad.hydrateme.presentation.auth.login.LoginRoot
import com.uk.ac.tees.mad.hydrateme.presentation.home.HomeRoot
import com.uk.ac.tees.mad.hydrateme.presentation.splash.SplashScreen
import kotlinx.serialization.Serializable

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(navcontroller: NavHostController){

    NavHost(navController = navcontroller, startDestination = Screen.SplashScreen.route){

        composable(Screen.SplashScreen.route) {
            SplashScreen(navController = navcontroller)
        }

        composable(Screen.LoginScreen.route){
            LoginRoot(
                onLoginSuccess = {
                    navcontroller.navigate(Screen.HomeScreen.route){
                        popUpTo(Screen.LoginScreen.route){
                            inclusive = true
                        }
                    }
                },
                onGoToCreateAccount = { navcontroller.navigate(GraphRoutes.Register) },
                onGoToForgotPassword = { navcontroller.navigate(GraphRoutes.Forgot) }
            )
        }

        composable<GraphRoutes.Register>{
            CreateAccountRoot(
                onSignInClick = {
                    navcontroller.navigate(Screen.LoginScreen.route) {
                        popUpTo(GraphRoutes.Register) { inclusive = true }
                    }
                },
                onCreateAccountSuccess = {
                    navcontroller.navigate(Screen.LoginScreen.route) {
                        popUpTo(GraphRoutes.Register) { inclusive = true }
                    }
                }
            )
        }

        composable<GraphRoutes.Forgot>{
            ForgotRoot(
                onBackToLogin = {
                    navcontroller.navigate(Screen.LoginScreen.route) {
                        popUpTo(GraphRoutes.Forgot) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.HomeScreen.route) {
            HomeRoot()
        }




    }

}

sealed class GraphRoutes {
    @Serializable
    data object Splash : GraphRoutes()
    @Serializable
    data object Login : GraphRoutes()
    @Serializable
    data object Register : GraphRoutes()
    @Serializable
    data object DashBoard : GraphRoutes()
    @Serializable
    data object Forgot : GraphRoutes()

    @Serializable
    data object Profile : GraphRoutes()

}
