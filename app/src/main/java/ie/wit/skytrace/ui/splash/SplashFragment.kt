package ie.wit.skytrace.ui.splash

import ie.wit.skytrace.MainActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.fragment.app.Fragment
import ie.wit.skytrace.R

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView: TextView = view.findViewById(R.id.skytraceText)
        val anim: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.text_scale_animation)
        textView.startAnimation(anim)

        Handler(Looper.getMainLooper()).postDelayed({
            navigateToSignIn()
        }, 3000)
    }

    private fun navigateToSignIn() {
        (activity as MainActivity).navigateToSignIn()
    }
}