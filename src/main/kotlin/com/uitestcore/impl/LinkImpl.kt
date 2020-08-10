package com.uitestcore.impl

import com.uitestcore.elementobjects.Link
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

internal class LinkImpl protected constructor(wrappedElement: WebElement?) :
    AbstractElement(wrappedElement!!), Link {
    override fun click() {
        wrappedElement.findElement(By.tagName("a")).click()
    }

    override fun text(): String {
        return wrappedElement.text
    }

    override fun href(): String {
        return wrappedElement.getAttribute("href")
    }
}