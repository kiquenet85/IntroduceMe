package com.kiquenet.introduceme.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

/**
 * Sets the value to the result of a function that is called when both `LiveData`s have data
 * or when they receive updates after that.
 */
fun <T> MediatorLiveData<T>.combineAndCompute(
    others: List<LiveData<*>>,
    onChange: () -> T
) {

    val emittedSources = Array(4, { false })

    others.forEachIndexed { index, itemLiveData ->
        addSourceToMediatorLiveData(this, index, emittedSources, itemLiveData, others.size, onChange)
    }
}

fun <T, A> addSourceToMediatorLiveData(
    result: MediatorLiveData<T>,
    index: Int,
    emittedSources: Array<Boolean>,
    liveData: LiveData<A>,
    sourcesSize: Int,
    onChange: () -> T
): Observer<A> {

    val checkAllSources = {
        if (emittedSources.filter { true }.size.equals(sourcesSize)) {
            result.value = onChange.invoke()
        }
    }

    val observer = Observer { newValue: A ->
        emittedSources[index] = true
        checkAllSources.invoke()
    }

    result.addSource(liveData, observer)
    return observer
}
