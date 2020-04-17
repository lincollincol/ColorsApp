package linc.com.colorsapp.utils

import java.nio.charset.Charset
import java.util.*

class KeyGenerator {
    companion object {
        fun generateRandom(): String {
            val array = ByteArray(16)
            Random().nextBytes(array)
            return String(array, Charset.forName("UTF-8"))
        }
    }
}