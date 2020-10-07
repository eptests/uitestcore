package com.uitestcore.containers

import org.openqa.selenium.WebElement

class DefaultContainerFactory : ContainerFactory {
    override fun <C : Container> create(containerClass: Class<C>, wrappedElement: WebElement): C {
        val container = createInstanceOf(containerClass)
        container.init(wrappedElement)
        return container
    }

    private fun <C : Container> createInstanceOf(containerClass: Class<C>): C {
        try {
            return containerClass.getDeclaredConstructor().newInstance()
        } catch (e: InstantiationException) {
            throw RuntimeException(e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        }

    }
}
