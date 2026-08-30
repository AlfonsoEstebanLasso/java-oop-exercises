package pac4;

import edu.uoc.pac4.exception.AddressException;

import java.util.Objects;

public class Address {
    private String street;
    private int number;
    private String zipCode;
    private String city;

    public Address(String street, int number, String zipCode, String city) {
        this.street = street;
        this.number = number;
        this.zipCode = zipCode;
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) throws AddressException {
        if (number <= 0) {
            throw new AddressException("[ERROR] Street number must be greater than zero");
        }
        this.number = number;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) throws AddressException {
        // Verificar si el código postal es alfanumérico
        if (!zipCode.matches("[a-zA-Z0-9]+")) {
            throw new AddressException(AddressException.ERR_INVALID_ZIPCODE);
        }
        this.zipCode = zipCode;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isInternational() {
        if (zipCode.length() == 5 && zipCode.matches("[0-9]+")) {
            return false; // ZipCode nacional
        } else {
            return true; // ZipCode internacional
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Address other = (Address) obj;
        return Objects.equals(this.street, other.street) &&
                Objects.equals(this.number, other.number) &&
                Objects.equals(this.zipCode, other.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, number, zipCode, city);
    }

    @Override
    public String toString() {
        return street + ", " + number + ", " + city + " (" + zipCode + ")";
    }
}
