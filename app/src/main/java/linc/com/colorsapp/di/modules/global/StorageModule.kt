package linc.com.colorsapp.di.modules.global

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import linc.com.colorsapp.data.room.ColorsRoomDatabase
import linc.com.colorsapp.utils.Constants
import javax.inject.Singleton

@Module
class StorageModule {

    @Singleton
    @Provides
    fun provideColorsDao(colorsRoomDatabase: ColorsRoomDatabase) = colorsRoomDatabase.colorsDao()

    @Singleton
    @Provides
    fun provideRoomDatabase(context: Context) = Room.databaseBuilder(
        context,
        ColorsRoomDatabase::class.java,
        Constants.DATABASE_NAME
    ).build()

}