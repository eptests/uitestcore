package com.uitestcore.impl

import com.uitestcore.driverutils.Driver
import com.uitestcore.elementobjects.Element
import org.openqa.selenium.WebElement
import java.lang.Exception

internal abstract class AbstractElement protected constructor(protected val wrappedElement: WebElement) : Element {
    override val isDisplayed: Boolean
        get() = wrappedElement.isDisplayed
    override val isExists: Boolean
        get() = try {
            wrappedElement.isDisplayed
            true
        }
        catch (ex: Exception) {
            false
        }
    
    override fun scrollTo() {
        Driver.scrollToElement(wrappedElement)
    }

    override fun jsFill(text: String) {
        Driver.executeJS("arguments[0].value = '${text}'", wrappedElement)
    }

    override fun jsClick() {
        Driver.executeJS("arguments[0].click()", wrappedElement)
    }
}