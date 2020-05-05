package linc.com.colorsapp.ui.presenters.implementation

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.domain.owncolors.OwnColorsInteractor
import linc.com.colorsapp.ui.views.OwnColorsView
import linc.com.colorsapp.ui.presenters.api.OwnColorsPresenter
import javax.inject.Inject
import kotlin.random.Random

class OwnColorsPresenterImpl @Inject constructor(
    private val ownColorsInteractor: OwnColorsInteractor
) : OwnColorsPresenter {

    private var view: OwnColorsView? = null

    override fun bind(view: OwnColorsView) {
        this.view = view
    }

    override fun unbind() {
        this.view = null
    }

    override fun getColors() {
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

    override fun createColor() {
        view?.openEditor(ColorModel())
    }

    override fun deleteColors(colors: List<ColorModel>) {
        this.ownColorsInteractor.deleteColors(colors)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                colors.forEach {
                    view?.deleteColor(it)
                }
            }
    }

    override fun editItem(color: ColorModel) {
        view?.openEditor(color)
    }

}