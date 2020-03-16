package com.uitestcore.example

import com.uitestcore.driverutils.Driver
import com.uitestcore.elementobjects.Button
import com.uitestcore.elementobjects.TextField
import com.uitestcore.pageobjects.BasePage
import org.openqa.selenium.support.FindBy

class MainPage : BasePage() {
    @FindBy(css="#tsf > div:nth-child(2) > div.A8SBwf > div.FPdoLc.tfB0Bf > center")
    private val searchBtn: SearchButton? = null

    @FindBy(css="input.gLFyf")
    private val searchField: TextField? = null

    fun open() {
        Driver.openPage()
    }

    fun reload() {
        Driver.reloadPage()
    }

    fun enterTextAndAccept(text: String) {
        searchField!!.clearAndType(text)
        searchBtn!!.clickSearch()
    }

}