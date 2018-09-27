package space.alcorb.kex

import android.annotation.SuppressLint
import android.content.Context

/**
 * @author Yamushev Igor
 * @since  10.09.18
 */
class Kex private constructor() {
    
    lateinit var context: Context
    
    companion object {
        @SuppressLint("StaticFieldLeak") lateinit var instance: Kex
            private set
        
        fun init(block: Kex.() -> Unit) {
            instance = Kex().apply(block)
        }
    }
    
}
