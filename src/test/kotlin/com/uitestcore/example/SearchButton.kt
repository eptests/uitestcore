package com.uitestcore.example

import com.uitestcore.containers.AbstractContainer
import com.uitestcore.elementobjects.Button
import org.openqa.selenium.support.FindBy

class SearchButton : AbstractContainer() {
    @FindBy(name="btnK")
    private val searchBtn: Button? = null

    fun clickSearch() {
        searchBtn!!.click()
    }

    fun clickLucky() {

    }
}
