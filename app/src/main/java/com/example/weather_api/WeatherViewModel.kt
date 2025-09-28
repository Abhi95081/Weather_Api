package com.example.weather_api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_api.api.Constant
import com.example.weather_api.api.NetworkResponse
import com.example.weather_api.api.RetrofitInstance
import com.example.weather_api.api.WeatherModel
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel(){


    private val weatherApi = RetrofitInstance.weatherApi
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult : LiveData<NetworkResponse<WeatherModel>> = _weatherResult

    fun getData(city : String){
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val responce =  weatherApi.getWeather(Constant.apikey,city)

                if(responce.isSuccessful){
                    responce.body()?.let {
                        _weatherResult.value = NetworkResponse.Success(it)
                    }

                }else{
                    _weatherResult.value = NetworkResponse.Error("Failed to load")
                }
            }
            catch (e : Exception){
                _weatherResult.value = NetworkResponse.Error("Failed to load")
            }
        }
    }
}