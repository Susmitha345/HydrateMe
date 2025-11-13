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

import kotlinx.serialization.Serializable

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(navcontroller: NavHostController){

    NavHost(navController = navcontroller, startDestination = GraphRoutes.Login){

//        composable<GraphRoutes.Splash> {
//            SplashScreen(
//                onTimeout = {
//                    val destination = if (FirebaseAuth.getInstance().currentUser != null) GraphRoutes.DashBoard else GraphRoutes.Login
//                    navcontroller.navigate(destination) {
//                        popUpTo<GraphRoutes.Splash> { inclusive = true }
//                    }
//                }
//            )
//        }

        composable<GraphRoutes.Login>{
         LoginRoot(
             onLoginSuccess = {
                 navcontroller.navigate(GraphRoutes.DashBoard){
                     popUpTo(GraphRoutes.Login){
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
                    navcontroller.navigate(GraphRoutes.Login) {
                        popUpTo(GraphRoutes.Register) { inclusive = true }
                    }
                },
                onCreateAccountSuccess = {
                    navcontroller.navigate(GraphRoutes.DashBoard) {
                        popUpTo(GraphRoutes.Register) { inclusive = true }
                    }
                }
            )
        }

        composable<GraphRoutes.Forgot>{
            ForgotRoot(
                onBackToLogin = {
                    navcontroller.navigate(GraphRoutes.Login) {
                        popUpTo(GraphRoutes.Forgot) { inclusive = true }
                    }
                }
            )
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
