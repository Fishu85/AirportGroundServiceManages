package com.example.agsm.nav

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.agsm.auth.AuthViewModel
import com.example.agsm.auth.AuthViewModelFactory
import com.example.agsm.auth.ui.LoginScreen
import com.example.agsm.auth.ui.RegisterScreen
import com.example.agsm.home.ui.HomeScreen
import com.example.agsm.nav.ui.AppTopBar
import com.example.agsm.user.UserViewModel
import com.example.agsm.user.ui.ProfileScreen

@Composable
fun AppNavHost() {
    val nav = rememberNavController()

    //observing current route
    val currentRoute = nav.currentBackStackEntryFlow
        .collectAsState(initial = nav.currentBackStackEntry)
        .value?.destination?.route

    val hideTopBar = currentRoute in listOf("login", "register", "profile")

    val context = LocalContext.current
    val authVm: AuthViewModel = viewModel(factory = AuthViewModelFactory(context))
    val userVm: UserViewModel = viewModel()

    authVm.onUserLoggedIn = {
        userVm.loadUser()
    }

    Scaffold(
        topBar = {
            if (!hideTopBar) {
                AppTopBar(nav)
            }
        }
    ) { padding ->
        NavHost(
            navController = nav,
            startDestination = "login",
            modifier = Modifier
                .padding(padding)
        ) {

            composable("login") {
                LoginScreen(
                    userVm = userVm,
                    onLoggedIn = {
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
                RegisterScreen(
                    onRegistered = {
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

            composable("profile") {
                ProfileScreen(
                    userVm = userVm,
                    authVm = authVm,
                    onLogout = {
                        nav.navigate("login") {
                            popUpTo("home") {
                                inclusive = true
                            }
                        }
                    },
                    onReturn = {
                        nav.navigate("home") {
                            popUpTo("profile") {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
    }
}