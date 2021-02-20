package com.coding.zxm.compiler;

import com.coding.zxm.annotation.Function;
import com.google.auto.service.AutoService;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * Created by ZhangXinmin on 2021/02/20.
 * Copyright (c) 2/20/21 . All rights reserved.
 */
@AutoService(Function.class)
public class FunctionProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        return false;
    }
}
