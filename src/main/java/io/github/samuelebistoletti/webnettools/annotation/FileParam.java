package io.github.samuelebistoletti.webnettools.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FileParam {
    String displayName();

    String description();

    String param();

    String group() default "Options";

    int maxSize() default 1024*1024;

    String accept() default "*.*";

    ParameterType paramType() default ParameterType.SPACE;
}