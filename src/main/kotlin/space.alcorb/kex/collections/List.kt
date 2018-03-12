package space.alcorb.kex.collections

// Toogle item in list
fun <T> MutableList<T>.toggle(item: T) {

    if (contains(item)) {
        remove(item)
    } else {
        add(item)
    }
}

// Find first element with index
inline fun <T> Iterable<T>.findFirstIndexed(predicate: (T) -> Boolean): Pair<Int, T>? {

    for ((index, item) in this.withIndex()) {
        if (predicate(item)) {
            return index to item
        }
    }
    return null
}

fun <T> T.addTo(list: MutableList<T>) {
    list.add(this)
}

// Check is list contains element
inline fun <T> Iterable<T>.contains(predicate: (T) -> Boolean) = firstOrNull(predicate) != null