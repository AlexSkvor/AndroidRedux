package voodoo.rocks.androidfluxsample.ui

import java.io.Serializable

data class ScreenData(
    val number: Int,
    val previousNumbers: List<Int>
) : Serializable