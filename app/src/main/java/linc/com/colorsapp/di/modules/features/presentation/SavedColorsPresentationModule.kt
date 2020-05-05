package linc.com.colorsapp.di.modules.features.presentation

import dagger.Binds
import dagger.Module
import linc.com.colorsapp.di.scopes.FragmentScope
import linc.com.colorsapp.ui.presenters.api.SavedColorsPresenter
import linc.com.colorsapp.ui.presenters.implementation.SavedColorsPresenterImpl

@Module
interface SavedColorsPresentationModule {

    @FragmentScope
    @Binds
    fun bindSavedColorsPresenter(savedColorsPresenterImpl: SavedColorsPresenterImpl) : SavedColorsPresenter

}