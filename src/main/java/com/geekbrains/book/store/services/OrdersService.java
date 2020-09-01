package com.geekbrains.book.store.services;


import com.geekbrains.book.store.entities.Order;
import com.geekbrains.book.store.exceptions.ResourceNotFoundException;
import com.geekbrains.book.store.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdersService {
    private OrdersRepository ordersRepository;

    @Autowired
    public void setOrdersRepository(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public Order saveOrder(Order order) {
        order.setStatus(Order.Status.INPROCESSING);
        return ordersRepository.save(order);
    }

    public Order findById(Long id){
        return ordersRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order with id: " + id + " not found"));
    }
}
