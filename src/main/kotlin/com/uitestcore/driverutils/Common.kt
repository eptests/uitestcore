package com.uitestcore.driverutils

import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

object Common {
    /**
     * Запускает ожидание WebDriver по таймауту.
     * @param sleepTime - время в секундах.
     */
    fun sleep(sleepTime: Int) {
        try {
            Thread.sleep(sleepTime * 1000.toLong())
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun waitElementVisible(selector: By){
        var wait: WebDriverWait = WebDriverWait(Driver.get(), 10)
        wait.until(ExpectedConditions.visibilityOfElementLocated(selector))
    }

    fun isElementBecomeVisible(selector: By) : Boolean {
        return try {
            waitElementVisible(selector)
            true
        } catch (ex: Exception) {
            false
        }
    }

    fun imagePathByOs(imageName: String) : String {
        return ( if (System.getProperty("os.name").toString() == "Windows") {"\\${imageName}"} else {"/${imageName}"} ).toString()
    }
}