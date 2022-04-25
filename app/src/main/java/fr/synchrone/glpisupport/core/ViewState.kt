package fr.synchrone.glpisupport.core

sealed class ViewState<out T> {
    data class Success<out T>(val data: T? = null): ViewState<T>()
    data class Loading(val nothing: Nothing? = null): ViewState<Nothing>()
    data class Exception(val exception: kotlin.Exception? = null): ViewState<Nothing>()
}