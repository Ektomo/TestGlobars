package ivan.gorbunov.testglobars.screens.autorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ivan.gorbunov.testglobars.model.retrofit.GlobarApi
import ivan.gorbunov.testglobars.model.retrofit.data.UserLogin
import kotlinx.coroutines.launch

class AuthorizationViewModel : ViewModel() {

    private val _token = MutableLiveData<String>()
    val token: LiveData<String>
        get() = _token

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean>
        get() = _isSuccess



    fun getToken(user: UserLogin) {
        viewModelScope.launch {
            try {
                val callToken = GlobarApi.retrofitService.getToken(user)
                _isSuccess.value = callToken.success
                _token.value = callToken.data
            } catch (e: Exception) {
                _isSuccess.value = false
                _token.value = "Failure: $e"
            }
        }
    }
}


