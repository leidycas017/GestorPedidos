package co.edu.udea.compumovil.gr01_20232.gestorpedidos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPedidoScreen(navController: NavController,myViewModel: SharedViewModel) {
    val userName = myViewModel.getLoginUserName()
    var nombrePedido by remember { mutableStateOf("") }
    var nombreCliente by remember { mutableStateOf("") }
    var fechaEntrega by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }

    val productos = listOf("Producto 1", "Producto 2", "Producto 3", /* ... */)
    var selectedProductos by remember { mutableStateOf(emptyList<String>()) }
    LaunchedEffect(Unit) {
        myViewModel.getPedidos()
    }
    val pedidos by myViewModel.pedidos.observeAsState(emptyList())


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
                    .background(MaterialTheme.colorScheme.background)
            ) {
                TopAppBar(
                    title = { Text(text = "Agregar Pedido") },
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
                item {
                    TextField(
                        value = nombrePedido,
                        onValueChange = { nombrePedido = it },
                        label = { Text("Nombre del Pedido") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )

                    TextField(
                        value = nombreCliente,
                        onValueChange = { nombreCliente = it },
                        label = { Text("Nombre del Cliente") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                    //Aca irian lo selectores para fecha y hora de entrega
                    TextField(
                        value = direccion,
                        onValueChange = { direccion = it },
                        label = { Text("Dirección de Entrega") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }

                item {
                    //Por aca ira la barra de busqueda que filtrara los porudctos en la BD
                    LazyColumn {
                        items(selectedProductos) { producto ->
                            Text(
                                text = producto,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(onClick = {}) {
                            Text("Agregar Pedido")
                        }

                        Button(onClick = { }) {
                            Text("Cancelar")
                        }
                    }
                }
            }
        }

    )
}