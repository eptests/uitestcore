package com.uitestcore.example

import com.uitestcore.driverutils.Driver
import com.uitestcore.driverutils.Logger
import org.testng.ITestResult
import org.testng.annotations.*

open class TestInit {

    @BeforeClass
    @Throws(Exception::class)
    fun beforeClass() {
        Driver.init()
        Driver.maximize()
    }

    @AfterTest
    fun afterClass() {
        Driver.get().quit()
    }

    @AfterMethod
    @Throws(Exception::class)
    fun testCaseFailure(testResult: ITestResult) {
        Logger.actionOnTestComplete(testResult.status, testResult.name)
    }
}