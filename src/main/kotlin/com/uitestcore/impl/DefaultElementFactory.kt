package com.uitestcore.impl

import com.uitestcore.elementobjects.Element
import com.uitestcore.elementobjects.ElementFactory
import org.openqa.selenium.WebElement
import java.lang.reflect.InvocationTargetException
import java.text.MessageFormat

class DefaultElementFactory : ElementFactory {
    override fun <E : Element> create(elementClass: Class<E>, wrappedElement: WebElement): E {
        return try {
            findImplementationFor(elementClass)
                .getDeclaredConstructor(WebElement::class.java)
                .newInstance(wrappedElement)
        } catch (e: InstantiationException) {
            throw RuntimeException(e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        } catch (e: InvocationTargetException) {
            throw RuntimeException(e)
        } catch (e: NoSuchMethodException) {
            throw RuntimeException(e)
        }
    }

    private fun <E : Element?> findImplementationFor(elementClass: Class<E>): Class<out E> {
        return try {
            Class.forName(
                MessageFormat.format(
                    "{0}.{1}Impl",
                    javaClass.getPackage().name,
                    elementClass.simpleName
                )
            ) as Class<out E>
        } catch (e: ClassNotFoundException) {
            System.out.println("Class: "+elementClass.simpleName)
            throw RuntimeException(e)
        }
    }
}