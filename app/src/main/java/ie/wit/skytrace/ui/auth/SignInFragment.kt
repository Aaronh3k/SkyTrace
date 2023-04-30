package ie.wit.skytrace.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import ie.wit.skytrace.R
import ie.wit.skytrace.ui.map.MapsFragment

class SignInFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailEditText: EditText = view.findViewById(R.id.email_edit_text)
        val passwordEditText: EditText = view.findViewById(R.id.password_edit_text)
        val signInButton: Button = view.findViewById(R.id.sign_in_button)
        val signUpTextView: TextView = view.findViewById(R.id.sign_up_text_view)

        signInButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isNotBlank() && password.isNotBlank()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            navigateToMaps()
                        } else {
                            Toast.makeText(requireContext(), "Authentication failed.", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(requireContext(), "Please enter email and password.", Toast.LENGTH_SHORT).show()
            }
        }

        signUpTextView.setOnClickListener {
            navigateToSignUp()
        }
    }

    private fun navigateToMaps() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MapsFragment())
            .commitAllowingStateLoss()
    }

    private fun navigateToSignUp() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, SignUpFragment())
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }
}