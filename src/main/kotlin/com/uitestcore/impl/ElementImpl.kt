package com.uitestcore.impl

import com.uitestcore.elementobjects.Element
import org.openqa.selenium.WebElement


class ElementImpl(protected var webElement: WebElement, override val isDisplayed: Boolean, override val isExists: Boolean) : Element {
    override fun scrollTo() {

    }
}