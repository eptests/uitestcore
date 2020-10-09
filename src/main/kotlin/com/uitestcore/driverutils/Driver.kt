package com.uitestcore.driverutils

/*import java.nio.file.Path
import java.nio.file.Paths
import java.awt.Robot
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.awt.event.KeyEvent*/

import com.uitestcore.driverutils.DriverSettings.BROWSER
import com.uitestcore.driverutils.DriverSettings.IMPLICIT_WAIT
import com.uitestcore.driverutils.DriverSettings.PARAMS
import com.uitestcore.driverutils.DriverSettings.URL
import com.uitestcore.driverutils.DriverSettings.DOMAIN
import io.github.bonigarcia.wdm.WebDriverManager
import io.qameta.allure.Step
import org.openqa.selenium.*
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.logging.LogType
import org.openqa.selenium.logging.LoggingPreferences
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.support.ui.ExpectedConditions.urlToBe
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.logging.Level
import kotlin.collections.HashMap
import kotlin.reflect.KClass

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
        return when(driverName)
        {
            "chrome" -> initChrome()
            //"ff" -> return initFF(params)
            else -> throw Exception("Browser name is incorrect!")
        }
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
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS)
        return driver
    }

    fun get(): WebDriver {
        return instance
    }


    fun maximize() {
        instance.manage().window().maximize()
    }

    @Step("Open base page")
    fun openPage() {
        instance.get(baseUrl)
    }

    @Step("Open {path} page")
    fun openPage(path: String) {
        instance.get(baseUrl+path)
    }

    @Step("Reload page")
    fun reloadPage() {
        instance.navigate().refresh()
    }

    @Step("Get element {id}")
    fun getElementById(id: String): WebElement{
        return instance.findElement(By.id(id))
    }

    @Step("Get element {css}")
    fun getElementByCss(css: String): WebElement{
        return instance.findElement(By.cssSelector(css))
    }

    @Step("Get element {xpath}")
    fun getElementByXpath(xpath: String): WebElement{
        return instance.findElement(By.xpath(xpath))
    }

    @Step("get element {name}")
    fun getElementByName(name: String): WebElement{
        return instance.findElement(By.name(name))
    }

    @Step("Get element with text {text}")
    fun getElementByText(text: String): WebElement{
        return instance.findElement(By.linkText(text))
    }

    @Step("Get element {by}")
    fun findElement(by: By): WebElement {
        return instance.findElement(by)
    }

    @Step("Get elements {by}")
    fun findElements(by: By): List<WebElement> {
        return instance.findElements(by)
    }

    @Step("Get element with class")
    fun <T : Any> findDecoratedElement(clazz: KClass<T>, by: By): T {
        return WebElementDecorator().decorate(clazz, this.findElement(by))
    }

    @Step("Get elements with class")
    fun <T : Any> findDecoratedElements(clazz: KClass<T>, by: By): List<T> {
        return WebElementDecorator().decorate(clazz, this.findElements(by))
    }

    @Step("Scroll to element {element}")
    fun scrollToElement(element: WebElement) {
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element)
    }

    @Step("Browser pause {time}")
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

    @Step("Wait to redirect to url {url}")
    fun waitToRedirection(url: String) {
        Wait.until(urlToBe(url))
    }

    @Step("Set cookies")
    fun setCookies(cookiesList : List<Cookie>) {
        cookiesList.forEach {
            this.instance.manage().addCookie(it)
        }
    }

    @Step("Set cookies")
    fun setCookies(cookie : Cookie) {
        this.instance.manage().addCookie(cookie)
    }

    @Step("Set cookies")
    fun setCookies(cookieValue : Pair<String, String>) {
        val cookie: Cookie = Cookie.Builder(cookieValue.first, cookieValue.second)
                .domain(DOMAIN)
                .expiresOn(GregorianCalendar(3000, 10, 3).time)
                .isHttpOnly(true)
                .isSecure(false)
                .path("/")
                .build()
        this.instance.manage().addCookie(cookie)
    }

    @Step("Get cookies")
    fun getCookies(): Set<Cookie>? {
        return this.instance.manage().cookies
    }

    @Step("Delete cookies")
    fun deleteCookies() {
        return this.instance.manage().deleteAllCookies()
    }
}