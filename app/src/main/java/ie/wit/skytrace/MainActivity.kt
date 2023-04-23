package ie.wit.skytrace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ie.wit.skytrace.ui.map.MapsFragment
import ie.wit.skytrace.ui.splash.SplashFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, SplashFragment())
                .commit()
        }
    }

    fun navigateToMaps() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MapsFragment())
            .commit()
    }
}
