package com.geekbrains.book.store.controllers;

import com.geekbrains.book.store.beans.Cart;
import com.geekbrains.book.store.entities.Order;
import com.geekbrains.book.store.entities.User;
import com.geekbrains.book.store.services.OrdersService;
import com.geekbrains.book.store.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/orders")
@AllArgsConstructor
public class OrdersController {
    private UserService usersService;
    private OrdersService ordersService;
    private Cart cart;

    @GetMapping("/create")
    public String confirmOrder(Principal principal, Model model) {
       User user = usersService.findByUsername(principal.getName()).get();
        Order order = new Order(user, cart);
        order = ordersService.saveOrder(order);
        model.addAttribute("order", order);
        return "order_confirm";
    }
}
