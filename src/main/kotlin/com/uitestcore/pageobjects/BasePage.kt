package com.uitestcore.pageobjects

import com.uitestcore.driverutils.Driver
import com.uitestcore.driverutils.ExtendedFieldDecorator
import org.openqa.selenium.support.PageFactory

open class BasePage {
    init {
        PageFactory.initElements(ExtendedFieldDecorator(Driver.get()), this)
    }
}