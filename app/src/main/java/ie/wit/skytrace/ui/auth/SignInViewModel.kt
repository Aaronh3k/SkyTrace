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

class SignInViewModel : ViewModel() {
    private val _signInState = MutableLiveData<SignInState>()
    val signInState: LiveData<SignInState> = _signInState

    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    fun signIn(email: String, password: String) {
        _signInState.value = SignInState.Loading

        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                _signInState.postValue(SignInState.Success)
            } catch (e: Exception) {
                _signInState.postValue(SignInState.Error(e.message))
            }
        }
    }
}

sealed class SignInState {
    object Loading : SignInState()
    object Success : SignInState()
    data class Error(val message: String?) : SignInState()
}