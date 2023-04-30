package ie.wit.skytrace.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import ie.wit.skytrace.R
import ie.wit.skytrace.ui.map.MapsFragment

class SignUpFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nameEditText: EditText = view.findViewById(R.id.name_edit_text)
        val emailEditText: EditText = view.findViewById(R.id.email_edit_text)
        val passwordEditText: EditText = view.findViewById(R.id.password_edit_text)
        val confirmPasswordEditText: EditText = view.findViewById(R.id.confirm_password_edit_text)
        val signUpButton: Button = view.findViewById(R.id.sign_up_button)

        signUpButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (name.isNotBlank() && email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()) {
                if (password == confirmPassword) {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                navigateToMaps()
                            } else {
                                Toast.makeText(requireContext(), "Registration failed.", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(requireContext(), "Passwords do not match.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Please fill in all the fields.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToMaps() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MapsFragment())
            .commitAllowingStateLoss()
    }
}