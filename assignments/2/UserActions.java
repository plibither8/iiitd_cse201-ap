import java.util.*;

public interface UserActions {
  public void listUsers();
  public int getUserSelection();
}

class RestaurantUserActions implements UserActions {
  private final Zotato company;

  @Override
  public void listUsers() {
    ArrayList<RestaurantInterface> restaurants = company.getRestaurants();
    System.out.println("\nRestaurants: ");
    for (int i = 0; i < restaurants.size(); i++) {
      RestaurantInterface restaurant = restaurants.get(i);
      String name = restaurant.getName();
      String type = restaurant.getType();
      String typeText = type.equals("Regular") ? "" : (" (" + type + ")");
      System.out.println("  " + (i + 1) + ") " + name + typeText);
    }
  }

  @Override
  public int getUserSelection() {
    System.out.print("\nSelect restaurant: ");
    Scanner in = new Scanner(System.in);
    return in.nextInt() - 1;
  }

  RestaurantUserActions(Zotato company) {
    this.company = company;
  }
}

class CustomerUserActions implements UserActions {
  private final Zotato company;

  @Override
  public void listUsers() {
    ArrayList<CustomerInterface> customers = company.getCustomers();
    System.out.println("\nCustomers: ");
    for (int i = 0; i < customers.size(); i++) {
      CustomerInterface customer = customers.get(i);
      String name = customer.getName();
      String type = customer.getType();
      String typeText = type.equals("Regular") ? "" : (" (" + type + ")");
      System.out.println("  " + (i + 1) + ") " + name + typeText);
    }
  }

  @Override
  public int getUserSelection() {
    System.out.print("\nSelect customer: ");
    Scanner in = new Scanner(System.in);
    return in.nextInt() - 1;
  }

  CustomerUserActions(Zotato company) {
    this.company = company;
  }
}
