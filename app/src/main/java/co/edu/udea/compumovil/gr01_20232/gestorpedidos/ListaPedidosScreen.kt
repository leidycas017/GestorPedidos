package co.edu.udea.compumovil.gr01_20232.gestorpedidos
import android.graphics.drawable.Icon
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
import androidx.compose.foundation.border
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.colorResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaPedidosScreen(navController: NavController) {
    val sharedViewModel: SharedViewModel = viewModel()
    val userName = sharedViewModel.getLoginUserName()
    val pedidos = listOf(
        "Pedido 1",
        "Pedido 2",
        "Pedido 3",
        "Pedido 4"
    )
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
                    PedidoItem(pedido = pedido)
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
fun PedidoItem(pedido: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
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
                // Lógica para eliminar el pedido
            }
        )

        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Editar pedido",
            modifier = Modifier.clickable {
                // Lógica para editar el pedido
            }
        )
    }
}