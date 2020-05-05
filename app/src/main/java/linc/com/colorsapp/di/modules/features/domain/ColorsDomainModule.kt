package linc.com.colorsapp.di.modules.features.domain

import dagger.Binds
import dagger.Module
import linc.com.colorsapp.di.scopes.FragmentScope
import linc.com.colorsapp.domain.colors.ColorsInteractor
import linc.com.colorsapp.domain.colors.ColorsInteractorImpl

@Module
interface ColorsDomainModule {

    @FragmentScope
    @Binds
    fun bindColorsInteractor(colorsInteractorImpl: ColorsInteractorImpl) : ColorsInteractor

}