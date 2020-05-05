package linc.com.colorsapp.di.modules.features.domain

import dagger.Binds
import dagger.Module
import linc.com.colorsapp.di.scopes.FragmentScope
import linc.com.colorsapp.domain.colors.ColorsInteractor
import linc.com.colorsapp.domain.colors.ColorsInteractorImpl
import linc.com.colorsapp.domain.owncolors.OwnColorsInteractor
import linc.com.colorsapp.domain.owncolors.OwnColorsInteractorImpl

@Module
interface OwnColorsDomainModule {

    @FragmentScope
    @Binds
    fun bindOwnColorsInteractor(ownColorsInteractorImpl: OwnColorsInteractorImpl) : OwnColorsInteractor

}