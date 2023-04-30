package ie.wit.skytrace.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SignUpViewModel : ViewModel() {
    private val _signUpState = MutableLiveData<SignUpState>()
    val signUpState: LiveData<SignUpState> = _signUpState

    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    fun signUp(email: String, password: String) {
        _signUpState.value = SignUpState.Loading

        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.createUserWithEmailAndPassword(email, password).await()
                _signUpState.postValue(SignUpState.Success)
            } catch (e: Exception) {
                _signUpState.postValue(SignUpState.Error(e.message))
            }
        }
    }
}

sealed class SignUpState {
    object Loading : SignUpState()
    object Success : SignUpState()
    data class Error(val message: String?) : SignUpState()
}