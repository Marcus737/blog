package com.wei.blogservice.annotations;

import com.wei.blogservice.queue.consumer.Consumer;
import com.wei.blogservice.queue.consumer.SqlConsumer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 凌雪
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogController {

    Class<? extends Consumer> consumer() default SqlConsumer.class;
}
