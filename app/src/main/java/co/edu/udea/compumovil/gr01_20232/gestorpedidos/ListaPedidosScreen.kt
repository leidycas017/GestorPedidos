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
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class Pedido(val id: Int, val nombre: String, val fecha: String, val valor: Double, val colaborador: String,val productos: List<Producto>)
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
            fecha = "10/11/2023",
            colaborador = "Miguel",
            productos = listOf(
                Producto(101, "Producto 1", 7.0),
                Producto(102, "Producto 2", 3.0)
            )
        ),
        Pedido(
            id = 2,
            nombre = "Pedido 2",
            fecha = "10/11/2023",
            colaborador = "Juan",
            valor = 15.0,
            productos = listOf(
                Producto(103, "Producto 3", 7.0),
                Producto(104, "Producto 4", 4.0)
            )
        ),
        Pedido(
            id = 3,
            nombre = "Pedido 3",
            fecha = "10/11/2023",
            colaborador = "Marta",
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White, //Card background color
            contentColor = Color.DarkGray  //Card content color,e.g.text
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White) // Fondo blanco del Card
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                Text(
                    text = pedido.nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "${pedido.fecha}",
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Colaborador: ${pedido.colaborador}",
                    fontSize = 14.sp
                )
            }

            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar pedido",
                    tint = Color.Magenta, // Color azul para el ícono de editar
                    modifier = Modifier
                        .clickable {
                            navController.navigate(route = DestinationScreen.ListaProductoScreenDest.route)
                        }
                        .padding(4.dp)
                )
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar pedido",
                    tint = Color.Red, // Color rojo para el ícono de eliminar
                    modifier = Modifier
                        .clickable {
                            // Lógica para eliminar el pedido
                        }
                        .padding(4.dp)
                )
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = Color.Gray, // Color amarillo para el ícono de ubicación
                    modifier = Modifier
                        .clickable {
                            navController.navigate(route = DestinationScreen.MapScreenDest.route)
                        }
                        .padding(4.dp)
                )
            }

            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.BottomStart)
            ) {
                Text(
                    text = "$${pedido.valor}",
                    fontSize = 18.sp,
                    color = Color.Blue
                )
            }
        }
    }
}
