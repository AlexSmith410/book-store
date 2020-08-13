package com.geekbrains.book.store.repositories;

import com.geekbrains.book.store.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Long> {
}
