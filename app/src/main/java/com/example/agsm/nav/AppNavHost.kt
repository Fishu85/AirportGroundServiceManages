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
import com.example.agsm.airport.AirportViewModel
import com.example.agsm.airport.ui.AirportDetailsScreen
import com.example.agsm.airport.ui.AirportListScreen
import com.example.agsm.airport.ui.AirportOperationsScreen
import com.example.agsm.apron.ApronViewModel
import com.example.agsm.auth.AuthViewModel
import com.example.agsm.auth.AuthViewModelFactory
import com.example.agsm.auth.ui.LoginScreen
import com.example.agsm.auth.ui.RegisterScreen
import com.example.agsm.flight.FlightViewModel
import com.example.agsm.home.ui.HomeScreen
import com.example.agsm.nav.ui.AppTopBar
import com.example.agsm.nav.ui.BottomNavBar
import com.example.agsm.stand.StandViewModel
import com.example.agsm.user.UserViewModel
import com.example.agsm.user.ui.ProfileScreen

@Composable
fun AppNavHost() {
    val nav = rememberNavController()

    //observing current route
    val currentRoute = nav.currentBackStackEntryFlow
        .collectAsState(initial = nav.currentBackStackEntry)
        .value?.destination?.route

    val hideTopBottomBar = currentRoute in listOf("login", "register", "profile")

    val context = LocalContext.current
    val authVm: AuthViewModel = viewModel(factory = AuthViewModelFactory(context))
    val userVm: UserViewModel = viewModel()
    val airportVm: AirportViewModel = viewModel()
    val apronVm: ApronViewModel = viewModel()
    val standVm: StandViewModel = viewModel()
    val flightVm: FlightViewModel = viewModel()

    authVm.onUserLoggedIn = {
        userVm.loadUser()
    }

    Scaffold(
        topBar = {
            if (!hideTopBottomBar) {
                AppTopBar(nav)
            }
        },
        bottomBar = {
            if (!hideTopBottomBar) {
                BottomNavBar(nav)
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
                        nav.navigate("airport_details") {
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
                        nav.navigate("airport_details") {
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
                            popUpTo("airport_details") {
                                inclusive = true
                            }
                        }
                    },
                    onReturn = {
                        nav.navigate("airport_details") {
                            popUpTo("profile") {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable("airport_list") {
                AirportListScreen(
                    userVm = userVm,
                    airportVm = airportVm
                )
            }

            composable("airport_details") {
                AirportDetailsScreen(
                    userVm = userVm,
                    airportVm = airportVm
                )
            }

            composable("airport_operations") {
                AirportOperationsScreen(
                    userVm = userVm,
                    airportVm = airportVm,
                    apronVm = apronVm,
                    standVm = standVm,
                    flightVm = flightVm
                )
            }
        }
    }
}