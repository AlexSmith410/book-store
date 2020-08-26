package com.geekbrains.book.store.controllers;

import com.geekbrains.book.store.beans.CountingAspect;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/statistic")
@AllArgsConstructor
public class CountingAspectController {
    CountingAspect aspect;

    @GetMapping
    public String countAllMethodCalls(Model model) {
        model.addAttribute("bookServiceCount", aspect.getBookServiceCount());
        model.addAttribute("ordersServiceCount", aspect.getOrdersServiceCount());
        model.addAttribute("userServiceCount", aspect.getUserServiceCount());
        return "statistic-page";
    }
}
