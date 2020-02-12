package com.uitestcore.impl

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
}