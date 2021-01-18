package com.uitestcore.driverutils

import com.aventstack.extentreports.ExtentReports
import com.aventstack.extentreports.ExtentTest
import com.aventstack.extentreports.Status
import com.aventstack.extentreports.markuputils.ExtentColor
import com.aventstack.extentreports.markuputils.MarkupHelper
import com.aventstack.extentreports.reporter.ExtentHtmlReporter
import org.testng.ITestResult
import java.awt.image.BufferedImage

object ReportUtils {
    var htmlReporter: ExtentHtmlReporter? = null
    var extent: ExtentReports? = null
    var test: ExtentTest? = null

    fun createReport(testName: String) {
        htmlReporter = ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/visual${testName}Report.html")
        extent = ExtentReports()
        extent!!.attachReporter(htmlReporter)
    }

    fun addStep(result: ITestResult, screenName: String, images: List<BufferedImage>) {
        if (result.status == ITestResult.FAILURE) {
            test!!.log(Status.FAIL, MarkupHelper.createLabel(result.name + " Test FAILED because difference is found", ExtentColor.RED))
            test!!.fail(result.throwable)
            test!!.fail("Expected: " + test!!.addScreenCaptureFromPath(System.getProperty("user.dir") + "/ExpectedScreenshots/" + screenName + ".jpg"))
            test!!.fail("Actual: " + test!!.addScreenCaptureFromPath(System.getProperty("user.dir") + "/ActualScreenshots/" + screenName + ".jpg"))
            test!!.fail("Diff: " + test!!.addScreenCaptureFromPath(System.getProperty("user.dir") + "/ResultScreenshots/" + screenName + ".jpg"))
        } else if (result.status == ITestResult.SUCCESS) {
            test!!.log(Status.PASS, MarkupHelper.createLabel(result.name + " Test Case PASSED", ExtentColor.GREEN))
            test!!.pass("Expected: " + test!!.addScreenCaptureFromPath(System.getProperty("user.dir") + "/ExpectedScreenshots/" + screenName + ".jpg"))
        } else {
            test!!.log(Status.SKIP, MarkupHelper.createLabel(result.name + " Test Case SKIPPED", ExtentColor.ORANGE))
            test!!.skip(result.throwable)
        }
        extent!!.flush()
    }
}