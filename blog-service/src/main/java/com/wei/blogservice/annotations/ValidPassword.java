package com.wei.blogservice.annotations;

import com.wei.blogservice.validator.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author 凌雪
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {PasswordValidator.class}) // 指定校验器
public @interface ValidPassword {
    String message() default "密码必须包含大小写";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
