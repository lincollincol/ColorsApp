package linc.com.colorsapp.di.modules.features.presentation

import dagger.Binds
import dagger.Module
import linc.com.colorsapp.di.scopes.FragmentScope
import linc.com.colorsapp.ui.presenters.implementation.ColorsPresenterImpl
import linc.com.colorsapp.ui.presenters.api.ColorsPresenter
import linc.com.colorsapp.ui.presenters.api.NewColorPresenter
import linc.com.colorsapp.ui.presenters.implementation.NewColorPresenterImpl

@Module
interface NewColorPresentationModule {

    @FragmentScope
    @Binds
    fun bindNewColorPresenter(newColorPresenterImpl: NewColorPresenterImpl) : NewColorPresenter

}