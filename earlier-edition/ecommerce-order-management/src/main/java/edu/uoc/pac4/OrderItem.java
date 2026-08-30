package edu.uoc.pac4;

import edu.uoc.pac4.exception.OrderItemException;

public class OrderItem implements Billable {
    private Order order;
    private Product product;
    private int quantity;

    public OrderItem(Order order, Product product, int quantity) throws OrderItemException {
        setOrder(order);
        setProduct(product);
        setQuantity(quantity);
    }

    private void setOrder(Order order) throws OrderItemException {
        if (order == null) {
            throw new OrderItemException("[ERROR] The order cannot be null");
        }
        this.order = order;
    }

    private void setProduct(Product product) throws OrderItemException {
        if (product == null) {
            throw new OrderItemException("[ERROR] The product cannot be null");
        }
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            this.quantity = 1;
        } else {
            this.quantity = quantity;
        }
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }

    public Order getOrder() {
        return order;
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public String bill() {
        double totalPrice = getTotalPrice();
        double taxValue = taxValue(totalPrice);
        String formattedTaxValue = String.format("%.2f", taxValue);
        return "Product: " + product.getName() + " | Quantity: " + quantity +
                " | Price: " + totalPrice + " | Tax: " + formattedTaxValue;
    }
}