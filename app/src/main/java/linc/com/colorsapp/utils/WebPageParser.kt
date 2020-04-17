package linc.com.colorsapp.utils

import android.util.Log
import linc.com.colorsapp.domain.ColorModel
import java.util.regex.Matcher
import java.util.regex.Pattern

class WebPageParser {

    fun parseHtmlResponse(serverResponse: String): List<ColorModel> {
        val colors = mutableListOf<ColorModel>()

        val extractColorsPattern: Pattern = Pattern.compile("<li><code.+?(?=</li>)")
        val extractFromTagsPattern: Pattern = Pattern.compile("[^>]+(?=</code>)")
        val extractRgbPattern: Pattern = Pattern.compile("[^(]+(?=\\))")

        val colorsMatcher: Matcher = extractColorsPattern.matcher(serverResponse)
        var tagsMatcher: Matcher = extractFromTagsPattern.matcher(serverResponse)
        var rgbMatcher: Matcher = extractRgbPattern.matcher(serverResponse)

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
                    color.name = tagsMatcher.group()
                }
                1 -> color.hex = tagsMatcher.group()
                2 -> {
//                    rgbMatcher = extractRgbPattern.matcher(tagsMatcher.group())
//                    rgbMatcher.find()
                    color.rgb = tagsMatcher.group()
                    colors += color
                }
            }

            if (counter == 2) counter = 0 else counter++
        }

        colors.forEach {
            Log.d("COLOR", it.toString())
        }
        return colors
    }

}