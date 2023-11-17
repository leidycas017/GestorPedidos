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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.lifecycle.viewmodel.compose.viewModel
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
    val showLoginForm = rememberSaveable{
        mutableStateOf(true)
    }
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
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Ocupa 2/3 del espacio disponible
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
                    .weight(5f), // Ocupa 4/3 del espacio disponible
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                )

            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(10.dp)
                ) {
                    if (showLoginForm.value){
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "Inicia Sesión",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(46, 0, 77), // Morado en formato RGB
                            fontFamily = FontFamily.Serif,
                            modifier = Modifier.padding(8.dp)
                        )

                        UserForm(
                            isCreateAccount = false
                        ){
                                email,password->
                            Log.d("Login ","Logueando con $email y $password")

                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.LightGray)
                                .clickable {
                                    val opciones = GoogleSignInOptions
                                        .Builder(
                                            GoogleSignInOptions.DEFAULT_SIGN_IN
                                        )
                                        .requestIdToken(token)
                                        .requestEmail()
                                        .build()
                                    val googleSignInClient =
                                        GoogleSignIn.getClient(context, opciones)
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

                    }else{
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "Registrate",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(46, 0, 77), // Morado en formato RGB
                            fontFamily = FontFamily.Serif,
                            modifier = Modifier.padding(8.dp)
                        )
                        UserForm(
                            isCreateAccount = true
                        ){
                                email,password->
                            Log.d("Registro ","Registrando con $email y $password")
                        }
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val text1 =
                            if(showLoginForm.value)"¿No tienes cuenta?"
                            else "¿ya tienes cuenta?"
                        val text2 =
                            if(showLoginForm.value)"Registrate"
                            else "Inicia sesion"
                        Text(text = text1)
                        Text(
                            text = text2,
                            modifier = Modifier
                                .clickable { showLoginForm.value = !showLoginForm.value }
                                .padding(start = 5.dp),
                            color= Color.Blue
                            )
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserForm(
    isCreateAccount: Boolean = false,
    onDone: (String,String) -> Unit = {email,pwd ->}
){
    val email = rememberSaveable{
        mutableStateOf("")
    }
    val password = rememberSaveable{
        mutableStateOf("")
    }
    val passwordVisible = rememberSaveable{
        mutableStateOf(false)
    }

    val valido = remember(email.value, password.value){
        email.value.trim().isNotEmpty() &&
                password.value.trim().isNotEmpty()

    }
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        EmailInput(
            emailState = email
        )
        PasswordInput(
            passwordState = password,
            labelId = "Password",
            passwordVisible = passwordVisible
        )
        SubmitButton(
            textId = if (isCreateAccount)"Registrar cuenta" else "Inicar sesion",
            inputValido = valido
        ){
            onDone(email.value.trim(),password.value.trim())
            keyboardController?.hide()
        }

    }
}

@Composable
fun SubmitButton(
    textId: String,
    inputValido: Boolean,
    onClic: ()-> Unit
) {
    Button(onClick = onClic,
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = CircleShape,
        enabled = inputValido
        ) {
        Text(text = textId,
        modifier = Modifier
            .padding(5.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInput(
    passwordState: MutableState<String>,
    labelId: String,
    passwordVisible: MutableState<Boolean>
) {
    val visualTransformation = if(passwordVisible.value)
        VisualTransformation.None
    else PasswordVisualTransformation()

    OutlinedTextField(
        value = passwordState.value,
        onValueChange = {passwordState.value = it},
        label = { Text(text = labelId)},
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        visualTransformation = visualTransformation,
        trailingIcon = {
            if (passwordState.value.isNotBlank()){
                PasswordVisibleIcon(passwordVisible)
            }else null
        }
    )
}

@Composable
fun PasswordVisibleIcon(
    passwordVisible: MutableState<Boolean>
) {
    val image =
        if(passwordVisible.value)
            Icons.Default.VisibilityOff
        else
            Icons.Default.Visibility
    IconButton(onClick = {
        passwordVisible.value = !passwordVisible.value
    }) {
        Icon(
            imageVector = image,
            contentDescription = ""
        )
    }
}

@Composable
fun EmailInput(
    emailState: MutableState<String>,
    labelID: String = "Email"
) {
    InputField(
        valueState = emailState,
        labelID = labelID,
        keyboardType = KeyboardType.Email
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    valueState: MutableState<String>,
    labelID: String,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it},
        label = { Text(text = labelID)},
        singleLine = isSingleLine,
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        )
    )
}
