package linc.com.colorsapp.utils

import android.graphics.Color
import androidx.annotation.ColorInt
import linc.com.colorsapp.utils.Constants.Companion.FORMAT_HEX
import linc.com.colorsapp.utils.Constants.Companion.FORMAT_RGB

class ColorUtil {
    companion object {
        fun getReadableColor(color: Int): Int {
            val luminance = (
                    0.2126 * Color.red(color)
                    + 0.7152 * Color.green(color)
                    + 0.0722 * Color.blue(color))
            return if(luminance < 140) Color.WHITE else Color.BLACK
        }

        fun getHexFromInt(@ColorInt color: Int) = String.format(
            FORMAT_HEX,
            Color.WHITE and color
        )

        fun getRgbFromInt(@ColorInt color: Int) = String.format(
            FORMAT_RGB,
            Color.red(color),
            Color.green(color),
            Color.blue(color)
        )

    }
}