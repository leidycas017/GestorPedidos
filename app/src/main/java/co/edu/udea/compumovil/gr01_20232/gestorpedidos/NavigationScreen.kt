package co.edu.udea.compumovil.gr01_20232.gestorpedidos

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationScreen() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = DestinationScreen.SplashScreenDest.route
    ) {
        composable(route = DestinationScreen.SplashScreenDest.route) {
            SplashScreen(navController = navController)
        }

        composable(route = DestinationScreen.LoginScreenDest.route) {
            LoginScreen(navController = navController)
        }

        composable(route = DestinationScreen.ListaPedidosScreenDest.route) {
            ListaPedidosScreen(navController = navController)
        }
    }
}