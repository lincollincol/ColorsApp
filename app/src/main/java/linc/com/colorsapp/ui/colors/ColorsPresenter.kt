package linc.com.colorsapp.ui.colors

import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import linc.com.colorsapp.domain.ColorsInteractor
import kotlin.random.Random

class ColorsPresenter(private val colorsInteractor: ColorsInteractor) {

    private var view: ColorsView? = null

    fun bind(view: ColorsView) {
        this.view = view
    }

    fun unbind() {
        this.view = null
    }

    fun getColors() {
        this.colorsInteractor.execute()
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
                Log.d("ERROR", it.localizedMessage)
            })
    }

}