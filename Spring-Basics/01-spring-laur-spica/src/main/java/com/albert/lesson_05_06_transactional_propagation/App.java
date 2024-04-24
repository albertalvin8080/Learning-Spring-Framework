package com.albert.lesson_05_06_transactional_propagation;

import com.albert.lesson_05_06_transactional_propagation.config.ProjectConfig;
import com.albert.lesson_05_06_transactional_propagation.domain.Product;
import com.albert.lesson_05_06_transactional_propagation.service.ProductService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        try(var context = new AnnotationConfigApplicationContext(ProjectConfig.class)) {
            final ProductService service = context.getBean(ProductService.class);

//            final Product product = new Product("Madoka Novel", 456);
//            service.save(product);
            service.saveTen();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
