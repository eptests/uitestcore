package com.uitestcore.driverutils

import com.uitestcore.containers.Container
import com.uitestcore.containers.DefaultContainerFactory
import com.uitestcore.elementobjects.Element
import com.uitestcore.impl.DefaultElementFactory
import org.openqa.selenium.SearchContext
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator
import org.openqa.selenium.support.pagefactory.ElementLocator
import java.lang.reflect.*

@Suppress("UNCHECKED_CAST")
class ExtendedFieldDecorator(searchContext: SearchContext) :
    DefaultFieldDecorator(DefaultElementLocatorFactory(searchContext)) {
    private val elementFactory = DefaultElementFactory()
    private val containerFactory = DefaultContainerFactory()

    override fun decorate(loader: ClassLoader, field: Field): Any? {
        if (Container::class.java.isAssignableFrom(field.type)) {
            return decorateContainer(loader, field)
        }
        if (Element::class.java.isAssignableFrom(field.type)) {
            return decorateElement(loader, field)
        }
        if (List::class.java.isAssignableFrom(field.type)) {
            return decorateList(loader, field)
        }
        return super.decorate(loader, field)
    }

    private fun decorateElement(loader: ClassLoader, field: Field): Any {
        val wrappedElement = proxyForLocator(loader, createLocator(field))
        return elementFactory.create(field.type as Class<out Element>, wrappedElement)
    }

    private fun createLocator(field: Field): ElementLocator {
        return factory.createLocator(field)
    }

    private fun decorateContainer(loader: ClassLoader, field: Field): Any {
        val wrappedElement = proxyForLocator(loader, createLocator(field))
        val container = containerFactory.create(field.type as Class<out Container>, wrappedElement!!)

        PageFactory.initElements(ExtendedFieldDecorator(wrappedElement), container)
        container.init(wrappedElement)
        return container
    }

    private fun decorateList(loader: ClassLoader, field: Field): Any {
        val wrappedElementList = proxyForListLocator(loader, createLocator(field))
        val genericType: Type = field.genericType
        if (genericType is ParameterizedType) {
            val clazz: Class<*>?
            try {
                // получаем класс для элементов списка
                clazz = (genericType).actualTypeArguments[0] as Class<*>
            }
            catch (ex: Exception)
            {
                return wrappedElementList
            }

            if (Container::class.java.isAssignableFrom(clazz)) {
                val returnList = mutableListOf<Any>()
                for (wrappedElement in wrappedElementList!!) {
                    val container = containerFactory.create(clazz as Class<out Container>, wrappedElement)
                    returnList.add(container)
                    PageFactory.initElements(ExtendedFieldDecorator(wrappedElement), container)
                    container.init(wrappedElement)
                }
                return returnList
            }
            if (Element::class.java.isAssignableFrom(clazz)) {
                val returnList = mutableListOf<Any>()
                for (wrappedElement in wrappedElementList!!) {
                    val element = elementFactory.create(clazz as Class<out Element>, wrappedElement)
                    returnList.add(element)
                }
                return returnList
            }
            return wrappedElementList

        }
        return wrappedElementList
    }
}
