package com.geekbrains.book.store;

import com.geekbrains.book.store.beans.Cart;
import com.geekbrains.book.store.entities.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class CartTest {
    @Autowired
    private Cart cart;
    private final Book testBook1 = new Book(1L, "Harry Potter", "Description", BigDecimal.valueOf(300L), 2000, Book.Genre.FANTASY);
    private final Book testBook2 = new Book(2L, "Hobbit", "Description", BigDecimal.valueOf(500L), 2013, Book.Genre.FANTASTIC);

    @Test
    void addBookTest() {
        Assertions.assertAll(() -> {
            cart.increase(testBook1);
            Assertions.assertEquals(testBook1, cart.getItems().get(0).getBook());
            Assertions.assertEquals(BigDecimal.valueOf(300), cart.getPrice());
            Assertions.assertEquals(1, cart.getItems().size());
        }, () -> {
            cart.increase(testBook2);
            Assertions.assertEquals(testBook2, cart.getItems().get(1).getBook());
            Assertions.assertEquals(BigDecimal.valueOf(800), cart.getPrice());
            Assertions.assertEquals(2, cart.getItems().size());
        });
    }

    @Test
    void clearCartTest() {
        cart.increase(testBook1);
        cart.increase(testBook1);
        cart.increase(testBook2);
        cart.increase(testBook2);
        cart.clear();
        Assertions.assertEquals(0, cart.getItems().size());
        Assertions.assertEquals(BigDecimal.valueOf(0), cart.getPrice());
    }

    @Test
    void removeByBookIdTest() {
        cart.increase(testBook1);
        cart.increase(testBook2);
        Assertions.assertAll(() -> {
            cart.removeByBookId(1L);
            Assertions.assertEquals(BigDecimal.valueOf(500), cart.getPrice());
            Assertions.assertNotEquals(testBook1, cart.getItems().get(0).getBook());
            Assertions.assertEquals(1, cart.getItems().size());
        }, () -> {
            cart.removeByBookId(2L);
            Assertions.assertEquals(BigDecimal.valueOf(0), cart.getPrice());
            Assertions.assertEquals(0, cart.getItems().size());
        });
    }
}
