package com.baomidou.dynamic.datasource.annotation;

import java.lang.annotation.*;

/**
 * 从数据源
 *
 * @author ljy
 * @date 2024/1/31 11:15
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DS("EpcCenter")
public @interface EpcCenter {
}
