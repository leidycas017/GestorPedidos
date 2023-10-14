package co.edu.udea.compumovil.gr01_20232.gestorpedidos
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private var username: String = ""
    private var password: String = ""

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


}