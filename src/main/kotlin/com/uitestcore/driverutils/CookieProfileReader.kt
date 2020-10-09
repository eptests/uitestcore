package com.uitestcore.driverutils

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.StringUtils
import org.openqa.selenium.Cookie
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale


object CookieProfileReader {
    private var propertiesPath: String? = null
    private var sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    @Volatile
    private var inputStream: InputStream? = null
    private val path: String?
        get() = if (StringUtils.isBlank(propertiesPath)) {
            ""
        } else {
            if (propertiesPath!![0] != '/') {
                propertiesPath = "/$propertiesPath"
            }
            propertiesPath
        }

    fun readProfile(profileName: String): List<Cookie> {
        val cookiesArray = mutableListOf<Cookie>()
        propertiesPath = "$profileName.json"
        try {
            inputStream = CookieProfileReader::class.java.getResourceAsStream(path!!)
            if (inputStream != null) {
                val jsonObject = JsonParser.parseString(IOUtils.toString(inputStream, "UTF-8")).asJsonObject
                for (entry: Map.Entry<String,JsonElement> in jsonObject.entrySet()) {
                    val element = entry.value.asJsonObject
                    val cookie: Cookie = Cookie.Builder(entry.key, element.get("value").asString)
                            .domain(if (element.has("domain")) element.get("domain").asString else DriverSettings.DOMAIN)
                            .expiresOn(if (element.has("date")) sdf.parse(element.get("date").asString) else GregorianCalendar(3000, 10, 3).time)
                            .isHttpOnly(if (element.has("isHttpOnly")) element.get("isHttpOnly").asBoolean else true)
                            .isSecure(if (element.has("isSecure")) element.get("isSecure").asBoolean else false)
                            .path(if (element.has("path")) element.get("path").asString else "/")
                            .build()
                    cookiesArray.add(cookie)
                }
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
        return cookiesArray
    }
}