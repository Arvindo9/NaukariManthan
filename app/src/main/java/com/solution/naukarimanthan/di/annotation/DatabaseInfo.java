package com.solution.naukarimanthan.di.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Author       : Arvindo Mondal
 * Created on   : 23-12-2018
 * Email        : arvindomondal@gmail.com
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface DatabaseInfo {
}
