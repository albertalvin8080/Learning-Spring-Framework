package org.albert.standardexpressions.controller;

import org.albert.standardexpressions.entity.Book;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;

@Controller
public class BookController
{
    @GetMapping(path = "/variable-expression")
    public String variableExpression(Model model)
    {
        var book = Book.builder()
                .id(99L)
                .author("Franz Bonaparta")
                .title("Monster")
                .price(BigDecimal.valueOf(23.99))
                .build();
        model.addAttribute("book", book);
        return "variable-expression";
    }

    @GetMapping(path = "/selection-expression")
    public String selectionExpression(Model model)
    {
        var book = Book.builder()
                .id(99L)
                .author("Emil Sebe")
                .title("Kaibutsu")
                .price(BigDecimal.valueOf(5.0))
                .build();
        model.addAttribute("book", book);
        return "selection-expression";
    }

    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String save(Book book)
    {
        System.out.println(book);
        return "selection-expression";
    }

    @GetMapping(path = "/message-expression")
    public String messageExpression()
    {
        return "message-expression";
    }
}
