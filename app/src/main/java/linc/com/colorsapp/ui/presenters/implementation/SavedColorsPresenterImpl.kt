package linc.com.colorsapp.ui.presenters.implementation

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.domain.saved.SavedColorsInteractor
import linc.com.colorsapp.ui.presenters.api.SavedColorsPresenter
import linc.com.colorsapp.ui.views.SavedColorsView
import javax.inject.Inject
import kotlin.random.Random

class SavedColorsPresenterImpl @Inject constructor(
    private val savedColorsInteractor: SavedColorsInteractor,
    private val compositeDisposable: CompositeDisposable
) : SavedColorsPresenter {

    private var view: SavedColorsView? = null

    override fun bind(view: SavedColorsView) {
        this.view = view
    }

    override fun unbind() {
        this.view = null
        this.compositeDisposable.clear()
    }

    override fun getColors() {
        compositeDisposable.add(
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
        )
    }

    override fun deleteColors(colors: List<ColorModel>) {
        compositeDisposable.add(
            savedColorsInteractor.deleteColors(colors)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    colors.forEach {
                        view?.deleteColor(it)
                    }
                }
        )
    }

}