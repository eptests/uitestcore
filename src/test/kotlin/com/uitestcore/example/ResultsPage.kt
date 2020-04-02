package com.uitestcore.example

import com.uitestcore.driverutils.Driver
import com.uitestcore.elementobjects.Button
import com.uitestcore.pageobjects.BasePage
import org.openqa.selenium.By
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
        nextBtn!!.scrollTo()
        nextBtn!!.click()
    }

    fun getResults(): List<SearchResultBlock> {
        return Driver.findDecoratedElements(SearchResultBlock().javaClass, By.cssSelector(".g")) as List<SearchResultBlock>
    }

}