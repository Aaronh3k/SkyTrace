package ie.wit.skytrace.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AccountViewModel : ViewModel() {

    private val _firebaseUser: MutableLiveData<FirebaseUser?> = MutableLiveData()
    val firebaseUser: MutableLiveData<FirebaseUser?> = _firebaseUser

    init {
        fetchFirebaseUser()
    }

    private fun fetchFirebaseUser() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            _firebaseUser.value = currentUser
        }
    }
}