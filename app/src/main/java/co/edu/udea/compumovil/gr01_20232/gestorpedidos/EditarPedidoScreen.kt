package co.edu.udea.compumovil.gr01_20232.gestorpedidos

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarPedidoScreen(navController: NavController, pedidoId: Int, myViewModel: SharedViewModel) {
    val pedido = myViewModel.getPedidos()?.find { it.id == pedidoId }


    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                TopAppBar(
                    title = {
                        pedido?.let {
                            Text(text = "Editar Pedido: ${it.nombre}")
                        } ?: run {
                            Text(text = "Pedido no encontrado")
                        }
                    },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onSecondary
                    ),

                )
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = "Producto", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "Valor", fontWeight = FontWeight.Bold)
                }

                LazyColumn(
                    contentPadding = paddingValues,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (pedido != null) {
                        items(pedido.productos) { producto ->
                            ProductoItem(producto = producto, navController)
                            Divider(
                                color = Color.Gray,
                                thickness = 1.dp,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                }


                if (pedido != null) {
                    Text(
                        text = "Valor Total: ${pedido.valor}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)

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
fun ProductoItem(producto: Producto, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = producto.nombre,
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        )
        Text(
            text = "$ " + producto.valor.toString(),
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        )

        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Eliminar producto",
            modifier = Modifier.clickable {
                // Lógica para eliminar el pedido
            }
        )

        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Editar producto",
            modifier = Modifier.clickable {

            }
        )
    }
}

