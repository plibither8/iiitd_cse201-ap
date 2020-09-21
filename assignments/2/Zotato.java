import java.util.*;

public class Zotato {
  private final ArrayList<CustomerInterface> customers;
  private final ArrayList<RestaurantInterface> restaurants;
  private float balance;
  private float deliveryCharges;

  public ArrayList<CustomerInterface> getCustomers() {
    return this.customers;
  }

  public ArrayList<RestaurantInterface> getRestaurants() {
    return this.restaurants;
  }

  public float getBalance() {
    return this.balance;
  }

  public float getDeliveryCharges() {
    return this.deliveryCharges;
  }

  public void printDetails() {
    String text = "\nTotal company balance: INR " + this.balance + "/-\n"
      + "Total delivery charges collected: INR " + this.deliveryCharges + "/-";
    System.out.println(text);
  }

  public void addToBalance(float amount) {
    this.balance += amount;
  }

  public void addToDeliveryCharges(float amount) {
    this.deliveryCharges += amount;
  }

  Zotato() {
    this.customers = new ArrayList<CustomerInterface>();
    this.restaurants = new ArrayList<RestaurantInterface>();
    this.balance = 0;
    this.deliveryCharges = 0;

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
