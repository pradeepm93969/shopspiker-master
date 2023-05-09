package com.shopspiker.auth.web.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface BasicAuth {

    public String permission() default "All Roles";

}
