package com.uitestcore.example

import org.testng.annotations.Test
import com.uitestcore.driverutils.Driver

class TestMainPage : TestInit() {

    @Test(priority=1)
    fun test() {
        Driver.openPage()
    }

}