package com.uitestcore.driverutils

import com.uitestcore.containers.AbstractContainer
import com.uitestcore.containers.Container
import com.uitestcore.containers.DefaultContainerFactory
import com.uitestcore.elementobjects.Element
import com.uitestcore.impl.DefaultElementFactory
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.PageFactory
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

@Suppress("UNCHECKED_CAST")
class WebElementDecorator {
    private val elementFactory = DefaultElementFactory()
    private val containerFactory = DefaultContainerFactory()

    fun <T : Any> decorate(clazz: KClass<T>, wrappedElement: WebElement): T {
        if (clazz.isSubclassOf(AbstractContainer::class))
            return decorateContainer(clazz, wrappedElement) as T
        else if (clazz.isSubclassOf(Element::class))
            return elementFactory.create(clazz.java as Class<out Element>, wrappedElement) as T
        else throw Exception("Cannot find element")
    }

    fun <T : Any> decorate(clazz: KClass<T>, wrappedElement: List<WebElement>): List<T> {
        return decorateList(clazz, wrappedElement)
    }

    private fun <T : Any> decorateContainer(clazz: KClass<T>, wrappedElement: WebElement): Any {
        val container = containerFactory.create(clazz.java as Class<out Container>, wrappedElement)

        PageFactory.initElements(ExtendedFieldDecorator(wrappedElement), container)
        container.init(wrappedElement)
        return container
    }

    private fun <T : Any> decorateList(clazz: KClass<T>, wrappedElementList: List<WebElement>): List<T> {
        if (clazz.isSubclassOf(AbstractContainer::class)) {
            val returnList = mutableListOf<Any>()
            for (wrappedElement in wrappedElementList) {
                val container = containerFactory.create(clazz.java as Class<out Container>, wrappedElement)
                returnList.add(container)
                PageFactory.initElements(ExtendedFieldDecorator(wrappedElement), container)
                container.init(wrappedElement)
            }
            return returnList.toList() as List<T>
        }
        if (clazz.isSubclassOf(Element::class)) {
                val returnList = mutableListOf<T>()
                for (wrappedElement in wrappedElementList) {
                val element = elementFactory.create(clazz.java as Class<out Element>, wrappedElement) as T
                returnList.add(element)
            }
            return returnList.toList()
        }
        throw Exception("Cannot find list of elements")
    }
}
