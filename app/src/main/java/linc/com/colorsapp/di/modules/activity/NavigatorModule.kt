package linc.com.colorsapp.di.modules.activity

import dagger.Binds
import dagger.Module
import linc.com.colorsapp.di.scopes.ActivityScope
import linc.com.colorsapp.utils.ScreenNavigator

@Module
abstract class NavigatorModule {

    @ActivityScope
    @Binds
    abstract fun bindScreenNavigator(navigator: ScreenNavigator.Implementation): ScreenNavigator.Api

}