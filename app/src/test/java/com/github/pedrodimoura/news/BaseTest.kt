package com.github.pedrodimoura.news

import org.junit.After
import org.junit.Before
import org.koin.test.KoinTest

abstract class BaseTest : KoinTest {

    @Before
    abstract fun setup()

    @After
    abstract fun tearDown()

}