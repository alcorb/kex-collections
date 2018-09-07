package space.alcorb.kex.network

/**
 * Interface for network errors tracking.
 *
 * @author Yamushev Igor
 * @since  07.09.18
 */
interface NetworkErrorTracker {
    fun track(url: String?, description: String, ex: Exception)
}