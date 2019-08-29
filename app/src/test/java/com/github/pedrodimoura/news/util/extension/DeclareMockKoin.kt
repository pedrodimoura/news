package com.github.pedrodimoura.news.util.extension

import org.koin.core.KoinApplication
import org.koin.core.definition.BeanDefinition
import org.koin.core.error.NoBeanDefFoundException
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import org.koin.ext.getFullName
import org.koin.test.mock.createMockedDefinition
import kotlin.reflect.KClass

inline fun <reified T : Any> Scope.declareMock(
    qualifier: Qualifier? = null,
    noinline stubbing: (T.() -> Unit)? = null
): T {
    val clazz = T::class

    val foundDefinition: BeanDefinition<T> = getDefinition(clazz, this, qualifier)

    declareMockedDefinition(foundDefinition, stubbing)

    return get()
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> getDefinition(
    clazz: KClass<T>,
    scope: Scope,
    qualifier: Qualifier?
): BeanDefinition<T> {
    KoinApplication.logger.info("declare mock for '${clazz.getFullName()}'")

    return scope.beanRegistry.findDefinition(qualifier, clazz) as BeanDefinition<T>?
        ?: throw NoBeanDefFoundException("No definition found for qualifier='$qualifier' & class='$clazz'")
}

inline fun <reified T : Any> Scope.declareMockedDefinition(
    foundDefinition: BeanDefinition<T>,
    noinline stubbing: (T.() -> Unit)?
) {
    val definition: BeanDefinition<T> = foundDefinition.createMockedDefinition(stubbing)
    beanRegistry.saveDefinition(definition)
}