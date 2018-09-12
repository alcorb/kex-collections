package space.alcorb.kex.stubs

import space.alcorb.kex.collections.R
import space.alcorb.kex.collections.databinding.ViewStubLoadingBinding

/**
 * @author Yamushev Igor
 * @since  12.09.18
 */
class LoadingStubHolder : StubHolder<ViewStubLoadingBinding>() {
    
    override val layout: Int = R.layout.view_stub_loading
    
    override fun onBind(binding: ViewStubLoadingBinding) { }
    
}