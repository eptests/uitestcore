package com.uitestcore.example

import com.uitestcore.driverutils.Driver
import com.uitestcore.elementobjects.Button
import com.uitestcore.elementobjects.TextField
import com.uitestcore.pageobjects.BasePage
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

class MainPage : BasePage() {
    @FindBy(css=".FPdoLc.tfB0Bf")
    private val searchBtn: SearchButton? = null

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
        searchField!!.clearAndType(text)
        logo!!.click()
        searchBtn!!.clickSearch()
    }

}