package com.uitestcore.example.pageobjects

import com.uitestcore.driverutils.Driver
import com.uitestcore.elementobjects.Button
import com.uitestcore.elementobjects.TextField
import com.uitestcore.pageobjects.BasePage
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

class MainPage : BasePage() {
    @FindBy(css="input.gLFyf")
    private val searchField: TextField? = null

    @FindBy(css="#hplogo")
    private val logo: WebElement? = null

    fun open() {
        Driver.openPage()
    }

    fun reload() {
        Driver.reloadPage()
    }

    fun enterTextAndAccept(text: String) {
        //searchField!!.fillJS(text)
        searchField!!.clearAndType(text)
        Driver.findDecoratedElement(Button::class, By.cssSelector("[value='Поиск в Google']")).click()
    }

}