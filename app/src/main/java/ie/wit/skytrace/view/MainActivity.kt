package ie.wit.skytrace.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Navigate to the SplashActivity
        startActivity(Intent(this, SplashActivity::class.java))
        finish()
    }
}
