package com.uitestcore.driverutils

import com.uitestcore.containers.Container
import com.uitestcore.containers.DefaultContainerFactory
import com.uitestcore.elementobjects.Element
import com.uitestcore.impl.DefaultElementFactory
import org.openqa.selenium.SearchContext
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindAll
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.FindBys
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator
import org.openqa.selenium.support.pagefactory.ElementLocator
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
        if (List::class.java.isAssignableFrom(clazz)) {
            return decorateList(wrappedElement, clazz)
        }
        return wrappedElement
    }

    private fun <T> decorateElement(wrappedElement: WebElement, clazz: Class<T>): Any {
        return elementFactory.create(clazz as Class<out Element>, wrappedElement)
    }

    private fun <T> decorateContainer(wrappedElement: WebElement, clazz: Class<T>): Any {
        val container = containerFactory.create(clazz as Class<out Container>, wrappedElement)

        PageFactory.initElements(ExtendedFieldDecorator(wrappedElement), container)
        return container
    }

    private fun <T> decorateList(wrappedElementList: List<WebElement>, type: Class<T>): Any {
        val genericType: Type = type.genericSuperclass
        if (genericType is ParameterizedType) {
            var clazz: Class<*>? = null
            try {
                // получаем класс для элементов списка
                clazz = (genericType).actualTypeArguments[0] as Class<*>
            }
            catch (ex: Exception)
            {
                return wrappedElementList
            }

            if (clazz != null) {
                if (Container::class.java!!.isAssignableFrom(clazz)) {
                    //System.out.println("decorateList container: " + clazz)
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
        return wrappedElementList
    }
}