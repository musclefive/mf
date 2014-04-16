package com.mf.dal.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by shixin on 3/11/14.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLControl {
    boolean applyToAllTables() default false;

    boolean useSlave() default false;

    boolean returnAotuGeneratedId() default false;
}