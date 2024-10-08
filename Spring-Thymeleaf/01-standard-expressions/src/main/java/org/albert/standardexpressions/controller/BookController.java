package org.albert.standardexpressions.controller;

import org.albert.standardexpressions.entity.Book;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

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

    @GetMapping(path = "/link-expression")
    public String linkExpression(@RequestParam(name = "id", required = false) Long id, Model model)
    {
        System.out.println(id);
        model.addAttribute("myid", id);
        return "link-expression";
    }

    @GetMapping(path = "/link-expression/{id}")
    public String linkExpressionId(@PathVariable("id") Long id)
    {
        System.out.print("Path variable: ");
        System.out.println(id);
        return "redirect:/link-expression?id="+id;
    }

    @GetMapping(path = "/fragment-expression")
    public String fragmentExpressionId()
    {
        return "fragment-expression";
    }

    @GetMapping(path = "/each-if-unless")
    public String eachIfUnless(Model model)
    {
        final List<Book> books = List.of(
                Book.builder().price(BigDecimal.valueOf(30.3)).author("Franz Bonaparta").id(1L).title("Monster").build(),
                Book.builder().price(BigDecimal.valueOf(321.0)).author("Emil Sebe").id(2L).title("Monster").build(),
                Book.builder().price(BigDecimal.valueOf(420.99)).author("Hemuth Voss").id(3L).title("Monster").build(),
                Book.builder().price(BigDecimal.valueOf(21.0)).author("Klaus Poppe").id(4L).title("Monster").build(),
                Book.builder().price(BigDecimal.valueOf(10.1)).author("Detestatio Sacrorum").id(5L).title("High Wills").build()
        );
        model.addAttribute("books", books);
        return "each-if-unless";
    }
}
