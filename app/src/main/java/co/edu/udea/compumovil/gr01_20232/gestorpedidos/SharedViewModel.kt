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
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SharedViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)
    private var username: String = ""
    private var password: String = ""
    private val _productoSeleccionado = MutableLiveData<Producto>()
    val productoSeleccionado: LiveData<Producto> = _productoSeleccionado


    //PRODUCTS AND ORDERS DB

    val db = Firebase.firestore
    private val pedidosCollection  = db.collection("pedidos")
    private val productsCollection = db.collection("products")


    private val _pedidos = MutableLiveData<List<Pedido>>()
    val pedidos: LiveData<List<Pedido>> get() = _pedidos

    fun getPedidos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = pedidosCollection.get().await()
                val pedidos = result.toObjects(Pedido::class.java)
                _pedidos.postValue(pedidos)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun  addPedido(
        address: String,
        amount: List<Int>,
        custName: String,
        name: String,
        products: List<String>,
        shippingDate:String,
        status: String,
        total: Double
    ) {
        val nuevoPedido = hashMapOf(
            "address" to address,
            "amount" to amount,
            "collabName" to auth.currentUser?.displayName,
            "creationDate" to shippingDate,
            "custName" to custName,
            "name" to name,
            "product" to products,
            "shippingDate" to shippingDate,
            "status" to status,
            "total" to total
        )

        pedidosCollection.add(nuevoPedido)
            .addOnSuccessListener { documentReference ->
                println("Pedido agregado con ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                println("Error al agregar el pedido: $e")
            }
    }




    //LOGIN WITH EMAIL
    fun signInWithEmail(email:String,password: String,home: () -> Unit)
    =viewModelScope.launch{
        try {
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Viewodel", "Login Email exitoso")
                        home()
                    } else {
                        Log.d("Viewmodel","Login con email: ${task.result.toString()}")
                    }

                }
        }catch (e:Exception){
            Log.d("Viewmodel","Exception al loguear con  Email y Contraseña: "+"${e.message}")
        }
    }

    //CREATE USERS
    fun createUserWithEmail(email:String,password: String,home: () -> Unit) {
        if (_loading.value == false){
            _loading.value = true
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(){ task->
                    if (task.isSuccessful){
                        val displayName =
                            task.result.user?.email?.split("@")?.get(0)
                        createUser(displayName)
                        home()
                    }else{
                        Log.d("Viewmodel","createUserWithEmail: ${task.result.toString()}")
                    }
                    _loading.value = false
                }

        }

    }

    private fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid
        val user = mutableMapOf<String,Any>()
        user["user_id"] = userId.toString()
        user["display_name"] = displayName.toString()
        FirebaseFirestore.getInstance().collection("users")
            .add(user)
            .addOnSuccessListener {
                Log.d("Viewmodel", "Creado ${it.id}")
            }
            .addOnFailureListener{
                Log.d("Viewmodel", "Ocurrio un error ${it}")
            }
    }
     fun getUserName(): String? {
        val username = auth.currentUser?.displayName
         return username
    }
    //LOGIN WITH GOOGLE
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