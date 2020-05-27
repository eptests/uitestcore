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
import org.openqa.selenium.support.ui.ExpectedConditions.urlToBe
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.logging.Level

/*import java.nio.file.Path
import java.nio.file.Paths
import java.awt.Robot
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.awt.event.KeyEvent*/

import com.uitestcore.driverutils.DriverSettings.BROWSER
import com.uitestcore.driverutils.DriverSettings.PARAMS
import com.uitestcore.driverutils.DriverSettings.IMPLICIT_WAIT
import com.uitestcore.driverutils.DriverSettings.URL
import io.github.bonigarcia.wdm.WebDriverManager

object Driver {
    private lateinit var instance: WebDriver
    private lateinit var baseUrl: String
    private lateinit var jsExecutor: JavascriptExecutor
    //private lateinit var robot: Robot

    fun init(url: String) {
        baseUrl = url
        init()
    }

    fun init() {
        DriverSettings.initFromProfile()
        instance = createDriver(BROWSER)
        jsExecutor = instance as JavascriptExecutor
        //robot = Robot()
        if (!this::baseUrl.isInitialized) {
            baseUrl = URL!!
        }
    }

    private fun createDriver(driverName: String): WebDriver {
        var driver = when(driverName)
        {
            "chrome" -> initChrome()
            //"ff" -> return initFF(params)
            else -> throw Exception("Browser name is incorrect!")
        }
        return  driver
    }

    private fun initChrome(): WebDriver {
        WebDriverManager.chromedriver().setup()
        val options = ChromeOptions()
        val logPrefs = LoggingPreferences()
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL)
        options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs)
        PARAMS.forEach{
            options.addArguments(it)
        }
        val prefs: MutableMap<String, Any> = HashMap()
        prefs["profile.default_content_setting_values.notifications"] = 2
        options.setExperimentalOption("prefs", prefs)
        val driver: WebDriver = ChromeDriver(options)
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
        return driver
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

    fun <C> findDecoratedElement(clazz: Class<C>, by: By): Any? {
        return WebElementDecorator().decorate(clazz, this.findElement(by))
    }

    fun <C> findDecoratedElements(clazz: Class<C>, by: By): Any? {
        return WebElementDecorator().decorate(clazz, this.findElements(by))
    }

    fun scrollToElement(element: WebElement) {
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element)
    }

    fun pause(time: Long) {
        Thread.sleep(time * 1000)
    }

    //Doesn't work in headless mode
    /*fun uploadFile(element: WebElement, filePath: String) {
        val resourceDirectory: Path = Paths.get("src", "test", "resources")
        val absolutePath: String = resourceDirectory.toFile().absolutePath
        element!!.click()

        Toolkit.getDefaultToolkit().systemClipboard.setContents(StringSelection(absolutePath + filePath), null)
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }*/

    fun waitToRedirection(url: String) {
        Wait.until(urlToBe(url))
    }
}