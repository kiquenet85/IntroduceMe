package com.kiquenet.introduceme.network

import kotlinx.coroutines.Deferred
import retrofit2.Response

/**
 * @author n.diazgranados
 * TODO: Check if this class is useful for Deferred responses.
 */
open class ResponseEvent<T> {
    var deffered: Deferred<T>? = null
        private set
    var response: Response<T>? = null
        private set
    var throwable: Throwable? = null
        private set
    var isSuccessful: Boolean = false
        private set

    fun setResponse(call: Deferred<T>, response: Response<T>) {
        this.deffered = call
        this.response = response
        this.isSuccessful = true
    }

    fun setBadResponse(call: Deferred<T>, throwable: Throwable) {
        this.deffered = call
        this.throwable = throwable
        isSuccessful = false
    }
}
