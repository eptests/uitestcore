package com.uitestcore.impl

import com.uitestcore.elementobjects.CheckBox
import org.openqa.selenium.WebElement

internal class CheckBoxImpl protected constructor(wrappedElement: WebElement?) :
    AbstractElement(wrappedElement!!), CheckBox {
    override fun click() {
        wrappedElement.click()
    }

    override fun isSelected(): Boolean {
        return wrappedElement.isSelected
    }

    override fun select(state: Boolean) {
        if (wrappedElement.isSelected != state)
            wrappedElement.click()
    }
}