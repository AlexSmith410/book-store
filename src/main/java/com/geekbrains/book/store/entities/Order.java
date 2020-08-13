package com.geekbrains.book.store.entities;

import com.geekbrains.book.store.beans.Cart;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<OrderItem> items;

    @Column(name = "price")
    private BigDecimal price;

    public Order(User user, Cart cart) {
        this.user = user;
        this.items = new ArrayList<>();
        for (OrderItem items : cart.getItems()) {
            items.setOrder(this);
            this.items.add(items);
        }
        this.price = new BigDecimal(cart.getPrice().doubleValue());
        cart.clear();
    }
}
