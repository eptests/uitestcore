package com.uitestcore.elementobjects

import org.openqa.selenium.WebElement

interface ElementFactory {
    fun <E : Element> create(elementClass: Class<E>, wrappedElement: WebElement): E
}
