package voodoo.rocks.androidfluxsample.navigation

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen
import voodoo.rocks.androidfluxsample.ui.NumberFragment
import voodoo.rocks.androidfluxsample.ui.ScreenData

object Screens {

    data class NumberScreen(val data: ScreenData) : SupportAppScreen() {
        override fun getFragment(): Fragment? = NumberFragment.newInstance(data)

        override fun getScreenKey(): String = super.getScreenKey() + data.toString()
    }

}