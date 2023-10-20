package co.edu.udea.compumovil.gr01_20232.gestorpedidos

import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Divider
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.PackageManagerCompat
import androidx.navigation.NavController





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaProductosScreen(navController: NavController, pedidoId: Int, myViewModel: SharedViewModel) {
    val pedido = myViewModel.getPedidos()?.find { it.id == pedidoId }
    var producto by remember { mutableStateOf("") }
    var valor by remember { mutableStateOf("") }

    val productoSeleccionado:Producto? by myViewModel.productoSeleccionado.observeAsState(initial = pedido?.productos?.get(0))




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

                LazyColumn(
                    contentPadding = paddingValues,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.mic),
                                contentDescription = "mic",
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.CenterVertically)
                            )

                            TextField(
                                value = productoSeleccionado?.nombre ?: "",
                                onValueChange = { producto = it},
                                label = { Text("Producto") },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                                    .align(Alignment.CenterVertically)
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.mic),
                                contentDescription = "mic",
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.CenterVertically)
                            )

                            TextField(
                                value = productoSeleccionado?.valor.toString() ?: "",
                                onValueChange = { valor = it},
                                label = { Text("Valor") },
                                modifier = Modifier
                                    .weight(0.5f)
                                    .padding(8.dp)
                                    .align(Alignment.CenterVertically)
                            )

                            Icon(
                                imageVector = Icons.Default.AddCircle,
                                contentDescription = "add",
                                modifier = Modifier
                                    .size(30.dp)
                                    .align(Alignment.CenterVertically)
                            )

                        }
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                        ) {
                            TableCell(text = "PRODUCTO", weight = .5f)
                            TableCell(text = "VALOR", weight = .5f)
                        }
                    }
                    if (pedido != null) {
                        items(pedido.productos) { producto ->
                            ProductoItem(producto = producto, myViewModel)
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
                            .padding(30.dp),
                        fontWeight = FontWeight.Bold

                    )
                }
            }
        },

        floatingActionButton = {
            val context = LocalContext.current
            LargeFloatingActionButton(
                onClick = {
                    val textToShare = "¡Comparte este contenido en WhatsApp!"
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.setPackage("com.whatsapp") // Especifica WhatsApp

                    // Agrega el texto que deseas compartir
                    intent.putExtra(Intent.EXTRA_TEXT, textToShare)

                    // Verifica si WhatsApp está instalado en el dispositivo

                    val packageManager = context.packageManager
                    val whatsappInstalled = isWhatsAppInstalled(packageManager)

                    if (whatsappInstalled) {
                        try {
                            // Inicia la actividad de compartir en WhatsApp
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            Toast.makeText(
                                context,
                                "No se pudo abrir WhatsApp",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "WhatsApp no está instalado en tu dispositivo",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Filled.Share, "Large floating action button")
            }
        }

    )

}

private fun isWhatsAppInstalled(packageManager: PackageManager): Boolean {
    val packages = packageManager.getInstalledApplications(0)
    for (packageInfo in packages) {
        if (packageInfo.packageName == "com.whatsapp") {
            return true
        }
    }
    return false
}


@Composable
fun ProductoItem(producto: Producto, myViewModel: SharedViewModel) {
    val productoSeleccionado:Producto? by myViewModel.productoSeleccionado.observeAsState(initial = producto)


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                myViewModel.setProductoSeleccionado(producto)
            }
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

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        modifier = Modifier
            .weight(1f)
            .padding(16.dp),
        fontWeight = FontWeight.Bold
    )
}


