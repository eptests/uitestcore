package com.uitestcore.driverutils

import io.qameta.allure.Attachment
import org.apache.commons.io.FileUtils
import org.jsoup.Connection.Base
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.logging.LogEntries
import org.openqa.selenium.logging.LogType
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*


object Logger {
    val screenShotPath: String = "/Screenshots"
    /**
     * Сделать скриншот текущего состояния браузера и сохранить в папку.
     * @param fileName - название файла.
     */
    @Throws(java.lang.Exception::class)
    fun takeScreenshot(fileName: String): String? {
        return try {
            val scrShot = Driver.get() as TakesScreenshot
            //Call getScreenshotAs method to create image file
            val srcFile = scrShot.getScreenshotAs(OutputType.FILE)
            //Move image file to new destination
            val imagePath: String = System.getProperty("user.dir") + "$screenShotPath//$fileName.jpg"
            val destFile = File(imagePath)
            //Copy file at destination
            FileUtils.copyFile(srcFile, destFile)
            imagePath
        } catch (e: java.lang.Exception) {
            println(e.message)
            null
        }
    }

    /**
     * Формирует имя скриншота для отчета.
     * @param testName - название тесткейса.
     */
    @Throws(Exception::class)
    fun takeScreenshotToReport(testName: String) {
        takeScreenshot(testName + "_fail")?.let { addScreenshotToReport(it) }
    }

    /**
     * Получить код страницы.
     */
    @Attachment(value = "Page sorce")
    fun getPageSource(): String? {
        return Driver.get().pageSource
    }

    /**
     * Получить логи из консоли браузера.
     */
    @Attachment
    fun getConsoleLogs(): StringBuilder? {
        val logs = StringBuilder()
        logs.append("***** Browser Console Logs: *****\n")
        val browserLogEntries: LogEntries? = Driver.get().manage()?.logs()?.get(LogType.BROWSER)
        if (browserLogEntries != null) {
            for (entry in browserLogEntries) {
                val entryStr: String =
                    Date(entry.timestamp).toString() + " " + entry.level + " " + entry.message
                //do something useful with the data
                logs.append(entryStr + "\n")
            }
        }
        /*logs.append("\n\n*******Perfomance Logs: *********\n");
        LogEntries networkLogsEntries = driver.manage().logs().get(LogType.PERFORMANCE);
        for (LogEntry entry : networkLogsEntries) {
            //do something useful with the data
        	logs.append( entry.toString() +"\n");
        }
        logs.append("*********************************\n");*/return logs
    }

    fun actionOnTestComplete(testResult: Int, testName: String) {
        if (testResult == 2) {
            actionOnFailure(testName)
        }
    }

    fun actionOnFailure(testName: String) {
        takeScreenshotToReport(testName)
        getConsoleLogs()
        getPageSource()
    }

    /**
     * Приложить скриншот к отчету allure.
     * @param screenshotPath - путь к скришоту.
     */
    @Attachment(value = "Page screenshot", type = "image/jpg")
    fun addScreenshotToReport(screenshotPath: String): ByteArray {
        return Files.readAllBytes(Paths.get(screenshotPath))
    }

    /**
     * Сделать скриншт в формате BASE64
     */
    fun takeBase64Screenshot(): String? {
        return try {
            //Call getScreenshotAs method to create image file
            (Driver.get() as TakesScreenshot).getScreenshotAs(OutputType.BASE64)
        } catch (e: java.lang.Exception) {
            println(e.message)
            null
        }
    }
}