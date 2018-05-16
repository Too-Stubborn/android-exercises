package com.xuhj.java.processor;

import com.google.auto.service.AutoService;

import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
@SupportedAnnotationTypes("com.xuhj.java.processor.Template")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class MyTemplateProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        StringBuilder sb = new StringBuilder("package com.xuhj.java.processor;\n")
                .append("public class GeneratedTemplate{\n")
                .append("\tpublic String getMessage(){\n")
                .append("\t\treturn \"");
        for (Element element : roundEnvironment.getElementsAnnotatedWith(Template.class)) {
            String objectType = element.getSimpleName().toString();
            sb.append(objectType).append(" say hello!\\n");
        }
        sb.append("\";\n")
                .append("\t}\n")
                .append("}\n");
        try {
            JavaFileObject source = processingEnv.getFiler()
                    .createSourceFile("com.xuhj.java.processor.generated.GeneratedTemplate");
            Writer writer = source.openWriter();
            writer.write(sb.toString());
            writer.flush();
            writer.close();
        } catch (Exception e) {

        }
        return true;
    }

    /**
     * 该处理器支持的所有注解类集合，在这里可以添加自定义注解
     *
     * 可以用注解@SupportedAnnotationTypes("com.xuhj.java.processor.Template")
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        // 添加自定义注解
        set.add(Template.class.getCanonicalName());
        return set;
    }

    /**
     * 该处理器支持的JDK版本，例如：SourceVersion.RELEASE_7
     * 一般返回SourceVersion.latestSupported()
     *
     * 可以用注解@SupportedSourceVersion(SourceVersion.RELEASE_7)
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * 该初始化方法会被注解处理工具调用，并传入参数processingEnvironment，
     * 该参数提供了很多有用的工具类，例如Elements、Types、Filter等等
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
    }


}
