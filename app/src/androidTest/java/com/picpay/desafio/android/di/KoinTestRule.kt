package com.picpay.desafio.android.di

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.test.KoinTest

class KoinTestRule : TestRule, KoinTest {

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                try {
                    setupKoin()
                    base.evaluate()
                } finally {
                    cleanupKoin()
                }
            }
        }
    }

    private fun setupKoin() {
        if (GlobalContext.getOrNull() == null) {
            startKoin {
                modules(TestModules.testModule)
            }
        } else {
            TestModules.loadModules()
        }
    }

    private fun cleanupKoin() {
        TestModules.unloadModules()
    }
}
