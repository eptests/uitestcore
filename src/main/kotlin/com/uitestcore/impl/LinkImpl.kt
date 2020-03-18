package com.uitestcore.impl

import com.uitestcore.elementobjects.Button
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

internal class LinkImpl protected constructor(wrappedElement: WebElement?) :
    AbstractElement(wrappedElement!!), Button {
    override fun click() {
        wrappedElement.findElement(By.tagName("a")).click()
    }
}