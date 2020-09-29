package voodoo.rocks.flux.interfaces

interface UserNotifier {
    fun notify(text: String, toast: Boolean = false)
}