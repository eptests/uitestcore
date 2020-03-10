package com.uitestcore.impl

import com.uitestcore.elementobjects.Select
import org.openqa.selenium.WebElement

internal class SelectImpl protected constructor(wrappedElement: WebElement?) :
    AbstractElement(wrappedElement!!), Select {
    override fun selectByValue(value: String) {
        org.openqa.selenium.support.ui.Select(wrappedElement).selectByValue(value)
    }
}