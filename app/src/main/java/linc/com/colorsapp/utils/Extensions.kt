package linc.com.colorsapp.utils

fun <E> MutableList<E>.updateAll(newList: List<E>) {
    this.clear()
    this.addAll(newList)
}