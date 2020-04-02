package com.uitestcore.example

import org.testng.annotations.Test
import com.uitestcore.driverutils.Driver
import org.testng.asserts.SoftAssert

class TestMainPage : TestInit() {
    private lateinit var mainPage: MainPage

    @Test(priority=1)
    fun test() {
        val assert = SoftAssert()
        Driver.openPage()
        mainPage = MainPage()
        mainPage.enterTextAndAccept("kotlin")
        val resultsPage = ResultsPage()
        val results = resultsPage.getResults()
        assert.assertEquals(results.size, 10)
        resultsPage.clickNext()
        assert.assertAll()
    }

}