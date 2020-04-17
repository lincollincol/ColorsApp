package linc.com.colorsapp.domain

import io.reactivex.rxjava3.core.Single

class ColorsInteractorImpl(
    private val colorsRepository: ColorsRepository
) : ColorsInteractor {

    override fun execute(): Single<List<ColorModel>> {
        return colorsRepository.getColors()
    }

}