package com.geekbrains.book.store.beans;

import com.geekbrains.book.store.entities.Book;
import com.geekbrains.book.store.entities.OrderItem;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class Cart {
    private List<OrderItem> items;
    private BigDecimal price;

    @PostConstruct
    public void init() {
        items = new ArrayList<>();
    }

    public void clear() {
        items.clear();
        countPrice();
    }

    public void countPrice() {
        price = new BigDecimal(0.0);
        for (OrderItem i : items) {
            price = price.add(i.getPrice());
        }
    }

    public void increase(Book book) {
        for (OrderItem i : items) {
            if (i.getBook().getId().equals(book.getId())) {
                i.setQuantity(i.getQuantity() + 1);
                i.setPrice(new BigDecimal(i.getQuantity() * book.getPrice().doubleValue()));
                countPrice();
                return;
            }
        }
        items.add(new OrderItem(book));
        countPrice();
    }

    public void decrease(Book book) {
        for (OrderItem i : items) {
            if (i.getBook().getId().equals(book.getId())) {
                i.setQuantity(i.getQuantity() - 1);
                i.setPrice(new BigDecimal(i.getQuantity() * book.getPrice().doubleValue()));
                if (i.getQuantity() == 0) {
                    items.remove(i);
                }
                countPrice();
                return;
            }
        }
    }

    public void removeByBookId(Long bookId) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getBook().getId().equals(bookId)) {
                items.remove(i);
                countPrice();
                return;
            }
        }
    }
}
