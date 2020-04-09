package com.uitestcore.impl

import com.uitestcore.elementobjects.Text
import org.openqa.selenium.WebElement

internal class TextImpl protected constructor(wrappedElement: WebElement?) :
    AbstractElement(wrappedElement!!), Text {
    override fun text() {
        wrappedElement.text
    }
}