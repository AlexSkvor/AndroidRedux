package voodoo.rocks.androidfluxsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import voodoo.rocks.flux.interfaces.BackPressedHolder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}