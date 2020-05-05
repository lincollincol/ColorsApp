package linc.com.colorsapp.di.modules.features.domain

import dagger.Binds
import dagger.Module
import linc.com.colorsapp.di.scopes.FragmentScope
import linc.com.colorsapp.domain.colors.ColorsInteractor
import linc.com.colorsapp.domain.colors.ColorsInteractorImpl
import linc.com.colorsapp.domain.newcolor.NewColorInteractor
import linc.com.colorsapp.domain.newcolor.NewColorInteractorImpl

@Module
interface NewColorDomainModule {

    @FragmentScope
    @Binds
    fun bindNewColorInteractor(newColorInteractorImpl: NewColorInteractorImpl) : NewColorInteractor

}