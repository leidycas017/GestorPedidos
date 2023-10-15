package co.edu.udea.compumovil.gr01_20232.gestorpedidos
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.ViewModelProvider
class MainActivity : ComponentActivity() {
    private lateinit var myViewModel: SharedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Inicializacion del viewmodel
        myViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        setContent {
            setContent {
                Surface(color = MaterialTheme.colorScheme.background) {
                    NavigationScreen(myViewModel)
                }
            }
        }

    }
}

