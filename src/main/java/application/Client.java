package application;

import application.exceptions.EmptyBucketException;
import application.exceptions.NegativeSumException;
import application.exceptions.OutOfBoundsDiscountException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Iterator;

@Getter
@ToString
@EqualsAndHashCode
public class Client {
    private static final LocalDate DATE_OF_OPENING_THE_FACTORY
            = LocalDate.of(2015, 1, 1);
    private static final int LOW_BOUND_OF_DISCOUNT = 0;
    private static final int UPPER_BOUND_OF_DISCOUNT = 90;
    private String name;
    private String surname;
    private LocalDate dateOfFirstAppointment;
    private double sumOfPurchases;
    private double discount;
    private Bucket bucket;

    public Client() {
    }

    public Client(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Client(String name, String surname, LocalDate dateOfFirstAppointment,
                  double sumOfPurchases, double discount, Bucket bucket) {
        this.name = name;
        this.surname = surname;
        checkDate(dateOfFirstAppointment);
        this.dateOfFirstAppointment = dateOfFirstAppointment;
        checkSumOfPurchases(sumOfPurchases);
        this.sumOfPurchases = sumOfPurchases;
        checkDiscount(discount);
        this.discount = discount;
        this.bucket = bucket;
    }

    public void setDateOfFirstAppointment(LocalDate dateOfFirstAppointment) {
        checkDate(dateOfFirstAppointment);
        this.dateOfFirstAppointment = dateOfFirstAppointment;
    }

    public void setSumOfPurchases(double sumOfPurchases) {
        checkSumOfPurchases(sumOfPurchases);
        this.sumOfPurchases = sumOfPurchases;
    }

    public void setDiscount(double discount) {
        checkDiscount(discount);
        this.discount = discount;
    }

    public void setBucket(Bucket bucket) {
        this.bucket = bucket;
    }

    private Double getPriceWithDiscount(double price) {
        return price - price * discount / 100;
    }

    private void checkDate(LocalDate dateOfFirstAppointment) {
        if (dateOfFirstAppointment.isBefore(DATE_OF_OPENING_THE_FACTORY)) {
            System.out.println("The factory didn't exit. The date of 1-st appointment should be after "
                    + DATE_OF_OPENING_THE_FACTORY);
            System.exit(0);
        }
    }

    private void checkSumOfPurchases(double sumOfPurchases) {
        if (sumOfPurchases < 0) {
            throw new NegativeSumException("The sum of purchases can't be 0");
        }
    }

    private void checkDiscount(double discount) {
        if (discount < LOW_BOUND_OF_DISCOUNT || discount > UPPER_BOUND_OF_DISCOUNT) {
            throw new OutOfBoundsDiscountException("Discount can't be less than 5 or more than 90");
        }
    }

    public Product order(Product product) {
        bucket.getProducts().add(product);
        return product;
    }

    public Double buy() {
        if (bucket.getProducts().size() == 0) {
            throw new EmptyBucketException("Firstly, make the order, please");
        }
        Double sum = bucket.getProducts()
                .stream()
                .map(Product::getPrice)
                .reduce(Double::sum)
                .orElseThrow(() -> new RuntimeException("Some problems calculating the sum of order"));
//        Double sum = 0D;
//        for (Product product : bucket.getProducts()) {
//            sum += product.getPrice();
//        }
        double priceWithDiscount = getPriceWithDiscount(sum);
        sumOfPurchases += priceWithDiscount;
        bucket.getProducts().clear();
        return priceWithDiscount;
    }

    public Bucket cancelOrder() {
        bucket.getProducts().clear();
        return bucket;
    }

    public Bucket cancelOrder(Product product) {
        Iterator<Product> iterator = bucket.getProducts().iterator();
        while (iterator.hasNext()) {
            Product currentProduct = iterator.next();
            if (currentProduct.equals(product)) {
                iterator.remove();
            }
        }
        return bucket;
    }
}
