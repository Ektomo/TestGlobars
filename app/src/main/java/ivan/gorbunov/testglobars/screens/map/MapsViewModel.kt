package ivan.gorbunov.testglobars.screens.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ivan.gorbunov.testglobars.model.retrofit.GlobarApi
import ivan.gorbunov.testglobars.model.retrofit.data.Session
import ivan.gorbunov.testglobars.model.retrofit.data.UnitTest
import kotlinx.coroutines.launch

class MapsViewModel(token: String) : ViewModel() {
    private var _sessionId = MutableLiveData<String>()
    val sessionId: LiveData<String>
        get() = _sessionId
    private val _units = MutableLiveData<List<UnitTest>>()
    val units: LiveData<List<UnitTest>>
        get() = _units

    private val _isSuccessSession = MutableLiveData<Boolean>()
    val isSuccessSession: LiveData<Boolean>
        get() = _isSuccessSession


    init {
        getSession(token)
    }

    private fun getSession(token: String) {
        viewModelScope.launch {
            try {
                val session = GlobarApi.retrofitService.getSession("Bearer $token")
                val id = session.data[0].id
                getUnits(id)
                Log.d("HOP", "LALA + $session")
            } catch (e: Exception) {
                Log.d("HOP", "ZOPA + $e")
            }
        }
    }

    private fun getUnits(id: String){
        viewModelScope.launch {
            try{
//                _units.value = GlobarApi.retrofitService.getUnits(id)
                Log.d("HOP", "URA")
            }catch (e: Exception){
                Log.d("HOP", "$e")
            }
        }
    }
}