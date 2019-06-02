package com.kiquenet.introduceme.di.scope

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Qualifier

/**
 * @author n.diazgranados
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
annotation class ApplicationContext