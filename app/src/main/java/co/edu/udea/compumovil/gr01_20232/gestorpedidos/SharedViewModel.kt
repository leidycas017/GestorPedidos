package co.edu.udea.compumovil.gr01_20232.gestorpedidos
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private var username: String = ""
    private var password: String = ""
    private val _pedidos = MutableLiveData<List<Pedido>>()
    private val _productoSeleccionado = MutableLiveData<Producto>()
    val pedidos: LiveData<List<Pedido>> get() = _pedidos

    val productoSeleccionado: LiveData<Producto> = _productoSeleccionado


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