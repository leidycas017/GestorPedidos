package co.edu.udea.compumovil.gr01_20232.gestorpedidos



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.navigation.NavController
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme

data class Pedido(val id: Int, val nombre: String, val valor: Double, val productos: List<Producto>)
data class Producto(val id: Int, var nombre: String, val valor: Number)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaPedidosScreen(navController: NavController,myViewModel: SharedViewModel) {
    val userName = myViewModel.getLoginUserName()
    val pedidos = listOf(
        Pedido(
            id = 1,
            nombre = "Pedido 1",
            valor = 10.0,
            productos = listOf(
                Producto(101, "Producto 1", 7.0),
                Producto(102, "Producto 2", 3.0)
            )
        ),
        Pedido(
            id = 2,
            nombre = "Pedido 2",
            valor = 15.0,
            productos = listOf(
                Producto(103, "Producto 3", 7.0),
                Producto(104, "Producto 4", 4.0)
            )
        ),
        Pedido(
            id = 3,
            nombre = "Pedido 3",
            valor = 20.0,
            productos = listOf(
                Producto(105, "Producto 5", 8.0),
                Producto(106, "Producto 6", 2.0)
            )
        )
    )
    myViewModel.setPedidos(pedidos)

    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(
            Color(255, 128, 0),  // Shade 1
            Color(255, 153, 51), // Shade 2
            Color(239, 127, 26)  // Shade 3
        )
    )

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background) // Establece el color de fondo naranja
            ) {
                TopAppBar(
                    title = { Text(text = "Lista de Pedidos") },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    actions = {
                        Text(
                            text = userName,
                            color = Color.White,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    }
                )
            }
        },
        content = {paddingValues ->
            LazyColumn(
                contentPadding = paddingValues,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(pedidos) { pedido ->
                    PedidoItem(pedido = pedido, navController)
                    Divider(
                        color = Color.Gray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = {  },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Filled.Add, "Large floating action button")
            }
        }

    )
}
@Composable
fun PedidoItem(pedido: Pedido, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = pedido.nombre,
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        )

        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Eliminar pedido",
            modifier = Modifier.clickable {
                // Lógica para eliminar el pedido
            }
        )

        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Editar pedido",
            modifier = Modifier.clickable {
                navController.navigate(route = DestinationScreen.ListaProductoScreenDest.route)
            }
        )
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Location",
            modifier = Modifier.clickable {
                navController.navigate(route = DestinationScreen.MapScreenDest.route)
            }
        )
    }
}