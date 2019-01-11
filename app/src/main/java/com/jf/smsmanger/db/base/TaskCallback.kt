package com.jf.smsmanger.db.base

interface TaskCallback<T> {
    fun onStart()
    fun onSuccess(rst:T)
    fun onError(exception: Throwable)
}