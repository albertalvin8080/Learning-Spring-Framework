package org.albert.integrationtestscustommockuser.security;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = CustomTestSecurityContextFactory.class)
public @interface WithCustomMockUser
{
    String priority() default "HIGH";
}
