package com.albert.lesson_03_jdbctemplate;

import com.albert.lesson_03_jdbctemplate.config.DataSourceConfig;
import com.albert.lesson_03_jdbctemplate.domain.Product;
import com.albert.lesson_03_jdbctemplate.service.ProductService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        try(var context = new AnnotationConfigApplicationContext(DataSourceConfig.class)) {

            final ProductService service = context.getBean(ProductService.class);
            service.save(new Product("One Piece", 1095d));
            service.listAll().forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
