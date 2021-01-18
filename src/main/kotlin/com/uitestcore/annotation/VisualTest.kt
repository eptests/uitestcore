package com.uitestcore.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class VisualTest(val screenName : String) {

}