package edu.uoc.pac4;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

public class Order implements Comparable<Order> {
    private UUID id;
    private User user;
    private LocalDate orderDate;
    private LocalDate deliveryDate = null;
    private OrderItem[] orderItems = new OrderItem[MAX_ORDER_ITEMS];
    public static final int MAX_ORDER_ITEMS = 100;

    public Order(User user, LocalDate orderDate) throws OrderException {
        setId();
        setUser(user);
        setOrderDate(orderDate);
        Arrays.fill(orderItems, null);
    }

    public UUID getId() {
        return id;
    }

    private void setId() {
        this.id = UUID.randomUUID();
    }

    public User getUser() {
        return user;
    }

    private void setUser(User user) throws OrderException {
        if (user == null) {
            throw new OrderException("[ERROR] The user cannot be null");
        }
        this.user = user;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public double getTotalPrice() {
                totalPrice = 0;
        for (OrderItem item : orderItems) {
            if (item != null) {
                totalPrice += item.getTotalPrice();
            }
        }
        return totalPrice;
    }

    public OrderItem[] getOrderItems() {
        return orderItems;
    }

    private int getOrderItemIndex(Product product) {
        for (int i = 0; i < orderItems.length; i++) {
            if (orderItems[i] != null && orderItems[i].getProduct().equals(product)) {
                return i;
            }
        }
        return -1;
    }

    public boolean addOrderItem(Product product, int quantity) {
        int index = getOrderItemIndex(product);
        if (index != -1) {
            orderItems[index].setQuantity(orderItems[index].getQuantity() + quantity);
            return true;
        } else {
            for (int i = 0; i < orderItems.length; i++) {
                if (orderItems[i] == null) {
                    orderItems[i] = new OrderItem(this, product, quantity);
                    return true;
                }
            }
            return false;
        }
    }

    public void removeOrderItem(Product product, int quantity) {
        int index = getOrderItemIndex(product);
        if (index != -1) {
            orderItems[index].setQuantity(orderItems[index].getQuantity() - quantity);
            if (orderItems[index].getQuantity() <= 0) {
                orderItems[index] = null;
            }
        }
    }

    @Override
    public String bill() {
        StringBuilder builder = new StringBuilder();
        double totalPrice = getTotalPrice();
        double taxValue = taxValue(totalPrice);
        int count = 1;
        for (OrderItem item : orderItems) {
            if (item != null) {
                builder.append("#").append(count).append(" ").append(item.bill()).append("\n");
                count++;
            }
        }
        builder.append("Total = ").append(totalPrice).append(" | Tax: ").append(taxValue);
        return builder.toString();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", orderDate=" + orderDate +
                ", deliveryDate=" + deliveryDate +
                ", orderItems=" + Arrays.toString(orderItems) +
                '}';
    }

    @Override
    public int compareTo(Order o) {
        if (o == null) {
            throw new NullPointerException();
        }
        if (orderDate.compareTo(o.orderDate) == 0) {
            return Double.compare(o.getTotalPrice(), getTotalPrice());
        } else {
            return orderDate.compareTo(o.orderDate);
        }
    }
}
