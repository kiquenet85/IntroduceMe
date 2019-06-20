package com.kiquenet.introduceme.util

import android.os.Build
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

/**
 * Extension function for merging several [LiveData] sources.
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

/**
 * Extension function on a query to avoid notifications not related to
 * specific object hen using [LiveData].
 */
fun <T> LiveData<T>.getDistinct(): LiveData<T> {
    val distinctLiveData = MediatorLiveData<T>()
    distinctLiveData.addSource(this, object : Observer<T> {
        private var initialized = false
        private var lastObj: T? = null
        override fun onChanged(obj: T?) {
            if (!initialized) {
                initialized = true
                lastObj = obj
                distinctLiveData.postValue(lastObj)
            } else if ((obj == null && lastObj != null)
                || obj != lastObj) {
                lastObj = obj
                distinctLiveData.postValue(lastObj)
            }
        }
    })
    return distinctLiveData
}

/**
 * Extension function for delegate adapter-
 */
fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

/**
 * Function to justify text.
 */
@RequiresApi(Build.VERSION_CODES.O)
fun applyJustificationOnText(listText: List<TextView>) {
    listText.forEach { it.justificationMode = Layout.JUSTIFICATION_MODE_INTER_WORD }
}

/**
 * Not thread safe lazy initializer.
 */
fun <T> androidLazy(initializer: () -> T) : Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)