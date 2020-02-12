package com.uitestcore.containers

import org.openqa.selenium.WebElement
import com.uitestcore.elementobjects.Element

interface Container : Element {
    fun init(wrappedElement: WebElement)
}
