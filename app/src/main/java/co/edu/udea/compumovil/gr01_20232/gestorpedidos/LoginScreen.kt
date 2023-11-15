package co.edu.udea.compumovil.gr01_20232.gestorpedidos
import android.text.style.BackgroundColorSpan
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

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
fun LoginScreen(navController: NavController,myViewModel: SharedViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val token = "450434930781-d5a36ddtvqoh64tdeie3oad4l0oo8j0o.apps.googleusercontent.com"
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts
            .StartActivityForResult()
    ){
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken,null)
            myViewModel.signInWithGoogleCredential(credential){
                navController.navigate(route = DestinationScreen.ListaPedidosScreenDest.route)
            }
        }catch (e:Exception){
            Log.d("LoginScreen","Google SignIn fallo")
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(221, 206, 255),  // Morado suave
                        Color(187, 167, 255),  // Morado claro
                        Color(162, 141, 255),  // Morado medio
                        Color(128, 109, 255),  // Morado oscuro
                        Color(77, 58, 255)    // Morado profundo
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
                painter = painterResource(id = R.drawable.logo), // Reemplaza con el recurso de tu logo
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
                    .padding(10.dp)
                    .fillMaxWidth()
                    .weight(4f) // Ocupa 4/3 del espacio disponible
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 100.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                )

            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(16.dp)
                        .background(Color(255,255,255))
                ) {
                    TextField(
                        value = userName,
                        onValueChange = { userName = it },
                        label = { Text("Nombre de usuario") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(Color.White)
                    )

                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(Color.White)
                    )

                    Button(
                        onClick = {

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

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),

                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center

                    ){

                        Text(
                            text = "¿No tienes una cuenta? Regístrate",
                            color = Color.Blue,
                            modifier = Modifier
                                .clickable {
                                    //navegacion a pantalla de registro
                                }
                                .padding(8.dp)
                        )

                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.LightGray)
                            .clickable {
                                val opciones = GoogleSignInOptions.Builder(
                                    GoogleSignInOptions.DEFAULT_SIGN_IN
                                )
                                    .requestIdToken(token)
                                    .requestEmail()
                                    .build()
                                val googleSignInClient = GoogleSignIn.getClient(context,opciones)
                                launcher.launch(googleSignInClient.signInIntent)
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center

                    ){
                        Image(
                            painter = painterResource(id = R.drawable.ic_google),
                            contentDescription = "Google Login",
                            modifier = Modifier
                                .padding(10.dp)
                                .size(30.dp)
                        )
                        Text("Iniciar sesion con Google",
                        fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )

                    }
                }
            }
        }
    }
}
