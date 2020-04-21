package linc.com.colorsapp.utils

import android.graphics.Color

class ColorUtil {
    companion object {
        fun getReadableColor(color: Int): Int {
            val luminance = (
                    0.2126 * Color.red(color)
                    + 0.7152 * Color.green(color)
                    + 0.0722 * Color.blue(color))
            return if(luminance < 140) Color.WHITE else Color.BLACK
        }
    }
}