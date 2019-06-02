package com.kiquenet.introduceme.view_models.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import javax.inject.Inject
import javax.inject.Provider

/**
 * @author n.diazgranados
 * Create a ViewModelFactory
 */
class ViewModelFactory @Inject
constructor(private val viewModelProviders: Map<Class<out ViewModel>, Provider<ViewModel>>) :
    ViewModelProvider.Factory {

    /*override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelProviders[modelClass]!!.get() as T
    }*/

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val provider = viewModelProviders[modelClass]
            ?: viewModelProviders.entries.first { modelClass.isAssignableFrom(it.key) }.value

        return provider.get() as T
    }
}
