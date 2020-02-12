package com.uitestcore.impl

import com.uitestcore.elementobjects.TextField
import org.openqa.selenium.WebElement

internal class TextFieldImpl protected constructor(wrappedElement: WebElement?) :
    AbstractElement(wrappedElement!!), TextField {
    override fun type(text: String) {
        wrappedElement.sendKeys(text)
    }

    override fun clear() {
        wrappedElement.clear()
    }

    override fun clearAndType(text: String) {
        clear()
        type(text)
    }
}