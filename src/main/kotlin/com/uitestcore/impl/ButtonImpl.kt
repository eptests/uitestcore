package com.uitestcore.impl

import com.uitestcore.elementobjects.Button
import org.openqa.selenium.WebElement

internal class ButtonImpl protected constructor(wrappedElement: WebElement?) :
    AbstractElement(wrappedElement!!), Button {
    override fun click() {
        wrappedElement.click()
    }
}