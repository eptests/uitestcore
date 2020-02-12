package com.uitestcore.data

import java.lang.RuntimeException

open class DataClass<T>() {

    fun setValues(valueFunc: (T) -> Unit): T {
        try {
            valueFunc(this as T)
            return this
        } catch (ex: Exception) {
            throw RuntimeException(ex)
        }
    }
}