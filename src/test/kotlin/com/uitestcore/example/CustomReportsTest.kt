package com.uitestcore.example

import com.aventstack.extentreports.ExtentReports
import com.aventstack.extentreports.ExtentTest
import com.aventstack.extentreports.Status
import com.aventstack.extentreports.markuputils.ExtentColor
import com.aventstack.extentreports.markuputils.MarkupHelper
import com.aventstack.extentreports.reporter.ExtentHtmlReporter
import com.uitestcore.driverutils.Driver
import org.testng.Assert
import org.testng.ITestResult
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeTest
import org.testng.annotations.Test


class CustomReportsTest: TestInit() {
    var htmlReporter: ExtentHtmlReporter? = null
    var extent: ExtentReports? = null
    var test: ExtentTest? = null

    @BeforeTest
    fun config() {
        htmlReporter = ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/MyOwnReport.html")
        extent = ExtentReports()
        extent!!.attachReporter(htmlReporter)
    }

    @Test
    fun captureScreenshot() {
        test = extent!!.createTest("captureScreenshot")
        Driver.openPage()
        Assert.assertEquals("Google", Driver.getPageTitle())
    }

    //Method to create html report after comparing screenshots
    @AfterMethod
    @Throws(Exception::class)
    fun getResult(result: ITestResult) {
        if (result.status == ITestResult.FAILURE) {
            val screenShotPath: String = Driver.takeFullScreenshot("MyFullPageScreenshot")
            test!!.log(Status.FAIL, MarkupHelper.createLabel(result.name + " Test case FAILED due to below issues:", ExtentColor.RED))
            test!!.fail(result.throwable)
            test!!.fail("Snapshot below: " + test!!.addScreenCaptureFromPath(screenShotPath))
        } else if (result.status == ITestResult.SUCCESS) {
            test!!.log(Status.PASS, MarkupHelper.createLabel(result.name + " Test Case PASSED", ExtentColor.GREEN))
        } else {
            test!!.log(Status.SKIP, MarkupHelper.createLabel(result.name + " Test Case SKIPPED", ExtentColor.ORANGE))
            test!!.skip(result.throwable)
        }
        extent!!.flush()
    }
}