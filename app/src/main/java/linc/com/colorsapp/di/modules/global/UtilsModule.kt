package linc.com.colorsapp.di.modules.global

import dagger.Binds
import dagger.Module
import linc.com.colorsapp.data.utilsapi.ColorModelMapper
import linc.com.colorsapp.data.utilsapi.WebPageParser
import linc.com.colorsapp.utils.ColorModelMapperImpl
import linc.com.colorsapp.utils.WebPageParserImpl
import javax.inject.Singleton

@Module
abstract class UtilsModule {

    @Singleton
    @Binds
    abstract fun bindColorModelMapper(colorModelMapperImpl: ColorModelMapperImpl): ColorModelMapper

    @Singleton
    @Binds
    abstract fun bindWebPageParser(webPageParserImpl: WebPageParserImpl): WebPageParser

}