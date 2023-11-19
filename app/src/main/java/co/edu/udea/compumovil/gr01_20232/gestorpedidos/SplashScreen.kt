package co.edu.udea.compumovil.gr01_20232.gestorpedidos
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController,myViewModel: SharedViewModel) {

    val scaleAnimation: Animatable<Float, AnimationVector1D> = remember { Animatable(initialValue = 0f) }

    AnimationSplashContent(
        scaleAnimation = scaleAnimation,
        navController = navController,
        durationMillisAnimation = 1500,
        delayScreen = 3000L
    )

    DesignSplashScreen(
        imagePainter = painterResource(id = R.drawable.logo),
        scaleAnimation = scaleAnimation
    )
}
//animacion de entrada
@Composable
fun AnimationSplashContent(
    scaleAnimation: Animatable<Float, AnimationVector1D>,
    navController: NavController,
    durationMillisAnimation: Int,
    delayScreen: Long
) {

    LaunchedEffect(key1 = true) {
        scaleAnimation.animateTo(
            targetValue = 0.5F,
            animationSpec = tween(
                durationMillis = durationMillisAnimation,
                easing = {
                    OvershootInterpolator(3F).getInterpolation(it)
                }
            )
        )

        delay(timeMillis = delayScreen)
        if(FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()){
            navController.navigate(route = DestinationScreen.LoginScreenDest.route) {
                popUpTo(route = DestinationScreen.SplashScreenDest.route) {
                    inclusive = true
                }
            }
        }else{
            navController.navigate(route = DestinationScreen.ListaPedidosScreenDest.route) {
                popUpTo(route = DestinationScreen.SplashScreenDest.route) {
                    inclusive = true
                }
            }
        }

    }
}

//diseño del fondo y ubicacion de los coponentes
@Composable
fun DesignSplashScreen(
    modifier: Modifier = Modifier,
    imagePainter: Painter,
    scaleAnimation: Animatable<Float, AnimationVector1D>
) {
    Box(
        modifier = modifier
            .fillMaxSize()

            //gradiente de fondo
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(221, 206, 255),  // Morado suave
                        Color(187, 167, 255),  // Morado claro
                        Color(162, 141, 255),  // Morado medio
                        Color(128, 109, 255),  // Morado oscuro
                        Color(77, 58, 255)    // Morado profundo
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = imagePainter,
                contentDescription = "Logotipo Splash Screen",
                modifier = modifier
                    .size(400.dp)
                    .scale(scale = scaleAnimation.value),
            )
            Text(
                text = "ListWise", // Reemplaza con el nombre de tu app
                fontFamily = fontFamily,
                fontSize = 50.sp, // Tamaño del texto más grande
                fontWeight = FontWeight.Bold, // Fuente en negrita
                modifier = Modifier
                    .align(Alignment.CenterHorizontally) // Centrar horizontalmente
                    .scale(scale = scaleAnimation.value)
            )
        }
    }
}
