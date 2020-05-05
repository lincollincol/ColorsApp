package linc.com.colorsapp.di.modules.features.presentation

import dagger.Binds
import dagger.Module
import linc.com.colorsapp.di.scopes.FragmentScope
import linc.com.colorsapp.ui.presenters.implementation.ColorsPresenterImpl
import linc.com.colorsapp.ui.presenters.api.ColorsPresenter

@Module
interface ColorsPresentationModule {

    @FragmentScope
    @Binds
    fun bindColorsPresenter(colorsPresenterImpl: ColorsPresenterImpl) : ColorsPresenter

}