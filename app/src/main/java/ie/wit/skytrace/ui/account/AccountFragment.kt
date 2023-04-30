package ie.wit.skytrace.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ie.wit.skytrace.R

class AccountFragment : Fragment() {

    private lateinit var accountViewModel: AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)

        val displayNameTextView: TextView = view.findViewById(R.id.display_name_value)
        val emailTextView: TextView = view.findViewById(R.id.email_value)

        accountViewModel.firebaseUser.observe(viewLifecycleOwner) { firebaseUser ->
            displayNameTextView.text = firebaseUser?.displayName ?: "Name not available"
            emailTextView.text = firebaseUser?.email ?: "Email not available"
        }
    }
}