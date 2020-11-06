package com.uitestcore.impl

import com.uitestcore.driverutils.Driver
import com.uitestcore.elementobjects.Select
import org.openqa.selenium.WebElement

internal class SelectImpl protected constructor(wrappedElement: WebElement?) :
    AbstractElement(wrappedElement!!), Select {
    override fun selectByValue(value: String) {
        org.openqa.selenium.support.ui.Select(wrappedElement).selectByValue(value)
    }

    override fun jsSelectByValue(value: String) {
        Driver.executeJS("arguments[0].value = $value", wrappedElement)
    }
}