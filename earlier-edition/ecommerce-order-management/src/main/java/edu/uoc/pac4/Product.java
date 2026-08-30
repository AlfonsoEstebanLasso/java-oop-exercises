package edu.uoc.pac4;

import edu.uoc.pac4.exception.ProductException;

public abstract class Product {
    private String name;
    private double price;
    private int soldUnits;

    public Product(String name, double price) throws ProductException {
        setName(name);
        setPrice(price);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws ProductException {
        if (name == null || name.trim().isEmpty()) {
            throw new ProductException(ProductException.ERR_WRONG_NAME);
        }
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) throws ProductException {
        if (price <= 0) {
            throw new ProductException(ProductException.ERR_WRONG_PRICE);
        }
        this.price = price;
    }

    public int getSoldUnits() {
        return soldUnits;
    }

    protected void setSoldUnits(int soldUnits) {
        this.soldUnits = soldUnits;
    }

    public void addSoldUnits(int units) {
        soldUnits += units;
    }

    public abstract String describeProduct();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Product other = (Product) obj;
        return name.equals(other.name);
    }
}