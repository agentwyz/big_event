package com.fnmain.anno;


import com.fnmain.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Documented
@Target({ElementType.FIELD})
@Constraint(validatedBy = { StateValidation.class }) //指定提供校验规则的类
@Retention(RUNTIME)
public @interface State {
    //表示提供校验失败后的提示信息
    String message() default "state参数的值只能是已发布或者草稿";

    //指定分组, 表示施加到那个类上去
    Class<?>[] groups() default {};

    //负载, 获取State注解的附加信息
    Class<? extends Payload>[] payload() default {};
}
