package linc.com.colorsapp.utils

import android.os.Parcel

inline fun <T> T.isNull(func: ()-> T): T {
    return this ?: func()
}

fun <E> MutableList<E>.updateAll(newList: List<E>) {
    this.clear()
    this.addAll(newList)
}

fun Parcel.writeBoolean(flag: Boolean?) {
    when(flag) {
        true -> writeInt(1)
        false -> writeInt(0)
        else -> writeInt(-1)
    }
}

fun Parcel.readBoolean(): Boolean? {
    return when(readInt()) {
        1 -> true
        0 -> false
        else -> null
    }
}