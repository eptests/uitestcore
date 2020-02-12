package com.uitestcore.elementobjects

interface TabElement : Element {
    fun click()

    fun isActive(): Boolean

    fun getText(): String
}
