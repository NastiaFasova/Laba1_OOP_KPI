package application;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private static final HashMap<String, Product> PRODUCTS = new HashMap<>();

    static {
        PRODUCTS.put("butter", new Product("Butter", 55));
        PRODUCTS.put("bread", new Product("bread", 15));
        PRODUCTS.put("oil", new Product("oil", 50));
        PRODUCTS.put("cheese", new Product("cheese", 45));
    }

    public static void main(String[] args) {
        Client client = new Client("Bob", "Hugh");
        Bucket bucket = new Bucket();
        client.setSumOfPurchases(5700);
        client.setDateOfFirstAppointment(LocalDate.of(2020, 2, 12));
        client.setDiscount(5);
        client.setBucket(bucket);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            showMenu();
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 : {
                    chooseProduct();
                    String productName = scanner.next();
                    System.out.println("You ordered " + client.order(PRODUCTS.get(productName)));
                    break;
                } case 2 : {
                    System.out.println("The sum of your purchase " + client.buy());
                    break;
                } case 3 : {
                    client.cancelOrder();
                    break;
                } case 4 : {
                    chooseProduct();
                    String productName = scanner.next();
                    client.cancelOrder(PRODUCTS.get(productName));
                    break;
                } case 5 : {
                    System.out.println(bucket.getProducts());
                    break;
                }
                case 6 : {
                    System.exit(0);
                }
                default:
                    System.out.println("Enter a num from 1 to 5");
            }
        }
    }

    private static void showMenu() {
        System.out.println("Enter the number of the feature you'd like to test: ");
        System.out.println("1. Make an order");
        System.out.println("2. Buy");
        System.out.println("3. Cancel an order");
        System.out.println("4. Cancel an order of a product");
        System.out.println("5. Display your bucket");
        System.out.println("6. Exit...");
    }

    private static void chooseProduct() {
        System.out.println("Enter a product: ");
        System.out.println("butter\nbread\noil\ncheese");
    }
}
