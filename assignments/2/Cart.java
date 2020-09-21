import java.util.*;

public class Cart {
  private final Zotato company;
  private final CustomerInterface customer;
  private final RestaurantInterface restaurant;
  private HashMap<Integer, FoodItem> foodItems;
  private boolean cleared;
  private boolean filled;

  public void addItem(FoodItem foodItem, int quantity) {
    int id = foodItem.getId();
    FoodItem alreadyPresentItem = foodItems.get(id);

    if (alreadyPresentItem != null) {
      int newQuantity = alreadyPresentItem.getQuantity() + quantity;
      alreadyPresentItem.setQuantity(newQuantity);
    } else {
      FoodItem newItem = new FoodItem(foodItem, quantity);
      foodItems.put(newItem.getId(), newItem);
    }

    customer.setNewOrder(false);
    filled = true;

    System.out.println("\nItems added to cart!");
  }

  private float getFoodPrice() {
    float totalFoodPrice = 0;
    for (int id : foodItems.keySet()) {
      FoodItem foodItem = foodItems.get(id);
      float rawItemPrice = foodItem.getPrice() * foodItem.getQuantity();
      float discountedPrice = (100 - foodItem.getDiscount()) * rawItemPrice / 100;
      totalFoodPrice += discountedPrice;
    }
    return totalFoodPrice;
  }

  private float getRestaurantCharge() {
    // Base price
    float restaurantCharges = getFoodPrice();

    // Restaurant discounts
    float restaurantOverallDiscount = restaurant.getRelativeDiscount() * restaurantCharges / 100;
    restaurantCharges -= restaurantOverallDiscount;
    if (restaurantCharges > 100) {
      restaurantCharges -= restaurant.getAbsoluteDiscount();
    }

    // Customer discounts
    if (restaurantCharges > 200) {
      restaurantCharges -= customer.getAbsoluteDiscount();
    }

    return restaurantCharges;
  }

  private float getBillableAmount() {
    return getRestaurantCharge() + customer.getDeliveryCharge();
  }

  public void checkout() {
    Scanner in = new Scanner(System.in);

    while (!customer.sufficientBalance(getBillableAmount())) {
      System.out.println("\nInsufficient balance!");
      System.out.println("\nYour cart:");

      for (int id : foodItems.keySet()) {
        foodItems.get(id).printDetails();
      }

      System.out.print("\nSelect food item by ID to change quantity: ");
      int id = in.nextInt();

      FoodItem foodItem = foodItems.get(id);
      System.out.print("Enter new quantity for " + foodItem.getName() + ": ");
      int oldQuantity = foodItem.getQuantity();
      int newQuantity = in.nextInt();

      int delta = -(newQuantity - oldQuantity);
      foodItem.setQuantity(newQuantity);
      restaurant.getFoodItemById(id).setQuantityDelta(delta);
    }

    System.out.println("\nYour cart:");

    int totalQuantity = 0;
    for (int id : foodItems.keySet()) {
      FoodItem foodItem = foodItems.get(id);
      foodItem.printDetails();
      totalQuantity += foodItem.getQuantity();
    }

    float billableAmount = getBillableAmount();
    float deliveryCharge = customer.getDeliveryCharge();

    System.out.println("\nDelivery charges: INR " + deliveryCharge + "/-");
    System.out.println("Total order value: INR " + billableAmount + "/-");
    System.out.print("\n  Checkout? [y/N]: ");
    if (!in.next().toLowerCase().equals("y")) {
      return;
    }

    float restaurantCharges = getRestaurantCharge();
    float companyCharges = (float)0.01 * restaurantCharges;
    float finalRewards = restaurant.calculateRewards(restaurantCharges);

    restaurant.addRewards(finalRewards);
    company.addToBalance(companyCharges);
    company.addToDeliveryCharges(deliveryCharge);
    customer.subtractFromBalance(billableAmount);
    customer.addRewards(finalRewards);
    customer.addOrder(
      new Order(
        new ArrayList<FoodItem>(foodItems.values()),
        restaurant.getName(),
        customer.getName(),
        billableAmount,
        deliveryCharge
      )
    );

    restaurant.incrementOrderCount();
    cleared = true;
    filled = false;
    customer.setNewOrder(true);

    System.out.println(
      "\n" + totalQuantity + " items successfully bought for INR " + billableAmount + "/-"
    );
  }

  public boolean isDiscardable() {
    return cleared || !filled;
  }

  Cart(Zotato company, CustomerInterface customer, RestaurantInterface restaurant) {
    foodItems = new HashMap<Integer, FoodItem>();
    cleared = false;
    filled = false;
    this.company = company;
    this.customer = customer;
    this.restaurant = restaurant;
  }
}
