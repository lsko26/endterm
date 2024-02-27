import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
interface Purchasable {

}
record Car(String model, double price) implements Purchasable {

}
class Customer {
    private final String name;
    private final double budget;

    public Customer(String name, double budget) {
        this.name = name;
        this.budget = budget;
    }

    public String getName() {
        return name;
    }
    public boolean canAfford(double price) {
        return budget >= price;
    }
}
class CarDealership {
    private final List<Purchasable> inventory;

    public CarDealership() {
        this.inventory = new ArrayList<>();
    }

    public void addToInventory(Purchasable item) {
        inventory.add(item);
    }

    public void displayAvailableItems() {
        System.out.println("Available Cars:");
        for (Purchasable item : inventory) {
            if (item instanceof Car car) {
                System.out.println(car.model() + " - $" + car.price());
            }
        }
    }

    public void sellItem(Customer customer, String model) {
        for (Purchasable item : inventory) {
            if (item instanceof Car car) {
                if (car.model().equalsIgnoreCase(model)) {
                    if (customer.canAfford(car.price())) {
                        System.out.println(customer.getName() + " bought " + car.model() + " for $" + car.price());
                    } else {
                        System.out.println(customer.getName() + " cannot afford " + car.model());
                    }
                    return;
                }
            }
        }
        System.out.println("Car with model " + model + " not found.");
    }
}

public class Main {
    public static void main(String[] args) {
        CarDealership dealership = new CarDealership();
        dealership.addToInventory(new Car("Toyota Camry", 25000));
        dealership.addToInventory(new Car("Honda Civic", 22000));
        dealership.addToInventory(new Car("Ford Mustang", 35000));

        dealership.displayAvailableItems();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your budget: ");
        double budget = scanner.nextDouble();
        scanner.nextLine();

        Customer customer = new Customer(name, budget);

        System.out.println("Welcome, " + customer.getName() + "!");

        while (true) {
            System.out.print("Enter the model of the car you want to buy (or type 'exit' to quit): ");
            String model = scanner.nextLine();
            if (model.equalsIgnoreCase("exit")) {
                System.out.println("Thank you for visiting!");
                break;
            }
            dealership.sellItem(customer, model);
        }

        scanner.close();
    }
}
