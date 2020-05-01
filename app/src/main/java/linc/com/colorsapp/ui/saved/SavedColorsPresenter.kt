package linc.com.colorsapp.ui.saved

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.domain.saved.SavedColorsInteractor
import linc.com.colorsapp.ui.onwcolors.OwnColorsView
import kotlin.random.Random

class SavedColorsPresenter(
    private val savedColorsInteractor: SavedColorsInteractor
) {

    private var view: SavedColorsView? = null

    fun bind(view: SavedColorsView) {
        this.view = view
    }

    fun unbind() {
        this.view = null
    }

    fun getColors() {
        savedColorsInteractor.execute()
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

    fun deleteColors(colors: List<ColorModel>) {
        println("DELETE COLORS 1 ${colors}")
        this.savedColorsInteractor.deleteColors(colors)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                println("DELETE COLORS ${colors}")
                colors.forEach {
                    view?.deleteColor(it)
                }
            }
    }

}