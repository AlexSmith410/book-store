package com.geekbrains.book.store.console;

import com.geekbrains.book.store.exceptions.ResourceNotFoundException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleOrdersHandler {
        private static final String ORDERS_QUEUE = "ordersQueue";
        private static final String READY_ORDERS_EXCHANGE = "readyOrdersExchanger";

        public static void main(String[] args) throws Exception {
            List<Long> newOrders = new ArrayList<>();
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            Scanner sc = new Scanner(System.in);
            System.out.println(" [*] Ожидаю заказ");
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println("[*] Заказ № " + message + " принят!");
                newOrders.add(Long.parseLong(message));
                while (true) {
                    String input = sc.nextLine();
                    if (input.startsWith("/готово")) {
                        String[] sp = input.split(" ");
                        Long orderID = Long.parseLong(sp[1]);
                        if (newOrders.contains(orderID)) {
                            System.out.println("[*] Заказ №" + orderID + " готов!");
                            newOrders.remove(orderID);
                            channel.basicPublish(READY_ORDERS_EXCHANGE, "", null, orderID.toString().getBytes());
                        } else {
                            throw new ResourceNotFoundException("[*] Заказ с таким номером не найден");
                        }
                    } else {
                        System.out.println("Wrong Input! Try (/готов orderID)");
                    }
                }
            };
            channel.basicConsume(ORDERS_QUEUE, true, deliverCallback, consumerTag -> {});
        }
}
