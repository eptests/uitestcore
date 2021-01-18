package com.uitestcore.example.pageobjects

import com.uitestcore.driverutils.Driver
import com.uitestcore.elementobjects.Button
import com.uitestcore.pageobjects.BasePage
import org.openqa.selenium.By
import org.openqa.selenium.support.FindBy

class ResultsPage : BasePage() {
    @FindBy(css="#pnnext > span:nth-child(2)")
    private val nextBtn: Button? = null
    @FindBy(css="#search #rso .g")
    private val results: List<SearchResultBlock>? = null
    @FindBy(css="#search #rso .g")
    private val result: SearchResultBlock? = null

    fun open() {
        Driver.openPage()
    }

    fun reload() {
        Driver.reloadPage()
    }

    fun clickNext() {
        nextBtn!!.scrollTo()
        nextBtn.click()
    }

    fun getResult(): SearchResultBlock {
        return Driver.findDecoratedElement(SearchResultBlock::class, By.cssSelector("#search #rso .g"))
    }

    fun getResults(): List<SearchResultBlock> {
        return Driver.findDecoratedElements(SearchResultBlock::class, By.cssSelector(".g"))
    }

    fun getPFResult(): SearchResultBlock {
        return result!!
    }

    fun getPFResults(): List<SearchResultBlock> {
        return results!!
    }

}