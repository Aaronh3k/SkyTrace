package ie.wit.skytrace.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ie.wit.skytrace.R

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        val textAnimation = AnimationUtils.loadAnimation(this, R.anim.text_scale_animation)
        val skytraceText: TextView = findViewById(R.id.skytraceText)
        skytraceText.startAnimation(textAnimation)

        textAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                finish()
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        })
    }
}
