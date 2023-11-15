package co.edu.udea.compumovil.gr01_20232.gestorpedidos
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch


class SharedViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private var username: String = ""
    private var password: String = ""
    private val _pedidos = MutableLiveData<List<Pedido>>()
    private val _productoSeleccionado = MutableLiveData<Producto>()
    val pedidos: LiveData<List<Pedido>> get() = _pedidos

    val productoSeleccionado: LiveData<Producto> = _productoSeleccionado

    fun signInWithGoogleCredential(credential: AuthCredential, home:() -> Unit)
    =viewModelScope.launch {
        try {
            auth.signInWithCredential(credential)
                .addOnCompleteListener {task->
                    Log.d("Viewodel","Login exitoso")
                    home()
                }
                .addOnFailureListener {task->
                    Log.d("Viewmodel", "Fallo a loguear")
                }
        }catch (e:Exception){
            Log.d("Viewmodel","Exception al loguear con  Google: "+"${e.localizedMessage}")
        }
    }


    //LOGIN SCREEN GETTERS AND SETTERS
    fun setLoginInfo(username: String, password: String) {
        this.username = username
        this.password = password
    }


    fun getLoginUserName(): String {
        return username
    }
    fun getLoginUserPassword(): String {
        return password
    }


    fun getPedidos(): List<Pedido>? {
        return _pedidos.value
    }

    fun setPedidos(pedidos: List<Pedido>) {
        _pedidos.value = pedidos
    }

    fun setNombreProductoSeleccionado(nombre:String){
        _productoSeleccionado.value?.nombre ?: nombre
    }

    fun setValorProductoSeleccionado(valor:Double){
        _productoSeleccionado.value?.valor ?: valor
    }

    fun setProductoSeleccionado(productoSeleccionado:Producto){
        _productoSeleccionado.value = productoSeleccionado
    }


}