package ie.wit.skytrace.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import ie.wit.skytrace.MainActivity
import ie.wit.skytrace.R

class AccountFragment : Fragment() {

    private lateinit var accountViewModel: AccountViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage: FirebaseStorage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)

        val displayNameTextView: TextView = view.findViewById(R.id.display_name_value)
        val emailTextView: TextView = view.findViewById(R.id.email_value)
        val phoneTextView: TextView = view.findViewById(R.id.phone_value)
        val addressTextView: TextView = view.findViewById(R.id.address_value)
        val profileImageView: ImageView = view.findViewById(R.id.profile_picture)

        val currentUser = auth.currentUser
        if (currentUser != null) {
            firestore.collection("users").document(currentUser.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        displayNameTextView.text = document.getString("displayName") ?: "Name not available"
                        emailTextView.text = document.getString("email") ?: "Email not available"
                        phoneTextView.text = document.getString("phone") ?: "Phone not available"
                        addressTextView.text = document.getString("address") ?: "Address not available"

                        val profilePictureRef = storage.reference.child("profile_pictures/${currentUser.uid}.jpg")
                        Glide.with(this)
                            .load(profilePictureRef)
                            .placeholder(R.drawable.default_profile_picture)
                            .error(R.drawable.default_profile_picture)
                            .into(profileImageView)
                    } else {
                        displayNameTextView.text = "Name not available"
                        emailTextView.text = "Email not available"
                        phoneTextView.text = "Phone not available"
                        addressTextView.text = "Address not available"
                    }
                }
                .addOnFailureListener { exception ->
                    displayNameTextView.text = "Name not available"
                    emailTextView.text = "Email not available"
                    phoneTextView.text = "Phone not available"
                    addressTextView.text = "Address not available"
                }
        } else {
            displayNameTextView.text = "Name not available"
            emailTextView.text = "Email not available"
            phoneTextView.text = "Phone not available"
            addressTextView.text = "Address not available"
        }

        val logoutButton: Button = view.findViewById(R.id.logout_button)
        logoutButton.setOnClickListener {
            auth.signOut()
            navigateToAuthScreen()
        }
    }

    private fun navigateToAuthScreen() {
        val mainActivity = requireActivity() as MainActivity
        mainActivity.navigateToSignIn()
    }
}