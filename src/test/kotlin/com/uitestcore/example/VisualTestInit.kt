package com.uitestcore.example

import com.aventstack.extentreports.ExtentReports
import com.aventstack.extentreports.ExtentTest
import com.aventstack.extentreports.MediaEntityBuilder
import com.aventstack.extentreports.Status
import com.aventstack.extentreports.markuputils.ExtentColor
import com.aventstack.extentreports.markuputils.MarkupHelper
import com.aventstack.extentreports.reporter.ExtentHtmlReporter
import com.uitestcore.annotation.VisualTestStep
import com.uitestcore.driverutils.Driver
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.testng.ITestResult
import org.testng.annotations.AfterMethod
import org.testng.annotations.AfterTest
import org.testng.annotations.BeforeClass
import org.testng.annotations.BeforeTest
import java.lang.reflect.Method
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*


open class VisualTestInit {
    var htmlReporter: ExtentHtmlReporter? = null
    var extent: ExtentReports? = null
    var test: ExtentTest? = null

    @BeforeTest
    fun config() {
        htmlReporter = ExtentHtmlReporter("MyOwnReport.html")
        extent = ExtentReports()
        extent!!.attachReporter(htmlReporter)
    }

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
    open fun teardown(result: ITestResult, method: Method) {
        val visualTest: VisualTestStep = method.getAnnotation(VisualTestStep::class.java)
        test = extent!!.createTest("Compare: ${visualTest.screenName}")
        if (result.status == ITestResult.FAILURE) {
            val mediaModelExpected = MediaEntityBuilder.createScreenCaptureFromBase64String(Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/ExpectedScreenshots/" + visualTest.screenName + ".png")))).build()
            val mediaModelCurrent = MediaEntityBuilder.createScreenCaptureFromBase64String(Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/ActualScreenshots/" + visualTest.screenName + ".png")))).build()
            val mediaModelDiff = MediaEntityBuilder.createScreenCaptureFromBase64String(Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/ResultScreenshots/" + visualTest.screenName + ".png")))).build()
            test!!.fail("expected:", mediaModelExpected)
            test!!.fail("actual:", mediaModelCurrent)
            test!!.fail("result:", mediaModelDiff)
        } else if (result.status == ITestResult.SUCCESS) {
            test!!.log(Status.PASS, MarkupHelper.createLabel(result.name + " Test Case PASSED", ExtentColor.GREEN))
            test!!.pass("Test: " + test!!.addScreenCaptureFromPath("/ExpectedScreenshots/"+ visualTest.screenName +".png"))
            test!!.addScreenCaptureFromPath("src/test/resources/img.jpg", "Test").pass("Pass")
        } else {
            test!!.log(Status.SKIP, MarkupHelper.createLabel(result.name + " Test Case SKIPPED", ExtentColor.ORANGE))
            test!!.skip(result.throwable)
        }
            extent!!.flush()
        }
}