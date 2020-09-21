import java.util.*;

public class Zotato {
  private final ArrayList<CustomerInterface> customers;
  private final ArrayList<RestaurantInterface> restaurants;
  private float balance;
  private float deliveryCharges;

  public ArrayList<CustomerInterface> getCustomers() {
    return customers;
  }

  public ArrayList<RestaurantInterface> getRestaurants() {
    return restaurants;
  }

  public float getBalance() {
    return balance;
  }

  public float getDeliveryCharges() {
    return deliveryCharges;
  }

  public void printDetails() {
    String text = "\nTotal company balance: INR " + balance + "/-\n"
      + "Total delivery charges collected: INR " + deliveryCharges + "/-";
    System.out.println(text);
  }

  public void addToBalance(float amount) {
    balance += amount;
  }

  public void addToDeliveryCharges(float amount) {
    deliveryCharges += amount;
  }

  Zotato() {
    customers = new ArrayList<CustomerInterface>();
    restaurants = new ArrayList<RestaurantInterface>();
    balance = 0;
    deliveryCharges = 0;

    customers.add(new EliteCustomer("Ram", "Dwarka"));
    customers.add(new EliteCustomer("Sam", "Gurgaon"));
    customers.add(new SpecialCustomer("Tim", "Okhla"));
    customers.add(new Customer("Kim", "NOIDA"));
    customers.add(new Customer("Jim", "Rohini"));

    restaurants.add(new AuthenticRestaurant("Shah", "Dwarka"));
    restaurants.add(new Restaurant("Ravi's", "Gurgaon"));
    restaurants.add(new AuthenticRestaurant("The Chinese", "Okhla"));
    restaurants.add(new FastFoodRestaurant("Wang's", "NOIDA"));
    restaurants.add(new Restaurant("Paradise", "Rohini"));
  }
}
