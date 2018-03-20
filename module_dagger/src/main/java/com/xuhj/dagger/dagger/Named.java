package com.xuhj.test.dagger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * @author xuhj
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface Named {
    String value() default "";
}
