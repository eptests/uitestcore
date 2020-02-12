package com.uitestcore.containers

import org.openqa.selenium.WebElement

interface ContainerFactory {
    fun <C : Container> create(containerClass: Class<C>, wrappedElement: WebElement): C
}
