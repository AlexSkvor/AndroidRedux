package voodoo.rocks.androidfluxsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import voodoo.rocks.flux.interfaces.BackPressedHolder

class MainActivity : AppCompatActivity() {

    private val currentFragment: BackPressedHolder? = null // TODO

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}