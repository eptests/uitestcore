package com.uitestcore.example

import com.uitestcore.containers.AbstractContainer
import com.uitestcore.elementobjects.Button
import com.uitestcore.elementobjects.Link
import com.uitestcore.elementobjects.Text
import org.openqa.selenium.By
import org.openqa.selenium.support.FindBy

class SearchResultBlock : AbstractContainer() {
    @FindBy(css=".rc h3")
    private val header: Text? = null
    @FindBy(css=".rc a")
    private val link: Link? = null

    fun clickLink() {
        link!!.click()
    }

    fun getText(): String {
        this.findElement(By.cssSelector(".rc h3"))
        return header.toString()
    }
}
