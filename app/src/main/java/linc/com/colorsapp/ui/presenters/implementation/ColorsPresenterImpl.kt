package linc.com.colorsapp.ui.presenters.implementation

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.domain.colors.ColorsInteractor
import linc.com.colorsapp.ui.views.ColorsView
import linc.com.colorsapp.ui.presenters.api.ColorsPresenter
import javax.inject.Inject
import kotlin.random.Random

class ColorsPresenterImpl @Inject constructor(
    private val colorsInteractor: ColorsInteractor,
    private val compositeDisposable: CompositeDisposable
) : ColorsPresenter {

    private var view: ColorsView? = null

    override fun bind(view: ColorsView) {
        this.view = view
    }

    override fun unbind() {
        this.view = null
        this.compositeDisposable.clear()
    }

    override fun getColors() {
        compositeDisposable.add(
            colorsInteractor.execute()
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

    override fun saveColors(colors: List<ColorModel>) {
        compositeDisposable.add(
            colorsInteractor.saveColors(colors)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

}