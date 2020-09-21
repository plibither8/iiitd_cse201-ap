import java.util.*;

public class Menu {
  private Zotato company;

  public void start() {
    Scanner in = new Scanner(System.in);
    SubMenu subMenu = new SubMenu(company);

    while(true) {
      String menuText = "\nWelcome to Zotato\n"
        + "  1) Enter as Restaurant Owner\n"
        + "  2) Enter as Customer\n"
        + "  3) Check User Details\n"
        + "  4) Company Account Details\n"
        + "  5) Exit\n";
      System.out.println(menuText);
      System.out.print("Enter choice: ");
      int choice = in.nextInt();
      in.nextLine();
      switch(choice) {
        case 1: {
          subMenu.restaurant();
          break;
        }
        case 2: {
          subMenu.customer();
          break;
        }
        case 3: {
          subMenu.userDetails();
          break;
        }
        case 4: {
          company.printDetails();
          break;
        }
        case 5:
        default: {
          return;
        }
      }
    }
  }

  Menu(Zotato company) {
    this.company = company;
  }
}

class SubMenu {
  private Scanner in;
  private Zotato company;
  private UserActions restaurantActions;
  private UserActions customerActions;

  public void restaurant() {
    restaurantActions.listUsers();
    RestaurantInterface restaurant = company
      .getRestaurants()
      .get(restaurantActions.getUserSelection());

    String menuText = "\nWelcome to " + restaurant.getName() + "\n"
      + "  1) Add item\n"
      + "  2) Edit item\n"
      + "  3) Print rewards\n"
      + "  4) Discount on bill value\n"
      + "  5) Exit\n";

    while(true) {
      System.out.println(menuText);
      System.out.print("Select choice: ");
      int choice = in.nextInt();
      in.nextLine();

      switch(choice) {
        case 1: {
          restaurant.addItem();
          break;
        }
        case 2: {
          restaurant.editItem();
          break;
        }
        case 3: {
          restaurant.printRewards();
          break;
        }
        case 4: {
          restaurant.setDiscount();
          break;
        }
        case 5: 
        default: {
          return;
        }
      }
    }
  }

  public void customer() {
    customerActions.listUsers();
    CustomerInterface customer = company
      .getCustomers()
      .get(customerActions.getUserSelection());
    RestaurantInterface restaurant = null;

    String menuText = "\nWelcome " + customer.getName() + "\n"
      + "  1) Select food items\n"
      + "  2) Checkout cart\n"
      + "  3) Rewards won\n"
      + "  4) Print recent orders\n"
      + "  5) Exit\n";

    while(true) {
      System.out.println(menuText);
      System.out.print("Select choice: ");
      int choice = in.nextInt();

      switch (choice) {
        case 1: {
          if (customer.getNewOrder()) {
            restaurantActions.listUsers();
            restaurant = company
              .getRestaurants()
              .get(restaurantActions.getUserSelection());
            customer.createNewCart(company, restaurant);
          }

          customer.fillCart(restaurant);
          break;
        }
        case 2: {
          if (customer.getNewOrder()) {
            System.out.println("\nPlease add food items before checking out cart!");
            break;
          }
          customer.checkoutCart();
          break;
        }
        case 3: {
          customer.printRewards();
          break;
        }
        case 4: {
          customer.printLastTenOrders();
          break;
        }
        case 5:
        default: {
          if (customer.isCartDiscardable()) return;
          System.out.println("\nPlease checkout your cart!");
        }
      }
    }
  }

  public void userDetails() {
    String menuText = "\nChoose type:\n"
      + "  1) Restaurant\n"
      + "  2) Customer";

    System.out.println(menuText);
    System.out.print("\nEnter choice: ");

    int choice = in.nextInt();
    in.nextLine();

    switch(choice) {
      case 1: {
        restaurantActions.listUsers();
        int selectedIndex = restaurantActions.getUserSelection();
        RestaurantInterface restaurant = company.getRestaurants().get(selectedIndex);
        restaurant.printDetails();

        break;
      }
      case 2: {
        customerActions.listUsers();
        int selectedIndex = customerActions.getUserSelection();
        CustomerInterface customer = company.getCustomers().get(selectedIndex);
        customer.printDetails();

        break;
      }
    }
  }

  SubMenu(Zotato company) {
    this.company = company;
    this.in = new Scanner(System.in);
    this.restaurantActions = new RestaurantUserActions(company);
    this.customerActions = new CustomerUserActions(company);
  }
}
