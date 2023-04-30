package ie.wit.skytrace.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import ie.wit.skytrace.R

class AccountFragment : Fragment() {

    private lateinit var accountViewModel: AccountViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)

        val displayNameTextView: TextView = view.findViewById(R.id.display_name_value)
        val emailTextView: TextView = view.findViewById(R.id.email_value)

        val currentUser = auth.currentUser
        if (currentUser != null) {
            firestore.collection("users").document(currentUser.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        displayNameTextView.text = document.getString("displayName") ?: "Name not available"
                        emailTextView.text = document.getString("email") ?: "Email not available"
                    } else {
                        displayNameTextView.text = "Name not available"
                        emailTextView.text = "Email not available"
                    }
                }
                .addOnFailureListener { exception ->
                    displayNameTextView.text = "Name not available"
                    emailTextView.text = "Email not available"
                }
        } else {
            displayNameTextView.text = "Name not available"
            emailTextView.text = "Email not available"
        }
    }
}