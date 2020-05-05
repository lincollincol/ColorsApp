package linc.com.colorsapp.di.modules.features.presentation

import dagger.Binds
import dagger.Module
import linc.com.colorsapp.di.scopes.FragmentScope
import linc.com.colorsapp.ui.presenters.api.OwnColorsPresenter
import linc.com.colorsapp.ui.presenters.api.SavedColorsPresenter
import linc.com.colorsapp.ui.presenters.implementation.OwnColorsPresenterImpl
import linc.com.colorsapp.ui.presenters.implementation.SavedColorsPresenterImpl

@Module
interface OwnColorsPresentationModule {

    @FragmentScope
    @Binds
    fun bindOwnColorsPresenter(ownColorsPresenterImpl: OwnColorsPresenterImpl) : OwnColorsPresenter

}