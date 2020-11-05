package com.uitestcore.elementobjects

interface Element {
    val isDisplayed: Boolean

    val isExists: Boolean

    fun scrollTo()

    fun jsFill(text: String)

    fun jsClick()
}
