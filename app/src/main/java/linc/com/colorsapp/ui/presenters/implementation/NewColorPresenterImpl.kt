package linc.com.colorsapp.ui.presenters.implementation

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.domain.newcolor.NewColorInteractor
import linc.com.colorsapp.ui.views.NewColorView
import linc.com.colorsapp.ui.presenters.api.NewColorPresenter
import javax.inject.Inject

class NewColorPresenterImpl @Inject constructor(
    private val newColorInteractor: NewColorInteractor
) : NewColorPresenter {

    private var newColorView: NewColorView? = null

    override fun bind(view: NewColorView) {
        this.newColorView = view
    }

    override fun unbind() {
        this.newColorView = null
    }

    override fun saveCustomColor(color: ColorModel) {
        newColorInteractor.saveCustomColor(color)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                newColorView?.close()
            }, {
                newColorView?.showError(it.localizedMessage)
            })
    }

    override fun closeWithoutSaving() {
        newColorView?.close()
    }

}