package com.solution.naukarimanthan.di.component;


import android.app.Application;

import com.solution.naukarimanthan.base.BaseApplication;
import com.solution.naukarimanthan.di.builder.ActivityBuilder;
import com.solution.naukarimanthan.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Author       : Arvindo Mondal
 * Created on   : 23-12-2018
 * Email        : arvindomondal@gmail.com
 */
@Singleton
@Component(
        modules = {
            AndroidInjectionModule.class,
            AppModule.class,
            ActivityBuilder.class
    }
)
public interface AppComponent {

    void inject(BaseApplication app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
