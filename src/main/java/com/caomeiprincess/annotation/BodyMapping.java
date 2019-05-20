package com.caomeiprincess.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ResponseBody
@RequestMapping
@Inherited
public @interface BodyMapping {
    @AliasFor(
            annotation = RequestMapping.class
    )
    String value() default "";

    RequestMethod[] method() default {};
}

