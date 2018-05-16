//package com.xuhj.java.processor.temp;
//
//import com.xuhj.java.processor.Template;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import javax.annotation.processing.AbstractProcessor;
//import javax.annotation.processing.ProcessingEnvironment;
//import javax.annotation.processing.RoundEnvironment;
//import javax.annotation.processing.SupportedAnnotationTypes;
//import javax.annotation.processing.SupportedSourceVersion;
//import javax.lang.model.SourceVersion;
//import javax.lang.model.element.TypeElement;
//
//
//@SupportedAnnotationTypes("com.xuhj.java.processor.Template")
//@SupportedSourceVersion(SourceVersion.RELEASE_7)
//public class MyTemplateProcessor extends AbstractProcessor {
//
//    /**
//     * 该处理器支持的注解类，在这里添加自定义注解
//     *
//     * 可以用注解@SupportedAnnotationTypes("com.xuhj.java.processor.Template")
//     */
//    @Override
//    public Set<String> getSupportedAnnotationTypes() {
//        Set<String> set = new HashSet<>();
//        // 添加自定义注解
//        set.add(Template.class.getCanonicalName());
//        return set;
//    }
//
//    /**
//     * 该处理器支持的JDK版本，例如：SourceVersion.RELEASE_7
//     * 一般返回SourceVersion.latestSupported()
//     *
//     * 可以用注解@SupportedSourceVersion(SourceVersion.RELEASE_7)
//     */
//    @Override
//    public SourceVersion getSupportedSourceVersion() {
//        return SourceVersion.latestSupported();
//    }
//
//    /**
//     * 该初始化方法会被注解处理工具调用，并传入参数processingEnvironment，
//     * 该参数提供了很多有用的工具类，例如Elements、Types、Filter等等
//     */
//    @Override
//    public synchronized void init(ProcessingEnvironment processingEnvironment) {
//        super.init(processingEnvironment);
//    }
//
//    @Override
//    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
//        return false;
//    }
//}
