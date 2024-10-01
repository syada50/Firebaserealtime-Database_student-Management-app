package com.example.firebase_realtimedatabase_studentmanagement

import android.os.Message
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class DataViewModel : ViewModel(){

    private val dataRepository = DataRepository()
    private var _dataList: MutableLiveData<List<Data>> =dataRepository.fetchData()

 val dataList: LiveData<List<Data>> = _dataList


    private val _error =MutableLiveData<String?>()
    val error:MutableLiveData<String?> get() =_error


    fun addData (data: Data,onSuccess: ()->Unit,onFailure: ()->Unit){

        dataRepository.addData(data)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener{
                _error.value = it.message
                onFailure()
            }
    }

  fun updateDAta(data: Data){
      dataRepository.updateData(data)
  }
     fun deleteDAta(data: Data,onSuccess: () -> Unit, onFailure: () -> Unit){
         dataRepository.deleteData(data)
         onSuccess()
     }

    fun handlerDatabaseError(errorMessage: String?){
        _error.value =errorMessage
    }

}