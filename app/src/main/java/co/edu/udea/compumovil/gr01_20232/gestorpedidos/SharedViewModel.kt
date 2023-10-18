package co.edu.udea.compumovil.gr01_20232.gestorpedidos
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private var username: String = ""
    private var password: String = ""
    private val _pedidos = MutableLiveData<List<Pedido>>()
    val pedidos: LiveData<List<Pedido>> get() = _pedidos

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

}