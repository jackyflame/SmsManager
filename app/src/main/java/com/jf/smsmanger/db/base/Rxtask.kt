package com.jf.smsmanger.db.base

interface Rxtask<T> {
    fun runTask():T
}