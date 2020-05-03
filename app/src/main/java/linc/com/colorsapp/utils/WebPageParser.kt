package linc.com.colorsapp.utils

import linc.com.colorsapp.domain.ColorModel
import java.util.regex.Matcher
import java.util.regex.Pattern

class WebPageParser {

    fun parseHtmlResponse(serverResponse: String): List<ColorModel> {
        val colors = mutableListOf<ColorModel>()

        val extractColorsPattern: Pattern = Pattern.compile("<li><code.+?(?=</li>)")
        val extractFromTagsPattern: Pattern = Pattern.compile("[^>]+(?=</code>)")

        val colorsMatcher: Matcher = extractColorsPattern.matcher(serverResponse)
        var tagsMatcher: Matcher

        var counter = 0
        var color = ColorModel()

        // todo optimize
        while (colorsMatcher.find()) {
            // Clear tags
            tagsMatcher = extractFromTagsPattern.matcher(colorsMatcher.group())
            tagsMatcher.find()

            when (counter) {
                0 -> {
                    color = ColorModel()
                    color.title = tagsMatcher.group().toUpperCase()
                }
                1 -> color.hex = tagsMatcher.group()
                2 -> {
                    color.rgb = tagsMatcher.group()
                    colors += color
                }
            }

            if (counter == 2) counter = 0 else counter++
        }

        return colors
    }

}