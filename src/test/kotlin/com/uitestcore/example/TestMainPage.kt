package com.uitestcore.example

import org.testng.annotations.Test
import com.uitestcore.driverutils.Driver

class TestMainPage : TestInit() {
    private lateinit var mainPage: MainPage

    @Test(priority=1)
    fun test() {
        Driver.openPage()
        mainPage = MainPage()
        mainPage.enterTextAndAccept("kotlin")
        ResultsPage().clickNext()
    }

}