package com.coding.zxm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ZhangXinmin on 2021/2/20.
 * Copyright (c) 2/20/21 . All rights reserved.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Function {
    Group group() default Group.Component;

    Class functionClass() default void.class;

    String funcName() default "";

    String docUrl() default "";

    int funcIconRes() default 0;
}
