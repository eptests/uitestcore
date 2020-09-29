package com.uitestcore.driverutils

import com.uitestcore.containers.Container
import com.uitestcore.containers.DefaultContainerFactory
import com.uitestcore.elementobjects.Element
import com.uitestcore.impl.DefaultElementFactory
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.PageFactory
import java.lang.Exception
import java.lang.reflect.*


class WebElementDecorator {
    private val elementFactory = DefaultElementFactory()
    private val containerFactory = DefaultContainerFactory()

    fun <T> decorate(clazz: Class<T>, wrappedElement: WebElement): Any? {
        if (Container::class.java!!.isAssignableFrom(clazz)) {
            return decorateContainer(wrappedElement, clazz)
        }
        if (Element::class.java.isAssignableFrom(clazz)) {
            return decorateElement(wrappedElement, clazz)
        }
        return wrappedElement
    }

    fun <T> decorate(clazz: Class<T>, wrappedElement: List<WebElement>): Any? {
        return decorateList(wrappedElement, clazz)
    }

    private fun <T> decorateElement(wrappedElement: WebElement, clazz: Class<T>): Any {
        return elementFactory.create(clazz as Class<out Element>, wrappedElement)
    }

    private fun <T> decorateContainer(wrappedElement: WebElement, clazz: Class<T>): Any {
        val container = containerFactory.create(clazz as Class<out Container>, wrappedElement)

        PageFactory.initElements(ExtendedFieldDecorator(wrappedElement), container)
        return container
    }

    private fun <T> decorateList(wrappedElementList: List<WebElement>, clazz: Class<T>): List<Any> {

            if (clazz != null) {
                if (Container::class.java!!.isAssignableFrom(clazz)) {
                    var returnList = mutableListOf<Any>()
                    for (wrappedElement in wrappedElementList!!) {
                        val container = containerFactory.create(clazz as Class<out Container>, wrappedElement)
                        returnList.add(container)
                        PageFactory.initElements(ExtendedFieldDecorator(wrappedElement), container)
                    }
                    return returnList
                }
                if (Element::class.java.isAssignableFrom(clazz)) {
                    var returnList = mutableListOf<Any>()
                    for (wrappedElement in wrappedElementList!!) {
                        var element = elementFactory.create(clazz as Class<out Element>, wrappedElement)
                        returnList.add(element)
                    }
                    return returnList
                }
            }
            return wrappedElementList
    }
}
