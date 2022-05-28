package com.example.marvelapp.presentation.common.extensions

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentViewBindingDelegateExt<VB : ViewBinding>(
    val fragment: Fragment,
    val viewBindingFactory: (View) -> VB
) : ReadOnlyProperty<Fragment, VB> {

    private var binding: VB? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {

            val viewLifecycleOwnerLiveDataObserver = Observer<LifecycleOwner?> {
                it?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
                    override fun onDestroy(owner: LifecycleOwner) {
                        super.onDestroy(owner)
                        binding = null
                    }
                })
            }

            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observeForever {
                    viewLifecycleOwnerLiveDataObserver
                }
            }

            override fun onDestroy(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.removeObserver {
                    viewLifecycleOwnerLiveDataObserver
                }
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {

        binding?.let { return it }

        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            throw  IllegalStateException("Should not attempt to get bindings when Fragment views are destroyed")
        }

        return viewBindingFactory(thisRef.requireView()).also { this.binding = it }
    }
}

fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) =
    FragmentViewBindingDelegateExt(this, viewBindingFactory)