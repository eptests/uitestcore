package com.uitestcore.example

import com.uitestcore.driverutils.Driver
import com.uitestcore.driverutils.Logger
import io.github.bonigarcia.wdm.WebDriverManager
import org.testng.ITestResult
import org.testng.annotations.*

open class TestInit {
    var baseUrl: String? = null

    @BeforeTest
    fun beforeTest() {
        WebDriverManager.chromedriver().setup()
    }

    @BeforeClass
    @Throws(Exception::class)
    fun beforeClass() {
        baseUrl = "https://google.com"
        Driver.init("Chrome", baseUrl!!);
        Driver.maximize()
    }

    @AfterTest
    fun afterClass() {
        Driver.get()?.quit()
    }

    @AfterMethod
    @Throws(Exception::class)
    fun testCaseFailure(testResult: ITestResult) {
        actionOnFailure(testResult.getStatus(), testResult.getName())
    }

    fun GetBaseUrl(): String? {
        return baseUrl
    }

    @Throws(java.lang.Exception::class)
    fun actionOnFailure(testResult: Int, testName: String) {
        if (testResult == 2) {
            Logger.takeScreenshotToReport(testName)
            Logger.getConsoleLogs()
            Logger.getPageSource()
        }
    }
}