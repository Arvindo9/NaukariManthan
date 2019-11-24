package com.solution.naukarimanthan.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.solution.naukarimanthan.data.AppDataManager;
import com.solution.naukarimanthan.data.DataManager;
import com.solution.naukarimanthan.data.local.db.AppDatabase;
import com.solution.naukarimanthan.data.local.db.Database;
import com.solution.naukarimanthan.data.local.db.DatabaseService;
import com.solution.naukarimanthan.data.local.prefs.AppPreferences;
import com.solution.naukarimanthan.data.local.prefs.PreferencesService;
import com.solution.naukarimanthan.data.remote.APIService;
import com.solution.naukarimanthan.data.remote.APIs;
import com.solution.naukarimanthan.data.remote.ApiConfig;
import com.solution.naukarimanthan.di.annotation.ApiService;
import com.solution.naukarimanthan.di.annotation.DatabaseInfo;
import com.solution.naukarimanthan.di.annotation.PreferenceInfo;
import com.solution.naukarimanthan.utils.AppConstants;
import com.solution.naukarimanthan.utils.rx.AppSchedulerProvider;
import com.solution.naukarimanthan.utils.rx.SchedulerProvider;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.solution.naukarimanthan.BuildConfig.BASE_URL_UTILS;

/**
 * Author       : Arvindo Mondal
 * Created on   : 23-12-2018
 * Email        : arvindomondal@gmail.com
 */
@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }


    //Preferences------------------------------------------------

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    PreferencesService providePreferencesService(AppPreferences appPreferences) {
        return appPreferences;
    }
    //Database----------------------------------------------------

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@DatabaseInfo String dbName, Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, dbName).fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    DatabaseService provideDbHelper(Database database) {
        return database;
    }

    //Network module----------------------------------------------------

    @Provides
    @Singleton
    APIService provideAPIService(APIs APIs) {
        return APIs;
    }

    @Provides
    @Singleton
    @ApiService
    APIs provideAPIHeader(@Named("APP") Retrofit retrofit, @Named("IPAddress") Retrofit retrofitIp,
                          @Named("Adgebra") Retrofit adgebra, @Named("UTILS") Retrofit retrofitUtils) {
        return new APIs(retrofit, retrofitIp, adgebra, retrofitUtils);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Provides
    @Singleton
    Cache provideHttpCache(Application application) {
//        int cacheSize = 10 * 1024 * 1024;
        return new Cache(application.getCacheDir(), 10485760);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient(Cache cache) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient
                .Builder()
                .cache(cache)
                .addInterceptor(interceptor)
                .readTimeout(600, TimeUnit.SECONDS)
                .connectTimeout(600, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    @Named("APP")
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(ApiConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    @Named("UTILS")
    Retrofit provideRetrofitUtils(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL_UTILS)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    @Named("IPAddress")
    Retrofit provideRetrofitForIp(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(ApiConfig.BASE_URL_IP)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    @Named("Adgebra")
    Retrofit provideRetrofitAdgebra(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(ApiConfig.BASE_URL_ADGEBRA)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(okHttpClient)
                .build();
    }

    //Additional task----------------------------------------------



    //------------------------end-----------------------------------
}
