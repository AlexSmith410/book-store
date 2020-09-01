package com.geekbrains.book.store.consumers;

import com.geekbrains.book.store.entities.Order;
import com.geekbrains.book.store.services.OrdersService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SimpleOrdersReceiver {
    private OrdersService ordersService;

    public void receiveMessage(byte[] message) {
        Long orderId = Long.parseLong(new String(message));
        System.out.println("[*] Заказ №" + orderId.toString() + " готов!");
        Order order = ordersService.findById(orderId);
        order.setStatus(Order.Status.READY);
        ordersService.saveOrder(order);
    }
}
