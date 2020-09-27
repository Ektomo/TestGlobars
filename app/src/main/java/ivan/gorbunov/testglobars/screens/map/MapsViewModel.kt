package ivan.gorbunov.testglobars.screens.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ivan.gorbunov.testglobars.model.retrofit.GlobarApi
import ivan.gorbunov.testglobars.model.retrofit.data.UnitTest
import kotlinx.coroutines.launch

class MapsViewModel(token: String) : ViewModel() {
    private val _units = MutableLiveData<List<UnitTest>>()
    val units: LiveData<List<UnitTest>>
        get() = _units


    init {
        getSession(token)
    }

    private fun getSession(token: String) {
        viewModelScope.launch {
            try {
                val session = GlobarApi.retrofitService.getSession(token)
                val id = session.data[0].id
                getUnits(token, id)
            } catch (e: Exception) {
                Log.d("Failure", "$e")
            }
        }
    }

    private fun getUnits(token: String, id: String) {
        viewModelScope.launch {
            try {
                val unitsFromTest = GlobarApi.retrofitService.getUnits(token, id)
                _units.value = unitsFromTest.data
            } catch (e: Exception) {
                Log.d("Failure", "$e")
            }
        }
    }
}