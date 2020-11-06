package com.uitestcore.elementobjects

interface Select : Element {
    fun selectByValue(value: String)
    fun jsSelectByValue(value: String)
}
