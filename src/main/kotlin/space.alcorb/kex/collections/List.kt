package space.alcorb.kex.collections

// Toogle item in list
fun <T> MutableList<T>.toggle(item: T) {

    if(contains(item)) {
        remove(item)
    }
    else {
        add(item)
    }
}

// Find first element with index
inline fun <T> List<T>.findFirstIndexed(predicate: (T) -> Boolean): Pair<Int, T>? {

    for ((index, item) in this.withIndex()) {
        if (predicate(item)) {
            return index to item
        }
    }
    return null
}