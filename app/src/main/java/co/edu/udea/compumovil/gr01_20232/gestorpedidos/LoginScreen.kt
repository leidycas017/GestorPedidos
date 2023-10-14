package co.edu.udea.compumovil.gr01_20232.gestorpedidos
import androidx.compose.ui.Alignment
import androidx.compose.runtime.remember
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.navigation.NavController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.res.colorResource

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)
//usar fuente Oswald de google fonts
val fontName = GoogleFont("Oswald")

//usar fuentes de google, credenciales en values>font_certs.xml
val fontFamily = FontFamily(
    Font(
        googleFont = fontName,
        fontProvider = provider,
        weight = FontWeight.Normal,
        style = FontStyle.Italic
    )
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    val sharedViewModel: SharedViewModel = viewModel()
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(239, 127, 26), // Amarillo oscuro
                        Color(255, 153, 51), // Amarillo
                        Color(255, 128, 0), // Amarillo más oscuro
                        Color(255, 102, 0), // Naranja
                        Color(255, 77, 0)  // Naranja oscuro
                    )
                )
            )
    )   {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.logo2), // Reemplaza con el recurso de tu logo
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f) // Ocupa 2/3 del espacio disponible
                    .fillMaxHeight(0.4f) // Reducir la altura a un 40%
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally) // Centrar horizontalmente
            )

            // Título de la app
            Text(
                text = "ListWise", // Reemplaza con el nombre de tu app
                fontFamily = fontFamily,
                fontSize = 40.sp, // Tamaño del texto más grande
                fontWeight = FontWeight.Bold, // Fuente en negrita
                modifier = Modifier
                    .weight(1f) // Ocupa 1/3 del espacio disponible
                    .align(Alignment.CenterHorizontally) // Centrar horizontalmente
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(4f) // Ocupa 4/3 del espacio disponible
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 100.dp))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    TextField(
                        value = userName,
                        onValueChange = { userName = it },
                        label = { Text("Nombre de usuario") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )

                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )

                    Button(
                        onClick = {
                            //guardar informacion de incicio de sesion en el view model
                            sharedViewModel.setLoginInfo(userName, password)
                            navController.navigate(route = DestinationScreen.ListaPedidosScreenDest.route)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)

                    ) {
                        Text(
                            text = "Login",
                            color = Color.White
                        )
                    }

                    Text(
                        text = "¿No tienes una cuenta? Regístrate",
                        color = Color.Blue,
                        modifier = Modifier
                            .clickable {
                                //navegacion a pantalla de registro
                            }
                            .padding(8.dp)
                    )

                    Button(
                        onClick = {
                            //Implementacion inicio de sesion con google
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)

                    ) {
                        Text(
                            text = "Iniciar sesion con Google",
                            color = Color.White // Color de texto en blanco
                        )
                    }
                }
            }
        }
    }
}
