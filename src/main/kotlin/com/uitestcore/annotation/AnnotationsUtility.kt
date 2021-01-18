package com.uitestcore.annotation

import com.google.auto.service.AutoService
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

@AutoService(Processor::class) // For registering the service
@SupportedSourceVersion(SourceVersion.RELEASE_8) // to support Java 8
class AnnotationUtility: AbstractProcessor() {
    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        roundEnv!!.getElementsAnnotatedWith(VisualTest::class.java).forEach { methodElement ->
            if (methodElement.kind != ElementKind.METHOD) {
                processingEnv.messager.printMessage(Diagnostic.Kind.WARNING, "Can only be applied to functions,  element: $methodElement ")
                return false
            }
        }
        return true
    }
}