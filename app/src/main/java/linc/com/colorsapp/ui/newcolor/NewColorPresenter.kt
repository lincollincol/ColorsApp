package linc.com.colorsapp.ui.newcolor

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.domain.newcolor.NewColorInteractor

class NewColorPresenter(
    private val newColorInteractor: NewColorInteractor
) {

    private var newColorView: NewColorView? = null

    fun bind(newColorView: NewColorView) {
        this.newColorView = newColorView
    }

    fun unbind() {
        this.newColorView = null
    }

    fun saveCustomColor(color: ColorModel) {
        newColorInteractor.saveCustomColor(color)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                newColorView?.close()
            }, {
                newColorView?.showError(it.localizedMessage)
            })
    }

    fun closeWitoutSaving() {
        newColorView?.close()
    }

}