package com.albert.annotationbasedconfig.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@EnableWebMvc // you need this annotation if you use 'getRootConfigClasses()' instead of 'getServletConfigClasses()' inside the "DispatcherServletInitializer" class.
@Configuration
@ComponentScan(basePackages = { "com.albert.annotationbasedconfig" })
// the name of this class could be other, like "DispatcherServletConfig".
// "WebMvcConfigurerAdapter" is deprecated. Use "WebMvcConfigurer" instead.
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
//        final InternalResourceViewResolver viewResolver =
//                new InternalResourceViewResolver();
//        viewResolver.setPrefix("/resources/");
//        viewResolver.setSuffix(".jsp");

        return new InternalResourceViewResolver("/resources/", ".jsp");
    }
}
