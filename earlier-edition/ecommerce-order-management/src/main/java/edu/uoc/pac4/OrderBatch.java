package edu.uoc.pac4;

import java.time.LocalDate;
import java.util.*;

public class OrderBatch {
    private String name;
    private String description;
    private final int MAX_SIZE;
    private final String MSG_ERR_NULL = "[ERROR] The Order object cannot be null";
    private Set<Order> orders;

    // Constructor
    public OrderBatch(String name, String description, int size) {
        this.name = name;
        this.description = description;
        this.MAX_SIZE = size;
        this.orders = new HashSet<>(size);
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxSize() {
        return MAX_SIZE;
    }

    // Returns a copy of the orders as a list
    public List<Order> getOrders() {
        List<Order> orderList = new ArrayList<>(orders);
        Collections.sort(orderList);
        return orderList;
    }

    // Adds a new order to the set
    public boolean addOrder(Order order) {
        if (order == null) {
            throw new NullPointerException(MSG_ERR_NULL);
        }
        if (orders.size() >= MAX_SIZE) {
            return false;
        }
        return orders.add(order);
    }

    // Removes an order from the set
    public boolean remove(Order order) {
        if (order == null) {
            throw new NullPointerException(MSG_ERR_NULL);
        }
        return orders.remove(order);
    }

    // Removes all orders from the set
    public void remove() {
        orders.clear();
    }

    // Checks if an order exists in the set
    public boolean exists(Order order) {
        return orders.contains(order);
    }

    // Checks if the set is empty
    public boolean isEmpty() {
        return orders.isEmpty();
    }

    // Checks if the set is full
    public boolean isFull() {
        return orders.size() >= MAX_SIZE;
    }

    // Returns a string representation of the object
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<Order> orderList = getOrders();
        for (Order order : orderList) {
            sb.append("###\n");
            sb.append(order.bill());
        }
        sb.append("###\n");
        return sb.toString();
    }

    // Updates the delivery date of all orders with a given order date to the current date
    public void deliverOrdersAfterDate(LocalDate orderDate) {
        orders.stream()
                .filter(order -> order.getOrderDate().equals(orderDate))
                .forEach(order -> order.setDeliveryDate(LocalDate.now()));
    }

    // Returns a list of orders with the largest total price
    public List<Order> getLargestOrders() {
        double maxPrice = orders.stream()
                .mapToDouble(Order::getTotalPrice)
                .max()
                .orElse(0);
        return orders.stream()
                .filter(order -> order.getTotalPrice() == maxPrice)
                .sorted()
                .toList();
    }

    // Calculates the total revenue for a given product
    public double auditIncomeByProduct(Product product) {
        return orders.stream()
                .flatMap(order -> order.getOrderItems().stream())
                .filter(item -> item.getProduct().equals(product))
                .mapToDouble(OrderItem::getTotalPrice)
                .sum();
    }
}