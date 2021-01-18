package com.uitestcore.example

import com.uitestcore.annotation.VisualTestStep
import com.uitestcore.driverutils.Driver
import com.uitestcore.driverutils.ScreenshotUtils
import com.uitestcore.example.pageobjects.MainPage
import org.testng.annotations.Test
import org.testng.asserts.SoftAssert

class VisualTestMainPage : VisualTestInit() {
    private lateinit var mainPage: MainPage

    @VisualTestStep(screenName = "mainPageKotlin")
    @Test(priority=1)
    fun testPass() {
        val assert = SoftAssert()
        Driver.openPage()
        mainPage = MainPage()
        mainPage.enterTextAndAccept("kotlin")
        assert.assertTrue(ScreenshotUtils.compareScreenshots("mainPageKotlin"))
        assert.assertAll()
    }

    @VisualTestStep(screenName = "mainPageJava")
    @Test(priority=2)
    fun testFail() {
        val assert = SoftAssert()
        Driver.openPage()
        mainPage = MainPage()
        mainPage.enterTextAndAccept("java")
        assert.assertTrue(ScreenshotUtils.compareScreenshots("mainPageJava"))
        assert.assertAll()
    }
}