package com.kiquenet.introduceme.ui.di.scope

/**
 * A scoping annotation to permit objects whose lifetime should
 * conform to the life of the Activity to be memorised in the
 * correct component.
 * @author n.diazgranados
 */

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Scope

@Scope
@Retention(RetentionPolicy.RUNTIME)
annotation class PerActivity