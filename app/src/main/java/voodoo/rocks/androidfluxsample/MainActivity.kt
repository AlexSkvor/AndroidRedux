package voodoo.rocks.androidfluxsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.Command
import voodoo.rocks.androidfluxsample.di.DependencyProvider
import voodoo.rocks.androidfluxsample.navigation.BackToImplementedNavigator
import voodoo.rocks.androidfluxsample.navigation.Screens
import voodoo.rocks.androidfluxsample.ui.ScreenData
import voodoo.rocks.flux.interfaces.BackPressedHolder
import voodoo.rocks.flux.interfaces.UserNotifier
import voodoo.rocks.flux.interfaces.ViewModelFactoryProvider
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ViewModelFactoryProvider, UserNotifier {

    private val currentFragment: BackPressedHolder?
        get() = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as? BackPressedHolder

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DependencyProvider.get().component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null)
            router.backTo(defaultScreen)
    }

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: Router

    private val defaultScreen: SupportAppScreen
        get() = Screens.NumberScreen(ScreenData(1, emptyList()))

    private val navigator: Navigator =
        object : BackToImplementedNavigator(this, supportFragmentManager, R.id.fragmentContainer) {
            override fun setupFragmentTransaction(
                command: Command,
                currentFragment: Fragment?,
                nextFragment: Fragment?,
                fragmentTransaction: FragmentTransaction
            ) {
                fragmentTransaction.setReorderingAllowed(true)
            }
        }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val viewModelFactoryProvider: ViewModelProvider.Factory
        get() = viewModelFactory

    override fun notify(text: String, toast: Boolean) {
        if (toast) Toast.makeText(this, text, Toast.LENGTH_LONG).show()
        else Snackbar.make(mainActivityContainer, text, Snackbar.LENGTH_LONG).show()
    }
}
