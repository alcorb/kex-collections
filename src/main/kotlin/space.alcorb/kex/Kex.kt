package space.alcorb.kex

import android.annotation.SuppressLint
import android.content.Context
import space.alcorb.kex.network.NetworkErrorTracker

/**
 * @author Yamushev Igor
 * @since  10.09.18
 */
class Kex private constructor() {
    
    lateinit var context: Context
    
    lateinit var networkData: NetworkData
    
    fun network(block: NetworkData.() -> Unit) {
        networkData = NetworkData().apply(block)
    }
    
    companion object {
        @SuppressLint("StaticFieldLeak") lateinit var instance: Kex
            private set
        
        fun init(block: Kex.() -> Unit) {
            instance = Kex().apply(block)
        }
    }
    
}

class NetworkData {
    lateinit var parsingErrorString: String
    lateinit var connectionErrorString: String
    lateinit var emptyBodyResponseString: String
    
    var errorTracker: NetworkErrorTracker? = null
}
