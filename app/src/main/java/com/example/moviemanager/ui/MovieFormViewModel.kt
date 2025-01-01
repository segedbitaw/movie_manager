package com.example.moviemanager.ui
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MovieFormViewModel : ViewModel(){
    private val _title = MutableLiveData<String>()
    val title: LiveData<String> get() = _title
    private val _description = MutableLiveData<String>()
    val description: LiveData<String> get() = _description
    private val _year = MutableLiveData<String>()
    val year: LiveData<String> get() = _year
    private val _posterUri = MutableLiveData<Uri?>()
    val posterUri: LiveData<Uri?> get() = _posterUri

    fun setTitle(value: String) {
        _title.value = value
    }

    fun setDescription(value: String) {
        _description.value = value
    }
    fun setYear(value: String) {
        _year.value = value
    }

    fun setPosterUri(uri: Uri?) {
        _posterUri.value = uri
    }

    fun clearForm() {
        _title.value = ""
        _description.value = ""
        _posterUri.value = null
    }

}