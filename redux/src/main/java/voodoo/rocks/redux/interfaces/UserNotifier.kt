package voodoo.rocks.redux.interfaces

interface UserNotifier {
    fun notify(text: String, toast: Boolean = false)
}