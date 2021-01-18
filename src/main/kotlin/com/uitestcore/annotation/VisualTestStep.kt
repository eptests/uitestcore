package com.uitestcore.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class VisualTestStep(val screenName: String) {
}