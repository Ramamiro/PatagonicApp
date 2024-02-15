package com.example.patagonicapp.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.patagonicapp.models.Client
import com.example.roompractice.viewmodels.DataViewModel

class PickerViewModel(dataViewModel: DataViewModel): ViewModel() {

    var selectedClient = mutableStateOf<Client?>(null)
        private set

    var selectedClientId = mutableStateOf<Long?>(null)
        private set

    var selectedProductId = mutableStateOf<Long?>(null)
        private set

    var selectedLocationId = mutableStateOf<Long?>(null)
        private set

    var selectedProductQuantity = mutableStateOf<String?>(null)

    fun clear(){
        selectedClientId.value = null
        selectedProductId.value = null
        selectedLocationId.value = null
        selectedProductQuantity.value = null
    }

    fun selectClient(id: Long){
        selectedClientId.value = id
    }

    fun selectProduct(id: Long){
        selectedProductId.value = id
    }

    fun selectLocation(id: Long){
        selectedLocationId.value = id
    }
}
