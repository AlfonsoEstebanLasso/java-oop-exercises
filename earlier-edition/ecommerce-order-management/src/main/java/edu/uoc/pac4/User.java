package pac4;

import edu.uoc.pac4.exception.UserException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import java.time.LocalDate;
import java.util.Arrays;

public class User {
    private String name;
    private String email;
    private LocalDate birthDate;
    private double debt = 0;
    private boolean premium = false;
    private Gender gender;
    private Address address;
    private final Order[] orders;
    public static final int MAX_ORDERS = 1000;

    public User(String name, String email, Gender gender) {
        this(name, email, gender, null);
    }

    public User(String name, String email, Gender gender, Address address) {
        this.name = name;
        setEmail(email);
        this.gender = gender;
        this.address = address;
        this.orders = new Order[MAX_ORDERS];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (!isValidEmail(email)) {
            throw new UserException("[ERROR] The email is not in a valid format");
        }
        this.email = email;
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        int atPos = email.indexOf('@');
        if (atPos < 1|| atPos != email.lastIndexOf('@') || atPos == email.length() - 1) {
            return false;
        }
        String username = email.substring(0, atPos);
        String domain = email.substring(atPos + 1);
        return !username.isEmpty() && domain.contains(".") && !domain.endsWith(".");
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public LocalDate setBirthDate(LocalDate birthDate) {
        if (birthDate.plusYears(16).isAfter(LocalDate.now())) {
            throw new UserException("[ERROR] The user must be at least 16 years old");
        }
        this.birthDate = birthDate;
        return this.birthDate;
    }

    public double getDebt() {
        return debt;
    }

    public void setDebt(double debt) {
        this.debt = debt;
    }

    public void addDebt(double debt) {
        if (debt <= 0) {
            throw new UserException("[ERROR] The added debt value must be greater than zero");
        }
        this.debt += debt;
    }

    public void resetDebt() {
        this.debt = 0;
    }

    public boolean isPremium() {
        return premium;
    }

    public void subscribe() {
        if (debt > 0) {
            throw new UserException("[ERROR] The user cannot be subscribed as premium if he/she is in debt");
        }
        this.premium = true;
    }

    public void unsubscribe() {
        this.premium = false;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        User other = (User) obj;
        return this.email.equals(other.email);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\tName: ").append(name).append("\n");
        sb.append("\te-mail: ").append(email).append("\n");
        sb.append("\tBirth date: ").append(birthDate != null ? birthDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A").append("\n");
        sb.append("\tPremium?: ").append(premium ? "Y" : "N").append("\n");
        sb.append("\tAddress: ").append(address != null ? address.toString() : "N/A").append("\n");
        return sb.toString();
    }

    public Order[] getOrders() {
        return orders;
    }

    public boolean addOrder(Order order) {
        for (int i= 0; i < MAX_ORDERS; i++) {
            if (orders[i] == null) {
                orders[i] = order;
                return true;
            }
        }
        return false;
    }
}