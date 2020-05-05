package linc.com.colorsapp.utils

import linc.com.colorsapp.data.utilsapi.WebPageParser
import linc.com.colorsapp.domain.ColorModel
import linc.com.colorsapp.utils.Constants.Companion.PARSE_COLOR_HEX
import linc.com.colorsapp.utils.Constants.Companion.PARSE_COLOR_RGB
import linc.com.colorsapp.utils.Constants.Companion.PARSE_COLOR_TITLE
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject

class WebPageParserImpl @Inject constructor() :
    WebPageParser {

    override fun parseHtmlResponse(serverResponse: String): List<ColorModel> {
        val colors = mutableListOf<ColorModel>()

        val extractColorsPattern: Pattern = Pattern.compile("<li><code.+?(?=</li>)")
        val extractFromTagsPattern: Pattern = Pattern.compile("[^>]+(?=</code>)")

        val colorsMatcher: Matcher = extractColorsPattern.matcher(serverResponse)
        var tagsMatcher: Matcher

        var counter = 0
        var color = ColorModel()

        while (colorsMatcher.find()) {
            // Clear tags
            tagsMatcher = extractFromTagsPattern.matcher(colorsMatcher.group())
            tagsMatcher.find()

            when (counter) {
                PARSE_COLOR_TITLE -> {
                    color = ColorModel()
                    color.title = tagsMatcher.group().toUpperCase()
                }
                PARSE_COLOR_HEX -> color.hex = tagsMatcher.group()
                PARSE_COLOR_RGB -> {
                    color.rgb = tagsMatcher.group()
                    colors += color
                }
            }

            if (counter == 2) counter = 0 else counter++
        }

        return colors
    }

}