package com.uitestcore.containers

import com.uitestcore.driverutils.Driver
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

abstract class AbstractContainer : Container {
    var wrappedElement: WebElement? = null

    override val isDisplayed: Boolean
        get() = wrappedElement!!.isDisplayed

    override val isExists: Boolean
        get() = wrappedElement != null

    override fun init(wrappedElement: WebElement) {
        this.wrappedElement = wrappedElement
    }

    fun findElement(by: By): WebElement {
        return this.wrappedElement!!.findElement(by)
    }

    fun findElements(by: By): List<WebElement> {
        return this.wrappedElement!!.findElements(by)
    }

    override fun scrollTo() {
        Driver.scrollToElement(wrappedElement!!)
    }

    override fun jsFill(text: String) {
        Driver.executeJS("arguments[0].value = '${text}'", wrappedElement!!)
    }

    override fun jsClick() {
        Driver.executeJS("arguments[0].click()", wrappedElement!!)
    }
}
