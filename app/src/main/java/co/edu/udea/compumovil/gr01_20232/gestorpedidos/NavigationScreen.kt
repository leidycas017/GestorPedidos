package co.edu.udea.compumovil.gr01_20232.gestorpedidos

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationScreen(myViewModel: SharedViewModel) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = DestinationScreen.SplashScreenDest.route
    ) {
        composable(route = DestinationScreen.SplashScreenDest.route) {
            SplashScreen(navController = navController,myViewModel)
        }

        composable(route = DestinationScreen.LoginScreenDest.route) {
            LoginScreen(navController = navController, myViewModel)
        }

        composable(route = DestinationScreen.ListaPedidosScreenDest.route) {
            ListaPedidosScreen(navController = navController,myViewModel)
        }
    }
}