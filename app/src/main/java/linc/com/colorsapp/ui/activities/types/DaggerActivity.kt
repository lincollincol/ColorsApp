package linc.com.colorsapp.ui.activities.types

import linc.com.colorsapp.di.components.MainActivitySubComponent

interface DaggerActivity {
    fun getActivitySubComponent(): MainActivitySubComponent
}