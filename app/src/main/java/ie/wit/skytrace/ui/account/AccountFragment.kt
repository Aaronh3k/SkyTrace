package ie.wit.skytrace.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import ie.wit.skytrace.MainActivity
import ie.wit.skytrace.R

class AccountFragment : Fragment() {

    private lateinit var accountViewModel: AccountViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage: FirebaseStorage
    private lateinit var getContent: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let { uri ->
                val currentUser = auth.currentUser
                if (currentUser != null) {
                    val profilePictureRef = storage.reference.child("profile_pictures/${currentUser.uid}.jpg")
                    val uploadTask = profilePictureRef.putFile(uri)
                    uploadTask.addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot ->
                        Toast.makeText(requireContext(), "Profile picture uploaded successfully.", Toast.LENGTH_SHORT).show()
                        updateProfilePictureUI(profilePictureRef)
                    }
                        .addOnFailureListener { exception ->
                            Toast.makeText(requireContext(), "Error uploading profile picture: ${exception.message}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(requireContext(), "User not logged in.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

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

        val displayNameEditText: TextInputEditText = view.findViewById(R.id.display_name_value)
        val emailTextView: TextView = view.findViewById(R.id.email_value)
        val phoneEditText: TextInputEditText = view.findViewById(R.id.phone_value)
        val addressEditText: TextInputEditText = view.findViewById(R.id.address_value)
        val profileImageView: ImageView = view.findViewById(R.id.profile_picture)
        val editProfilePictureButton: ImageButton = view.findViewById(R.id.edit_profile_picture_button)
        val updateButton: Button = view.findViewById(R.id.edit_account_button)
        val logoutButton: Button = view.findViewById(R.id.logout_button)

        val currentUser = auth.currentUser
        if (currentUser != null) {
            firestore.collection("users").document(currentUser.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        displayNameEditText.setText(document.getString("displayName") ?: "Name not available")
                        emailTextView.text = document.getString("email") ?: "Email not available"
                        phoneEditText.setText(document.getString("phone") ?: "Phone not available")
                        addressEditText.setText(document.getString("address") ?: "Address not available")

                        val profilePictureRef = storage.reference.child("profile_pictures/${currentUser.uid}.jpg")
                        Glide.with(this)
                            .load(profilePictureRef)
                            .placeholder(R.drawable.default_profile_picture)
                            .error(R.drawable.default_profile_picture)
                            .into(profileImageView)
                    } else {
                        displayNameEditText.setText("Name not available")
                        emailTextView.text = "Email not available"
                        phoneEditText.setText("Phone not available")
                        addressEditText.setText("Address not available")
                    }
                }
                .addOnFailureListener { exception ->
                    displayNameEditText.setText("Name not available")
                    emailTextView.text = "Email not available"
                    phoneEditText.setText("Phone not available")
                    addressEditText.setText("Address not available")
                }
        } else {
            displayNameEditText.setText("Name not available")
            emailTextView.text = "Email not available"
            phoneEditText.setText("Phone not available")
            addressEditText.setText("Address not available")
        }

        updateButton.setOnClickListener {
            updateUserInformation(displayNameEditText, phoneEditText, addressEditText)
        }

        logoutButton.setOnClickListener {
            auth.signOut()
            navigateToAuthScreen()
        }

        editProfilePictureButton.setOnClickListener {
            getContent.launch("image/*")
        }
    }

    private fun updateUserInformation(displayNameEditText: TextInputEditText, phoneEditText: TextInputEditText, addressEditText: TextInputEditText) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val displayName = displayNameEditText.text.toString()
            val phone = phoneEditText.text.toString()
            val address = addressEditText.text.toString()

            val userUpdates = hashMapOf(
                "displayName" to displayName,
                "phone" to phone,
                "address" to address
            )

            firestore.collection("users").document(currentUser.uid)
                .update(userUpdates as Map<String, Any>)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "User information updated successfully.", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(requireContext(), "Error updating user information: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(requireContext(), "User not logged in.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateProfilePictureUI(profilePictureRef: StorageReference) {
        val profileImageView: ImageView = requireView().findViewById(R.id.profile_picture)

        Glide.with(this)
            .load(profilePictureRef)
            .placeholder(R.drawable.default_profile_picture)
            .error(R.drawable.default_profile_picture)
            .into(profileImageView)
    }

    private fun navigateToAuthScreen() {
        val mainActivity = requireActivity() as MainActivity
        mainActivity.navigateToSignIn()
    }
}