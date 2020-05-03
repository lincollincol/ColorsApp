package linc.com.colorsapp.ui.onwcolors

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
                view?.showColors(it.toMutableList(), mutableListOf<Int>()
                    .apply {
                        for(i in 0..it.size) {
                            add(Random.nextInt(400, 700))
                        }
                    })
            }, {
                view?.showError(it.localizedMessage)
            })
    }

    fun createColor() {
        view?.openEditor(ColorModel())
    }

    fun deleteColors(colors: List<ColorModel>) {
        this.ownColorsInteractor.deleteColors(colors)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                colors.forEach {
                    view?.deleteColor(it)
                }
            }
    }

    fun editItem(color: ColorModel) {
        view?.openEditor(color)
    }

}