package co.edu.udea.compumovil.gr01_20232.gestorpedidos

sealed class DestinationScreen(val route: String) {
    object SplashScreenDest : DestinationScreen(route = "splash_screen")
    object LoginScreenDest : DestinationScreen(route = "login_screen")
    object ListaPedidosScreenDest : DestinationScreen(route = "listaPedidos_screen")
    object EditarPedidoScreenDest : DestinationScreen(route = "editarPedido_screen")

}