package com.geekbrains.book.store.controllers;

import com.geekbrains.book.store.entities.Book;
import com.geekbrains.book.store.repositories.BookRepository;
import com.geekbrains.book.store.repositories.specifications.BookSpecifications;
import com.geekbrains.book.store.services.BookService;
import com.geekbrains.book.store.utils.BookFilter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Controller
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {
    private BookService bookService;
    private BookRepository bookRepository;

    @Autowired
    public void setBookService(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping
    public String showAllBooks(Model model,
                               @RequestParam(name = "page", defaultValue = "1") Integer pageIndex,
                               @RequestParam (required = false) MultiValueMap<String, String> params
    ) {
        BookFilter bookFilter = new BookFilter(params);
        Page<Book> page = bookService.findAll(bookFilter.getSpec(), pageIndex - 1, 5);
        int pageCount = page.getTotalPages();
        List<Integer> pages = IntStream.rangeClosed(1, pageCount).boxed().collect(Collectors.toList());
        model.addAttribute("pages", pages);
        model.addAttribute("books", page.getContent());
        model.addAttribute("page", page);
        model.addAttribute("requestParameters", bookFilter.getFilterParams());
//        model.addAttribute("current", bookRepository.findAll(PageRequest.of(pageIndex, pageCount)));
        model.addAttribute("genres", Book.Genre.values());
        return "store-page";
    }

}
