package com.uitestcore.example

import com.uitestcore.containers.AbstractContainer
import com.uitestcore.elementobjects.Button
import org.openqa.selenium.support.FindBy

class SearchButton : AbstractContainer() {
    @FindBy(css="[value='Поиск в Google']")
    private val searchBtn: Button? = null

    fun clickSearch() {
        searchBtn!!.click()
    }
}
