package voodoo.rocks.flux

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import voodoo.rocks.flux.interfaces.BackPressedHolder
import voodoo.rocks.flux.interfaces.UserNotifier
import voodoo.rocks.flux.interfaces.ViewModelFactoryProvider
import voodoo.rocks.flux.interfaces.getImplementation

abstract class FluxFragment<W, S : Any> : Fragment(), BackPressedHolder {

    abstract val machine: Machine<W, *, S>
    abstract fun onScreenInit()
    abstract fun render(state: S)

    @LayoutRes
    abstract fun layoutRes(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(layoutRes(), container, false)

    protected fun getMachineFromFactory(klacc: Class<out Machine<W, *, S>>): Machine<W, *, S> {
        val factory = getImplementation(ViewModelFactoryProvider::class.java)
            ?.viewModelFactoryProvider ?: error("ViewModelFactoryProvider is not found")

        return ViewModelProvider(this, factory).get(screenTag, klacc)
    }

    private val screenTag by lazy { hashCode().toString() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        machine.start()
        machine.states().observe { render(it) }
        machine.messages().observe { getImplementation(UserNotifier::class.java)?.notify(it) }
        onScreenInit()
    }

    protected fun <T> LiveData<T>.observe(observer: (T) -> Unit) {
        observe(viewLifecycleOwner, { observer(it) })
    }

    protected fun performWish(wish: W) = machine.performWish(wish)

    override fun onBackPressed() = machine.onBackPressed()

}