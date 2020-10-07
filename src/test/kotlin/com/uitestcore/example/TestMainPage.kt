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
        val result = resultsPage.getResult()
        val results = resultsPage.getResults()
        val pfResult = resultsPage.getPFResult()
        val pfResults = resultsPage.getPFResults()
        assert.assertEquals(results.size, 10)
        result.getText()
        results[0].getText()
        pfResult.getText()
        pfResults[0].getText()
        resultsPage.clickNext()
        assert.assertAll()
    }

}