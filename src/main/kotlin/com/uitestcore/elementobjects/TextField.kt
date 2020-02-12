package com.uitestcore.elementobjects

interface TextField : Element {
    fun type(text: String)

    fun clear()

    fun clearAndType(text: String)
}
