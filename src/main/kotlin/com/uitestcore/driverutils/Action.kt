package com.uitestcore.driverutils

@FunctionalInterface
interface Action<T> {
    @Throws(Exception::class)
    operator fun invoke(var1: T)

    fun execute(value: T) {
        try {
            invoke(value)
        } catch (ex: Exception) {
            throw RuntimeException(ex)
        }
    }
}