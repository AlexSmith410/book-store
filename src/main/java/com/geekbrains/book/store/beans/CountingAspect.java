package com.geekbrains.book.store.beans;

import lombok.Getter;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Getter
@Component
@Aspect
public class CountingAspect {
    private int bookServiceCount, ordersServiceCount, userServiceCount;

    @After("execution(public * com.geekbrains.book.store.services.BookService.*(..))")
    public void afterBookService() {
        bookServiceCount++;
    }

    @After("execution(public * com.geekbrains.book.store.services.OrdersService.*(..))")
    public void afterOrdersService() {
        ordersServiceCount++;
    }

    @After("execution(public * com.geekbrains.book.store.services.UserService.*(..))")
    public void afterUserService() {
        userServiceCount++;
    }
}
