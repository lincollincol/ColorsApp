package linc.com.colorsapp.ui.presenters.api

interface BasePresenter<T> {

    fun bind(view: T)
    fun unbind()

}