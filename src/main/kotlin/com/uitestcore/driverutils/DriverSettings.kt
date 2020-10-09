package com.uitestcore.driverutils

import java.util.*

object DriverSettings {
    private var TEST_PROPERTIES_PATH = "test.properties"
    var URL: String? = null
    var BROWSER: String = "chrome"
    var LOG_LEVEL: String? = null
    var PARAMS: Array<String> = arrayOf()
    var IMPLICIT_WAIT: Long = 10
    var DOMAIN: String = ""

    /**
     * Initialize settings from property file
     */
    @Synchronized
    fun initFromProfile() {
        val properties: Properties? = PropertyReader.getProperties(TEST_PROPERTIES_PATH)
        //set properties from array
        BROWSER = properties!!.getProperty("driver")
        URL = properties.getProperty("url")
        LOG_LEVEL = properties.getProperty("log.level")
        System.getProperty("params")?.let {
            PARAMS = System.getProperty("params").split(",").toTypedArray()
        }
        IMPLICIT_WAIT = properties.getProperty("timeout.wait.element").toLong()
        DOMAIN = properties.getProperty("domain")
    }
}