package com.kiquenet.introduceme.ui.di.module;

import android.app.Application;
import android.content.Context;
import com.google.gson.Gson;
import com.kiquenet.introduceme.R;
import com.kiquenet.introduceme.ui.di.scope.ApplicationContext;
import com.kiquenet.introduceme.ui.network.NetworkManager;
import com.kiquenet.introduceme.ui.settings.Settings;
import com.kiquenet.introduceme.ui.util.FileUtil;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Provide application-level dependencies
 * @author n.diazgranados
 */
@Module
public class ApplicationModule {

    protected final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    Settings settings(@ApplicationContext Context context) {
        return new Gson().fromJson(FileUtil.INSTANCE.readFile(context, R.raw.default_config), Settings.class);
    }

    @Provides
    @Singleton
    NetworkManager provideNetworkManger(@ApplicationContext Context context, Settings settings) {
        return new NetworkManager(context, settings);
    }
}
