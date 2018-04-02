package com.xuhj.library.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义限定符
 *
 * @author xuhj
 */
@Scope
@Documented
@Retention(RUNTIME)
public @interface FragmentScope {
}
