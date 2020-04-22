package linc.com.colorsapp.domain.colors

import android.graphics.Color
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.domain.ColorsRepository

class ColorsInteractorImpl(
    private val colorsRepository: ColorsRepository
) : ColorsInteractor {

    override fun execute(): Single<List<ColorModel>> {
        return colorsRepository.getAllColors()
    }

    override fun saveColors(colors: List<ColorModel>): Completable {
        return colorsRepository.saveColors(
            colors.map {
                it.saved = true
                return@map it
            }
        )
    }

}