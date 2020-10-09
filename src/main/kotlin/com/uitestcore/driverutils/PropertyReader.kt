package com.uitestcore.driverutils

import org.apache.commons.lang3.StringUtils
import java.io.IOException
import java.io.InputStream
import java.util.*

object PropertyReader {
    private var propertiesPath: String? = null

    @Volatile
    private var properties: Properties? = null
    private var inputStream: InputStream? = null
    val path: String?
        get() = if (StringUtils.isBlank(propertiesPath)) {
            ""
        } else {
            if (propertiesPath!![0] != '/') {
                propertiesPath = "/$propertiesPath"
            }
            propertiesPath
        }

    private fun readProperties(): Properties? {
        properties = Properties()
        try {
            inputStream = PropertyReader::class.java.getResourceAsStream(path!!)
            if (inputStream != null) {
                properties!!.load(inputStream)
            }
        } catch (var3: Exception) {
            try {
                if (inputStream != null) {
                    inputStream!!.close()
                }
            } catch (var2: IOException) {
                throw RuntimeException(var2.message)
            }
        }
        return properties
    }

    private fun loadProperties(): Properties? {
        return if (properties != null) properties else readProperties()
    }

    fun getProperties(path: String?): Properties? {
        propertiesPath = path
        return readProperties()
    }

    fun getProperty(propertyName: String?): String {
        return loadProperties()!!.getProperty(propertyName)
    }
}