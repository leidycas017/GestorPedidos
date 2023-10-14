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
import androidx.compose.foundation.border
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.res.colorResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaPedidosScreen(navController: NavController) {
    val sharedViewModel: SharedViewModel = viewModel()
    // Traer el username ingresado en LoginScreen
    val userName = sharedViewModel.getLoginUserName()

    // Lista de pedidos manual para pruebas de diseño
    val pedidos = listOf(
        "Pedido 1",
        "Pedido 2",
        "Pedido 3",
        "Pedido 4"
    )

    // Scaffold con la barra de navegación superior, ventana con scroll
    Scaffold(
        topBar = {
            GradientTopAppBar(title = "Lista de Pedidos", userName)
        },
        content = {padding ->
            // Contenido de la lista de pedidos
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                // Espacio entre la barra de navegación y la lista de pedidos
                Spacer(modifier = Modifier.height(16.dp))

                // Lista de pedidos
                LazyColumn {
                    items(pedidos) { pedido ->
                        PedidoItem(pedido = pedido)
                    }
                }
            }
        },
        floatingActionButton = {
            // Botón flotante para agregar un nuevo pedido
            FloatingActionButton(
                onClick = {
                    //funcionalidades del boton pendientes
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar pedido"
                )
            }
        }
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradientTopAppBar(title: String, userName: String, modifier: Modifier = Modifier) {
    // Gradiente horizontal del topbar
    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(
            Color(255, 128, 0),  // Shade 1
            Color(255, 153, 51), // Shade 2
            Color(239, 127, 26)  // Shade 3
        )
    )

    TopAppBar(
        title = {
            Text(text = title)
                },
        modifier = modifier
            .background(gradientBrush)
            .fillMaxSize(),
        actions = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = userName,
                    color = Color.White
                )
            }
        }
    )

}
@Composable
fun PedidoItem(pedido: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White)
            .border(1.dp,Color.Gray)
    ) {
        Text(
            text = pedido,
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        )

        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Eliminar pedido",
            modifier = Modifier.clickable {
                // Funcionalidad de eliminar
            }
        )

        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Editar pedido",
            modifier = Modifier.clickable {
                // Funcionalidad de editar
            }
        )
    }
}