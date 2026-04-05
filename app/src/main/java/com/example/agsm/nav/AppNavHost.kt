package com.example.agsm.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.agsm.auth.ui.LoginScreen
import com.example.agsm.auth.ui.RegisterScreen
import com.example.agsm.home.ui.HomeScreen

@Composable
fun AppNavHost() {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = "login") {

        composable("login") {
            LoginScreen(onLoggedIn = {
                nav.navigate("home") {
                    popUpTo("login") {
                        inclusive = true
                    }
                }
            },
                onRegisterClick = {
                    nav.navigate("register") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable("register") {
            RegisterScreen(onRegistered = {
                nav.navigate("home") {
                    popUpTo("register") {
                        inclusive = true
                    }
                }
            },
            onLoginClick = {
                nav.navigate("login") {
                    popUpTo("register") {
                        inclusive = true
                    }
                }
            }
            )
        }

        composable("home") {
            HomeScreen()
        }
    }
}