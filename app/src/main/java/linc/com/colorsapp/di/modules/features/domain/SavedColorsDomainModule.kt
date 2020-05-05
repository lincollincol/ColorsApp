package linc.com.colorsapp.di.modules.features.domain

import dagger.Binds
import dagger.Module
import linc.com.colorsapp.di.scopes.FragmentScope
import linc.com.colorsapp.domain.colors.ColorsInteractor
import linc.com.colorsapp.domain.colors.ColorsInteractorImpl
import linc.com.colorsapp.domain.saved.SavedColorsInteractor
import linc.com.colorsapp.domain.saved.SavedColorsInteractorImpl

@Module
interface SavedColorsDomainModule {

    @FragmentScope
    @Binds
    fun bindSavedColorsInteractor(savedColorsInteractorImpl: SavedColorsInteractorImpl) : SavedColorsInteractor

}