package com.uitestcore.elementobjects

import com.uitestcore.driverutils.Driver

interface Element {
    val isDisplayed: Boolean

    val isExists: Boolean

    fun scrollTo()
}
