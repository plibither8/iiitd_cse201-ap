import java.util.*;

interface CustomerInterface {
  public String getName();
  public String getType();
  public float getAbsoluteDiscount();
  public float getDeliveryCharge();
  public boolean getNewOrder();
  public void setNewOrder(boolean value);
  public void printDetails();
  public void printRewards();
  public void printLastTenOrders();
  public void addRewards(float amount);
  public void addOrder(Order order);
  public void fillCart(RestaurantInterface restaurant);
  public void createNewCart(Zotato company, RestaurantInterface restaurant);
  public void checkoutCart();
  public void subtractFromBalance(float amount);
  public boolean isCartDiscardable();
  public boolean sufficientBalance(float billAmount);
}

class Customer extends Account implements CustomerInterface {
  protected float deliveryCharge;
  protected float absoluteBillDiscount;
  protected float balance;
  protected Cart cart;
  protected ArrayList<Order> orders;
  protected boolean newOrder;

  private Scanner in;

  @Override
  public void fillCart(RestaurantInterface restaurant) {
    while(true) {
      restaurant.printFoodItems();

      System.out.print("\nSelect food by ID (0 to exit): ");
      int id = in.nextInt();
      if (id == 0) break;

      FoodItem selectedItem = restaurant.getFoodItemById(id);
      System.out.print("\nEnter quantity: ");
      int requestedQuantity = in.nextInt();

      while (requestedQuantity > selectedItem.getQuantity()) {
        System.out.println("\nEntered quantity not available. Please try again.");
        System.out.print("Enter quantity: ");
        requestedQuantity = in.nextInt();
      }

      restaurant.getFoodItemById(id).setQuantityDelta(-requestedQuantity);
      cart.addItem(selectedItem, requestedQuantity);
    }
  }

  @Override
  public void createNewCart(Zotato company, RestaurantInterface restaurant) {
    this.cart = new Cart(company, this, restaurant);
  }

  @Override
  public void checkoutCart() {
    this.cart.checkout();
  }

  @Override
  public boolean getNewOrder() {
    return this.newOrder;
  }

  @Override
  public void setNewOrder(boolean value) {
    this.newOrder = value;
  }

  @Override
  public float getAbsoluteDiscount() {
    return this.absoluteBillDiscount;
  }

  @Override
  public float getDeliveryCharge() {
    return this.deliveryCharge;
  }

  @Override
  public void printDetails() {
    String typeText = this.type.equals("Regular") ? "" : (" (" + this.type + ")");
    String text = this.name + typeText + ", " + this.address + ", INR " + this.balance + "/-";
    System.out.println(text);
  }

  @Override
  public void printRewards() {
    System.out.println("Reward points: " + this.rewards);
  }

  @Override
  public void printLastTenOrders() {
    int count = 0;
    for (Order order : orders) {
      if (++count > 10) {
        break;
      }

      System.out.println("\nRestaurant: " + order.getRestaurantName());
      System.out.println("Total price: INR " + order.getFinalBillAmount() + "/-");
      System.out.println("Delivery charge: INR " + order.getDeliveryCharge() + "/-");
      System.out.println("Food items:");
      for (FoodItem foodItem : order.getFoodItems()) {
        foodItem.printDetails();
      }
    }
  }

  @Override
  public boolean isCartDiscardable() {
    return this.cart.isDiscardable();
  }

  @Override
  public boolean sufficientBalance(float billAmount) {
    return (this.balance + this.rewards) >= billAmount;
  }

  @Override
  public void subtractFromBalance(float amount) {
    if (this.rewards > amount) {
      this.rewards -= amount;
    } else {
      amount -= this.rewards;
      this.rewards = 0;
      this.balance -= amount;
    }
  }

  @Override
  public void addOrder(Order order) {
    this.orders.add(order);
  }

  Customer(String name, String address) {
    this(name, address, "Regular");
  }

  Customer(String name, String address, String type) {
    super(name, address, type);
    this.balance = 1000;
    this.deliveryCharge = 40;
    this.absoluteBillDiscount = 0;
    this.orders = new ArrayList<Order>();
    this.newOrder = true;
    in = new Scanner(System.in);
  }
}

class EliteCustomer extends Customer {
  EliteCustomer(String name, String address) {
    super(name, address, "Elite");
    this.deliveryCharge = 0;
    this.absoluteBillDiscount = 25;
  }
}

class SpecialCustomer extends Customer {
  SpecialCustomer(String name, String address) {
    super(name, address, "Special");
    this.deliveryCharge = 20;
    this.absoluteBillDiscount = 50;
  }
}