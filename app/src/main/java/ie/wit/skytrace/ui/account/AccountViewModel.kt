package ie.wit.skytrace.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

data class UserInfo(
    val displayName: String?,
    val email: String?,
    val phone: String?,
    val address: String?
)

class AccountViewModel : ViewModel() {

    private val _firebaseUser: MutableLiveData<FirebaseUser?> = MutableLiveData()
    val firebaseUser: LiveData<FirebaseUser?> = _firebaseUser

    private val _userInfo: MutableLiveData<UserInfo> = MutableLiveData()
    val userInfo: LiveData<UserInfo> = _userInfo

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    init {
        fetchFirebaseUser()
    }

    private fun fetchFirebaseUser() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            _firebaseUser.value = currentUser
            fetchUserInfo(currentUser.uid)
        }
    }

    private fun fetchUserInfo(uid: String) {
        firestore.collection("users").document(uid)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val displayName = document.getString("displayName")
                    val email = document.getString("email")
                    val phone = document.getString("phone")
                    val address = document.getString("address")
                    _userInfo.value = UserInfo(displayName, email, phone, address)
                }
            }
            .addOnFailureListener {
                _userInfo.value = UserInfo(null, null, null, null)
            }
    }
}