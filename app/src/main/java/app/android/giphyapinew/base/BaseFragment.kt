package app.android.giphyapinew.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.DefinitionParameters
import org.koin.core.parameter.emptyParametersHolder
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

abstract class BaseFragment<VM : BaseViewModel, VB : ViewBinding> : Fragment() {
    private var _binding: VB? = null
//    private lateinit var router: Router
    protected open val viewModel: VM by lazy {
        getViewModel(
            clazz = viewModelKClass(),
            parameters = parameters()
        )
    }
    protected val binding get() = _binding!!

/*    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getResId(), container, false)
        router = Router(MainActivity(), R.id.nav_host_fragment)
        viewModel.router = router

        return view
    }*/

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = createBindingInstance(inflater, container).also { _binding = it }.root
//        router = Router(MainActivity(), R.id.nav_host_fragment)
//        viewModel.router = router
        return view
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    @Suppress("UNCHECKED_CAST")
    private fun viewModelKClass(): KClass<VM> {
        return ((javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[0] as Class<VM>).kotlin
    }

    open fun parameters(): () -> DefinitionParameters = {
        emptyParametersHolder()
    }

    @Suppress("UNCHECKED_CAST")
    private fun bindingClass(): Class<VM> {
        return ((javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[1] as Class<VM>)
    }

    @Suppress("UNCHECKED_CAST")
    private fun createBindingInstance(inflater: LayoutInflater, container: ViewGroup?): VB {
        val vbClass = bindingClass()
        val method = vbClass.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        return method.invoke(null, inflater, container, false) as VB
    }
}