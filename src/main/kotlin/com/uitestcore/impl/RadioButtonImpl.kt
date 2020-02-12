package com.uitestcore.impl

import com.uitestcore.elementobjects.RadioButton
import org.openqa.selenium.WebElement

internal class RadioButtonImpl protected constructor(wrappedElement: WebElement?) :
    AbstractElement(wrappedElement!!), RadioButton {
    override fun click() {
        wrappedElement.click()
    }

    override fun isSelected(): Boolean {
        return wrappedElement.isSelected
    }
}