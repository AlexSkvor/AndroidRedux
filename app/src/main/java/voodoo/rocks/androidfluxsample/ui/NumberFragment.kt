package voodoo.rocks.androidfluxsample.ui

import kotlinx.android.synthetic.main.fragment_number.*
import voodoo.rocks.androidfluxsample.R
import voodoo.rocks.androidfluxsample.navigation.getArgument
import voodoo.rocks.androidfluxsample.navigation.withArguments
import voodoo.rocks.flux.FluxFragment
import voodoo.rocks.androidfluxsample.ui.NumberMachine.Wish
import voodoo.rocks.androidfluxsample.ui.NumberMachine.State

class NumberFragment : FluxFragment<Wish, State>() {

    companion object {
        fun newInstance(data: ScreenData) = NumberFragment()
            .withArguments(DATA_KEY to data)

        private const val DATA_KEY = "NumberFragment_DATA_KEY"
    }

    override val machine by lazy { getMachineFromFactory(NumberMachine::class.java) }

    override fun layoutRes(): Int = R.layout.fragment_number

    private val screenData: ScreenData by lazy { getArgument(DATA_KEY) }

    private val adapter by lazy { numberAdapter { index -> performWish(Wish.BackTo(index)) } }

    override fun onScreenInit() {
        recycler.adapter = adapter
        performWish(Wish.InitialData(screenData))

        forwardNumber1.setOnClickListener { performWish(Wish.NavigateMinus) }
        forwardNumber2.setOnClickListener { performWish(Wish.NavigateSame) }
        forwardNumber3.setOnClickListener { performWish(Wish.NavigatePlus) }
    }

    override fun render(state: State) {
        if (state.screenData == null) return

        currentValue.text = state.screenData.number.toString()
        forwardNumber1.text = (state.screenData.number - 1).toString()
        forwardNumber2.text = state.screenData.number.toString()
        forwardNumber3.text = (state.screenData.number + 1).toString()
        adapter.items = state.screenData.previousNumbers
    }
}