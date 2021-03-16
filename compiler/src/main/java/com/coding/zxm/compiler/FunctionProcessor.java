package com.coding.zxm.compiler;

import com.coding.zxm.annotation.Function;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * Created by ZhangXinmin on 2021/02/20.
 * Copyright (c) 2/20/21 . All rights reserved.
 */
@AutoService(Function.class)
public class FunctionProcessor extends AbstractProcessor {

    private Filer mFiler; //文件相关的辅助类
    @SuppressWarnings("FieldCanBeLocal") private Elements mElementUtils; //元素相关的辅助类
    private Messager mMessager; //日志相关的辅助类

    private boolean mIsFileCreated = false;

    private final String mClassName = "FunctionContainer";
    private final String mPackageName = "com.example.androidutils.manager";

    ClassName mMapName = ClassName.get("java.util", "Map");
    ClassName mHashMapName = ClassName.get("java.util", "HashMap");
    ClassName mItemDescName = ClassName.get("com.example.androidutils.model", "FuncItemDescription");
    ClassName mBaseFragmentName = ClassName.get("com.example.androidutils.base", "BaseFragment");
    TypeName mBaseFragmentClassName = ParameterizedTypeName.get(ClassName.get(Class.class),
            WildcardTypeName.subtypeOf(mBaseFragmentName));
    TypeName mMapFieldTypeName = ParameterizedTypeName.get(mMapName,
            mBaseFragmentClassName, mItemDescName);

    ClassName mFuncContainerName = ClassName.get(mPackageName, mClassName);

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFiler = processingEnv.getFiler();
        mElementUtils = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();
        mIsFileCreated = false;
        System.out.println("init()");
    }

    /**
     * @return 指定哪些注解应该被注解处理器注册
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        System.out.println("getSupportedAnnotationTypes()");
        Set<String> types = new LinkedHashSet<>();
        types.add(Function.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println("process()");
        if (mIsFileCreated) {
            return true;
        }
        mIsFileCreated = true;
        TypeSpec.Builder widgetContainerBuilder = TypeSpec.classBuilder(mFuncContainerName);

        FieldSpec instanceField = FieldSpec.builder(mFuncContainerName, "sInstance")
                .addModifiers(Modifier.PRIVATE)
                .addModifiers(Modifier.STATIC)
                .initializer("new $T()", mFuncContainerName)
                .build();

        FieldSpec mapField = FieldSpec.builder(mMapFieldTypeName, "mWidgets")
                .addModifiers(Modifier.PRIVATE)
                .build();

        MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .addStatement("mWidgets = new $T<>()", mHashMapName);

        for (Element element : roundEnvironment.getElementsAnnotatedWith(Function.class)) {
            if (element instanceof TypeElement) {
                TypeElement classElement = (TypeElement) element;
                ClassName elementName = ClassName.get(classElement);
                Function widget = classElement.getAnnotation(Function.class);
                String name = null;
                // http://www.programcreek.com/java-api-examples/index.php?api=javax.lang.model.type.TypeMirror
                // https://blog.retep.org/2009/02/13/getting-class-values-from-annotations-in-an-annotationprocessor/
                try {
                    widget.functionClass();
                } catch (MirroredTypeException mte) {
                    TypeMirror nameMirror = mte.getTypeMirror();
                    if (nameMirror.getKind() == TypeKind.DECLARED) {
                        name = ((DeclaredType) nameMirror).asElement().getSimpleName().toString();
                    }
                }
                if (name == null && widget.funcName().length() > 0) {
                    name = widget.funcName();
                }

                if (name == null || name.length() == 0) {
                    error("please provide widgetClass or name");
                }
                constructorBuilder.addStatement("mWidgets.put($T.class, new $T($T.class, $S, $L, $S))",
                        elementName,
                        mItemDescName,
                        elementName,
                        name,
                        widget.funcIconRes(),
                        widget.docUrl());
            }

        }
        MethodSpec constructorMethod = constructorBuilder.build();

        MethodSpec instanceMethod = MethodSpec.methodBuilder("getInstance")
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.STATIC)
                .returns(mFuncContainerName)
                .addStatement("return sInstance")
                .build();

        MethodSpec getMethod = MethodSpec.methodBuilder("get")
                .addModifiers(Modifier.PUBLIC)
                .returns(mItemDescName)
                .addParameter(mBaseFragmentClassName, "fragment")
                .addStatement("return mWidgets.get($L)", "fragment")
                .build();

        try {
            widgetContainerBuilder
                    .addField(instanceField)
                    .addField(mapField)
                    .addMethod(constructorMethod)
                    .addMethod(instanceMethod)
                    .addMethod(getMethod);
            JavaFile.builder(mPackageName, widgetContainerBuilder.build()).build().writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void error(String msg, Object... args) {
        System.out.println("error()");
        mMessager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args));
    }

    private void info(String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, String.format(msg, args));
    }
}
