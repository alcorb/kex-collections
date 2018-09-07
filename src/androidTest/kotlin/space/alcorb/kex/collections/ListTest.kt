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

    @Test
    fun testAddTo() {
        val orig = mutableListOf("apple")
        "banana".addTo(orig)
        assert(orig.contains("banana"))
    }

    @Test
    fun testContains() {
        val orig = mutableListOf("apple", "orange")
        assert(orig.contains { it.contains("app") })
        assert(orig.contains { it == "apple" })
        assert(!orig.contains { it.length == 7 })
    }
}