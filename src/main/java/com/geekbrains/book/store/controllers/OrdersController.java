package com.geekbrains.book.store.controllers;

import com.geekbrains.book.store.beans.Cart;
import com.geekbrains.book.store.consumers.SimpleOrdersReceiver;
import com.geekbrains.book.store.entities.Order;
import com.geekbrains.book.store.entities.User;
import com.geekbrains.book.store.services.OrdersService;
import com.geekbrains.book.store.services.UserService;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/orders")
@AllArgsConstructor
public class OrdersController {
    private static final String READY_ORDERS_QUEUE = "readyOrdersQueue";
    private static final String EXCHANGE_ORDERS = "OrdersExchanger";

    private UserService usersService;
    private OrdersService ordersService;
    private Cart cart;
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/create")
    public String confirmOrder(Principal principal, Model model) throws Exception {
        User user = usersService.findByUsername(principal.getName()).get();
        Order order = new Order(user, cart);
        order = ordersService.saveOrder(order);
        rabbitTemplate.convertAndSend(EXCHANGE_ORDERS, null, order.getId().toString());
        model.addAttribute("order", order);
        return "order_confirm";
    }

    @Bean
    public SimpleMessageListenerContainer containerForReadyOrdersQueue(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(READY_ORDERS_QUEUE);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(SimpleOrdersReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
}
