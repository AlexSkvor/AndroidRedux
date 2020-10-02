package voodoo.rocks.androidfluxsample.ui

import ru.terrakok.cicerone.Router
import voodoo.rocks.androidfluxsample.navigation.Screens
import voodoo.rocks.flux.Machine
import javax.inject.Inject
import voodoo.rocks.androidfluxsample.ui.NumberMachine.Wish
import voodoo.rocks.androidfluxsample.ui.NumberMachine.State
import voodoo.rocks.androidfluxsample.ui.NumberMachine.Result

class NumberMachine @Inject constructor(
    private val router: Router
) : Machine<Wish, Result, State>() {

    sealed class Wish {
        data class InitialData(val data: ScreenData) : Wish()
        data class BackTo(val index: Int) : Wish()

        object NavigateMinus : Wish()
        object NavigateSame : Wish()
        object NavigatePlus : Wish()
    }

    object Result

    data class State(
        val screenData: ScreenData? = null
    )

    override val initialState: State = State()

    override fun onWish(wish: Wish, oldState: State) = when (wish) {
        is Wish.InitialData -> State(wish.data)
        is Wish.BackTo -> oldState.also {
            val old = requireNotNull(it.screenData)
            backTo(wish.index, old)
        }
        Wish.NavigateMinus -> oldState.also {
            val old = requireNotNull(it.screenData)
            navigateTo(old.number - 1, old)
        }
        Wish.NavigateSame -> oldState.also {
            val old = requireNotNull(it.screenData)
            navigateTo(old.number, old)
        }
        Wish.NavigatePlus -> oldState.also {
            val old = requireNotNull(it.screenData)
            navigateTo(old.number + 1, old)
        }
    }

    override fun onResult(res: Result, oldState: State): State = oldState

    private fun navigateTo(next: Int, old: ScreenData) {
        val newData = ScreenData(next, old.previousNumbers + old.number)
        router.navigateTo(Screens.NumberScreen(newData))
    }

    override fun onBackPressed() {
        if (!states().value.screenData?.previousNumbers.isNullOrEmpty())
            router.exit()
    }

    private fun backTo(toIndex: Int, currentData: ScreenData) {
        if (toIndex > 0 && toIndex < currentData.previousNumbers.size) {
            val requiredData = ScreenData(
                number = currentData.previousNumbers[toIndex],
                previousNumbers = currentData.previousNumbers.subList(0, toIndex)
            )
            router.backTo(Screens.NumberScreen(requiredData))
        } else onBackPressed()
    }

}