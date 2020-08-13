package com.geekbrains.book.store.controllers;

import com.geekbrains.book.store.beans.Cart;
import com.geekbrains.book.store.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {
    private BookService bookService;
    private Cart cart;

    @GetMapping
    public String showCartPage(Model model) {
        return "cart";
    }

    @GetMapping("/remove/{bookId}")
    public void removeBookFromCartById(@PathVariable Long bookId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        cart.removeByBookId(bookId);
        response.sendRedirect(request.getHeader("referer"));
    }

    @GetMapping("/increase/{bookId}")
    public void addBookToCartById(@PathVariable Long bookId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        cart.increase(bookService.findById(bookId));
        response.sendRedirect(request.getHeader("referer"));
    }

    @GetMapping("/decrease/{bookId}")
    public void decrementBookToCartById(@PathVariable Long bookId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        cart.decrease(bookService.findById(bookId));
        response.sendRedirect(request.getHeader("referer"));
    }
}
