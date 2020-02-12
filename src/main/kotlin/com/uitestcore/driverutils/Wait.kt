package com.uitestcore.driverutils

import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

object Wait {
    private var waitTime: Long = 20
    var wait = WebDriverWait(Driver.get(), waitTime)

    fun setTimeOut(time: Long){
        waitTime = time
    }

    fun until(condition: ExpectedCondition<Boolean>){
        wait.until(condition)
    }

    fun elementPresence(by: By){
        wait.until(ExpectedConditions.presenceOfElementLocated(by))
    }

    fun elementAbsence(element: WebElement){
        wait.until(ExpectedConditions.invisibilityOf(element))
    }

    fun elementVisibility(by: By){
        wait.until(ExpectedConditions.visibilityOf(Driver.findElement(by)))
    }

    fun elementVisibility(element: WebElement){
        wait.until(ExpectedConditions.visibilityOf(element))
    }

    fun titleIs(title: String){
        wait.until(ExpectedConditions.titleIs(title))
    }

    fun locationIs(location: String){
        wait.until(ExpectedConditions.urlMatches(".*/$location"))
    }
}
