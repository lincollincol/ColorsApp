package linc.com.colorsapp.domain.colors

import io.reactivex.rxjava3.core.Single
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.domain.ColorsRepository

class ColorsInteractorImpl(
    private val colorsRepository: ColorsRepository
) : ColorsInteractor {

    override fun execute(): Single<List<ColorModel>> {
        return colorsRepository.getAllColors()
    }

}