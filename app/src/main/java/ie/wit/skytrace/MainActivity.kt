package ie.wit.skytrace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import ie.wit.skytrace.ui.BottomNavigationFragment
import ie.wit.skytrace.ui.auth.SignInFragment
import ie.wit.skytrace.ui.splash.SplashFragment

class MainActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, SplashFragment())
                .commitAllowingStateLoss()
        }
    }

    fun navigateToSignIn() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, SignInFragment())
            .commitAllowingStateLoss()
    }

    fun navigateToBottomNavigation() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, BottomNavigationFragment())
            .commitAllowingStateLoss()
    }
}