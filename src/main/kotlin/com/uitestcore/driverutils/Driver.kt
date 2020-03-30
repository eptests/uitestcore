package com.uitestcore.driverutils

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.logging.LogType
import org.openqa.selenium.logging.LoggingPreferences
import org.openqa.selenium.remote.CapabilityType
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.logging.Level

import java.nio.file.Path
import java.nio.file.Paths
import java.awt.Robot
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.awt.event.KeyEvent

object Driver {
    lateinit private var instance: WebDriver
    lateinit var jsExecutor: JavascriptExecutor
    lateinit var baseUrl: String
    lateinit var robot: Robot

    fun init(driverName: String, url: String) {
        instance = createDriver(driverName)
        jsExecutor = instance as JavascriptExecutor
        baseUrl = url
        robot = Robot()
    }

    fun get(): WebDriver {
        return instance
    }


    fun maximize() {
        instance.manage().window().maximize()
    }

    fun openPage() {
        instance.get(baseUrl)
    }

    fun openPage(path: String) {
        instance.get(baseUrl+path)
    }

    fun reloadPage() {
        instance.navigate().refresh()
    }

    fun getElementById(id: String): WebElement{
        return instance.findElement(By.id(id));
    }

    fun getElementByCss(css: String): WebElement{
        return instance.findElement(By.cssSelector(css));
    }

    fun getElementByXpath(xpath: String): WebElement{
        return instance.findElement(By.xpath(xpath));
    }

    fun getElementByName(name: String): WebElement{
        return instance.findElement(By.name(name));
    }

    fun getElementByText(text: String): WebElement{
        return instance.findElement(By.linkText(text));
    }

    fun findElement(by: By): WebElement {
        return instance.findElement(by)
    }

    fun findElements(by: By): List<WebElement> {
        return instance.findElements(by)
    }

    /* TO DO: return decorated web element
    fun <C : Container> findDecorated(containerClass: Class<C>, by: By): Any? {
        return ExtendedFieldDecorator(instance).decorate(containerClass.getClassLoader(), field)
    }*/

    fun scrollToElement(element: WebElement) {
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element)
    }

    fun uploadImage(element: WebElement, filePath: String) {
        val resourceDirectory: Path = Paths.get("src", "test", "resources")
        val absolutePath: String = resourceDirectory.toFile().absolutePath
        element!!.click()

        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(StringSelection(absolutePath + filePath), null)
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    private fun createDriver(driverName: String): WebDriver {
        return initChrome()
    }

    private fun initChrome(): WebDriver {
        val options = ChromeOptions()
        val logPrefs = LoggingPreferences()
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL)
        options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs)
        val prefs: MutableMap<String, Any> = HashMap()
        prefs["profile.default_content_setting_values.notifications"] = 2
        options.setExperimentalOption("prefs", prefs)
        val driver: WebDriver = ChromeDriver(options)
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver
    }
}