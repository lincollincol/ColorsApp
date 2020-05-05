package linc.com.colorsapp.data.utilsapi

import linc.com.colorsapp.domain.ColorModel

interface WebPageParser {
    fun parseHtmlResponse(serverResponse: String): List<ColorModel>
}