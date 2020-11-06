package application;

import application.exceptions.NegativePriceException;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
public class Product {
    private static final int LOW_BOUND = 0;
    private String title;
    private double price;

    public Product(String title, double price) {
        this.title = title;
        if (price <= LOW_BOUND) {
            throw new NegativePriceException("The price of a product can't be negative");
        }
        this.price = price;
    }

    public void setPrice(double price) {
        if (price <= LOW_BOUND) {
            throw new NegativePriceException("The price of a product can't be negative");
        }
        this.price = price;
    }
}
