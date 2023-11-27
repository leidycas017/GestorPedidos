package co.edu.udea.compumovil.gr01_20232.gestorpedidos

sealed class DestinationScreen(val route: String) {
    object SplashScreenDest : DestinationScreen(route = "splash_screen")
    object LoginScreenDest : DestinationScreen(route = "login_screen")
    object ListaPedidosScreenDest : DestinationScreen(route = "listaPedidos_screen")
    object ListaProductoScreenDest : DestinationScreen(route = "listaProducto_screen")
    object MapScreenDest : DestinationScreen(route = "map_screen")
    object AddPedidoScreenDest: DestinationScreen(route = "addPedido_screen")

}