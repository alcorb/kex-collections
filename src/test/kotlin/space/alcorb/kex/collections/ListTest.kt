package space.alcorb.kex.collections

import org.junit.Test

/**
 * Author: Andrey Khitryy
 * Email: andrey.khitryy@gmail.com
 */
class ListTest {

    @Test
    fun testToggle() {
        val orig = mutableListOf("apple", "orange")

        orig.toggle("apple")
        orig.toggle("mustikka")

        assert(!orig.contains("apple"))
        assert(orig.contains("mustikka"))
    }

    @Test
    fun testFindFirstIndex() {
        val orig = mutableListOf("apple", "orange", "apple", "banana")

        // Test: found
        val found = orig.findFirstIndexed { it == "apple" }
        assert(found!!.first == 0 && found.second == "apple")

        // Test: not found
        val notFound = orig.findFirstIndexed { it == "mustikka" }
        assert(notFound == null)
    }
}