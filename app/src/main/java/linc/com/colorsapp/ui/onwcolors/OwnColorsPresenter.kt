package linc.com.colorsapp.ui.onwcolors

import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.domain.owncolors.OwnColorsInteractor
import kotlin.random.Random

class OwnColorsPresenter (private val ownColorsInteractor: OwnColorsInteractor) {

    private var view: OwnColorsView? = null

    fun bind(view: OwnColorsView) {
        this.view = view
    }

    fun unbind() {
        this.view = null
    }

    fun getColors() {
        this.ownColorsInteractor.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.showColors(it, mutableListOf<Int>()
                    .apply {
                        for(i in 0..it.size) {
                            add(Random.nextInt(400, 700))
                        }
                    })
            }, {
                view?.showError(it.localizedMessage)
            })
    }

    fun saveColor(color: ColorModel) {
        this.ownColorsInteractor
            .saveColor(color)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.showNewColor(color, Random.nextInt(400, 700))
            }, {
                view?.showError(it.localizedMessage)
            })
    }

}