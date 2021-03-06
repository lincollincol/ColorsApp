package linc.com.colorsapp.domain.owncolors

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.domain.ColorsRepository
import javax.inject.Inject

class OwnColorsInteractorImpl @Inject constructor(
    private val colorsRepository: ColorsRepository
) : OwnColorsInteractor {

    override fun execute(): Single<List<ColorModel>> {
        return colorsRepository.getCustomColors()
    }

    override fun deleteColors(colors: List<ColorModel>): Completable {
        return colorsRepository.deleteCustomColors(colors)
    }

}