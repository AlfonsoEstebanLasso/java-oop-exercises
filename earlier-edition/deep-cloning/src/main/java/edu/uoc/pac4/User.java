package edu.uoc.pac4;

import java.time.LocalDate;

public class User implements Cloneable {
    private String name;
    private Address address;
    private PaymentCard paymentCard;

    public User(String name, String street, String zipCode, String cardNumber, LocalDate cardExpireDate) {
        setName(name);
        this.address = new Address(street, zipCode);
        this.paymentCard = new PaymentCard(cardNumber, cardExpireDate);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }


    public PaymentCard getPaymentCard() {
        return paymentCard;
    }

    @Override
    public User clone() throws CloneNotSupportedException {
        User clonedUser = (User) super.clone();
        clonedUser.address = (Address) address.clone();
        clonedUser.paymentCard = paymentCard.clone();
        return clonedUser;
    }
}