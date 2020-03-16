package com.uitestcore.example

import com.uitestcore.driverutils.Driver
import com.uitestcore.elementobjects.Button
import com.uitestcore.elementobjects.TextField
import com.uitestcore.pageobjects.BasePage
import org.openqa.selenium.support.FindBy

class ResultsPage : BasePage() {
    @FindBy(css="#pnnext > span:nth-child(2)")
    private val nextBtn: Button? = null

    fun open() {
        Driver.openPage()
    }

    fun reload() {
        Driver.reloadPage()
    }

    fun clickNext() {
        nextBtn!!.click()
    }

}