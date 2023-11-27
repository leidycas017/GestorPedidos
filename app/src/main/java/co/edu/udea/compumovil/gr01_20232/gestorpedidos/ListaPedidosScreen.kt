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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale


data class Pedido(
    val address: String,
    val amount: List<Int>,
    val collabName: String ,
    val creationDate: Timestamp ,
    val custName: String ,
    val name: String ,
    val product: List<String> ,
    val shippingDate: Timestamp,
    val status: String ,
    val total: Double
){
    constructor() : this(
        address = "",
        amount = emptyList(),
        collabName = "",
        creationDate = Timestamp.now(),
        custName = "",
        name = "",
        product = emptyList(),
        shippingDate = Timestamp.now(),
        status = "",
        total = 0.0
    )
    fun getFormattedShippingDate(): String {
        val dateFormat = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        return dateFormat.format(shippingDate.toDate())
    }
}
data class Producto(val id: Int, var nombre: String, val valor: Number)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaPedidosScreen(navController: NavController,myViewModel: SharedViewModel) {
    val userName = myViewModel.getLoginUserName()
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
                onClick = {navController.navigate(DestinationScreen.AddPedidoScreenDest.route)},
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
            containerColor = Color.White,
            contentColor = Color.DarkGray
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                Text(
                    text = pedido.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Entregar el: ${pedido.getFormattedShippingDate()}",
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Colaborador: ${pedido.collabName}",
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
                    tint = Color.Magenta,
                    modifier = Modifier
                        .clickable {
                            navController.navigate(route = DestinationScreen.ListaProductoScreenDest.route)
                        }
                        .padding(4.dp)
                )
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar pedido",
                    tint = Color.Red,
                    modifier = Modifier
                        .clickable {

                        }
                        .padding(4.dp)
                )
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = Color.Gray,
                    modifier = Modifier
                        .clickable {
                            navController.navigate(route = DestinationScreen.MapScreenDest.route)
                        }
                        .padding(4.dp)
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.BottomStart)
            ) {
                Text(
                    text = "$${pedido.total}",
                    fontSize = 18.sp,
                    color = Color.Blue
                )
            }
        }
    }
}
