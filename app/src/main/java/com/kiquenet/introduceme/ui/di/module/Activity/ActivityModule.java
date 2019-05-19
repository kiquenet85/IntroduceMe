package com.kiquenet.introduceme.ui.di.module.Activity;

import android.app.Activity;
import android.content.Context;
import dagger.Module;
import dagger.Provides;

/**
 * @author n.diazgranados
 */
@Module
public class ActivityModule {

    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    Activity provideActivity() {
        return activity;
    }

    @Provides
    Context provideContext() {
        return activity;
    }
}
