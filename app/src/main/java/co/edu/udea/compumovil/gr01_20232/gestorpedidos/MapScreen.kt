package co.edu.udea.compumovil.gr01_20232.gestorpedidos

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(navController: NavController) {
    val medellin = LatLng(6.25184, -75.56359)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(medellin, 10f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = medellin),
            title = "Medellín",
            snippet = "Marker in Medellín, Colombia"
        )
    }
}
