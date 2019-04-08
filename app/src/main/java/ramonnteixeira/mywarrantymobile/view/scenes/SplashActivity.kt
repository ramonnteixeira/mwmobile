package ramonnteixeira.mywarrantymobile.view.scenes

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash.*
import ramonnteixeira.mywarrantymobile.R
import tyrantgit.explosionfield.ExplosionField

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handler = Handler()
        val context = this

        logo.startAnimation(AnimationUtils.loadAnimation(context, R.anim.splash_in))
        handler.postDelayed(fadeIn(handler, context), 3000)
    }

    private fun fadeIn(handler: Handler, context: Context): Runnable {
        return Runnable {
            logo.startAnimation(AnimationUtils.loadAnimation(context, R.anim.splash_out))
            val explosionField = ExplosionField(context)
            explosionField.explode(logo)
            handler.postDelayed(fadeOut(context), 1500)
        }
    }

    private fun fadeOut(context: Context): Runnable {
        return Runnable {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
