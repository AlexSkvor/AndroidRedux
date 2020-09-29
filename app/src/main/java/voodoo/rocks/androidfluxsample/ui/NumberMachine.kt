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

    sealed class Result {
        data class InitialData(val data: ScreenData) : Result()
        data class NavigateTo(val number: Int) : Result()
        data class BackTo(val index: Int) : Result()
    }

    data class State(
        val screenData: ScreenData? = null
    )

    override val initialState: State = State()

    override fun onWish(wish: Wish, oldState: State) = when (wish) {
        is Wish.InitialData -> pushResult(Result.InitialData(wish.data))
        is Wish.BackTo -> pushResult(Result.BackTo(wish.index))
        Wish.NavigateMinus -> pushResult(Result.NavigateTo(oldState.screenData!!.number - 1))
        Wish.NavigateSame -> pushResult(Result.NavigateTo(oldState.screenData!!.number))
        Wish.NavigatePlus -> pushResult(Result.NavigateTo(oldState.screenData!!.number + 1))
    }

    override fun onResult(res: Result, oldState: State): State = when (res) {
        is Result.InitialData -> State(res.data)
        is Result.NavigateTo -> oldState.also {
            val old = requireNotNull(it.screenData)
            val newData = ScreenData(res.number, old.previousNumbers + old.number)
            router.navigateTo(Screens.NumberScreen(newData))
        }
        is Result.BackTo -> oldState.also {
            val old = requireNotNull(it.screenData)
            backTo(res.index, old)
        }
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