package com.uitestcore.elementobjects

interface Link : Element {
    fun click()
    fun text() : String
    fun href() : String
}
