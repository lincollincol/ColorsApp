package linc.com.colorsapp.domain.saved

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.domain.ColorsRepository

class SavedColorsInteractorImpl(
    private val colorsRepository: ColorsRepository
) : SavedColorsInteractor {

    override fun execute(): Single<List<ColorModel>> {
        return colorsRepository.getSavedColors()
    }

    override fun deleteColors(colors: List<ColorModel>): Completable {
        return colorsRepository.deleteColors(colors)
    }

}