package com.uitestcore.driverutils

import com.uitestcore.containers.Container
import com.uitestcore.containers.DefaultContainerFactory
import com.uitestcore.elementobjects.Element
import com.uitestcore.impl.DefaultElementFactory
import org.openqa.selenium.SearchContext
import org.openqa.selenium.support.FindAll
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.FindBys
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator
import org.openqa.selenium.support.pagefactory.ElementLocator
import java.lang.Exception
import java.lang.reflect.*
import java.util.*


class ExtendedFieldDecorator(searchContext: SearchContext) :
    DefaultFieldDecorator(DefaultElementLocatorFactory(searchContext)) {
    private val elementFactory = DefaultElementFactory()
    private val containerFactory = DefaultContainerFactory()

    override fun decorate(loader: ClassLoader, field: Field): Any? {
        if (Container::class.java!!.isAssignableFrom(field.type)) {
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

    private fun checkAnnotations(field: Field): Boolean {
        if (field.getAnnotation(FindBy::class.java) == null &&
            field.getAnnotation(FindBys::class.java) == null &&
            field.getAnnotation(FindAll::class.java) == null) {
            return false
        }
        return true
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
        val container = containerFactory.create(field.type as Class<out Container>, wrappedElement)

        PageFactory.initElements(ExtendedFieldDecorator(wrappedElement), container)
        return container
    }

    private fun decorateList(loader: ClassLoader, field: Field): Any {
        //System.out.println("decorateList: " + field + ", " + field.type)
        val wrappedElementList = proxyForListLocator(loader, createLocator(field))
        val genericType: Type = field.genericType
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
                    //System.out.println("decorateList container: " + returnList)
                    return returnList
                }
                if (Element::class.java.isAssignableFrom(clazz)) {
                    //System.out.println("decorateList element: " + clazz)
                    var returnList = mutableListOf<Any>()
                    for (wrappedElement in wrappedElementList!!) {
                        var element = elementFactory.create(clazz as Class<out Element>, wrappedElement)
                        returnList.add(element)
                    }
                    //System.out.println("decorateList element: " + returnList)
                    return returnList
                }
            }
            return wrappedElementList

        }
        return wrappedElementList
    }
}
