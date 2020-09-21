import java.util.*;

public interface RestaurantInterface {
  public String getName();
  public String getType();
  public float getRelativeDiscount();
  public float getAbsoluteDiscount();
  public float calculateRewards(float restaurantCharges);
  public void printDetails();
  public void printFoodItems();
  public void incrementOrderCount();
  public void addRewards(float amount);
  public void addItem();
  public void editItem();
  public void printRewards();
  public void setDiscount();
  public FoodItem getFoodItemById(int id);
}

class Restaurant extends Account implements RestaurantInterface {
  protected float relativeDiscount;
  protected float absoluteDiscount;
  protected float rewardUnit;
  protected float rewardPer;
  protected HashMap<Integer, FoodItem> foodItems;
  protected int orderCount;

  private Scanner in;

  public void printDetails() {
    System.out.println(name + ", " + address + ", " + orderCount);
  }

  /* Accessors */
  @Override
  public float getRelativeDiscount() {
    return relativeDiscount;
  };

  @Override
  public float getAbsoluteDiscount() {
    return absoluteDiscount;
  };

  @Override
  public float calculateRewards(float restaurantCharges) {
    return rewardUnit * (int)(restaurantCharges / rewardPer);
  }

  @Override
  public FoodItem getFoodItemById(int id) {
    return foodItems.get(id);
  }

  @Override
  public void printFoodItems() {
    System.out.println("\nFood items from " + name + ":");
    for (int id : foodItems.keySet()) {
      FoodItem foodItem = foodItems.get(id);
      foodItem.printDetails();
    }
  }

  @Override
  public void printRewards() {
    System.out.println("\nReward points: " + rewards);
  }

  /* Modifiers */
  @Override
  public void incrementOrderCount() {
    orderCount++;
  }

  @Override
  public void addItem() {
    System.out.println("\nEnter details:");

    System.out.print("  Name: ");
    String name = in.nextLine();

    System.out.print("  Price (INR): ");
    float price = in.nextFloat();

    System.out.print("  Quantity: ");
    int quantity = in.nextInt();

    System.out.println("  Category:");
    System.out.println(
      "    1) Starter\n"
      + "    2) Main Course\n"
      + "    3) Desert\n"
      + "    4) Beverage"
    );
    String category = "";
    System.out.print("  Enter category: ");
    switch(in.nextInt()) {
      case 1: {
        category = "Starter";
        break;
      }
      case 2: {
        category = "Main Course";
        break;
      }
      case 3: {
        category = "Desert";
        break;
      }
      case 4: {
        category = "Beverage";
        break;
      }
    }

    System.out.print("  Offer (%): ");
    float discount = in.nextFloat();
    in.nextLine();

    FoodItem foodItem = new FoodItem(
      name,
      price,
      quantity,
      category,
      discount
    );
    foodItems.put(foodItem.getId(), foodItem);

    System.out.println();
    foodItem.printDetails();
  }

  @Override
  public void editItem() {
    printFoodItems();

    System.out.print("\nSelect food by ID: ");
    int id = in.nextInt();
    FoodItem selectedItem = foodItems.get(id);

    String choicesText = "\nChoose an attribute to edit:\n"
      + "  1) Name\n"
      + "  2) Price\n"
      + "  3) Quantity\n"
      + "  4) Category\n"
      + "  5) Offer";

    System.out.println(choicesText);
    System.out.print("Enter choice: ");

    int choice = in.nextInt();
    in.nextLine();

    switch(choice) {
      case 1: {
        System.out.print("\nEnter new name: ");
        selectedItem.setName(in.nextLine());
        break;
      }
      case 2: {
        System.out.print("\nEnter new price: ");
        selectedItem.setPrice(in.nextFloat());
        break;
      }
      case 3: {
        System.out.print("\nEnter new quantity: ");
        selectedItem.setQuantity(in.nextInt());
        break;
      }
      case 4: {
        System.out.print("\nSelect category: ");
        System.out.println(
          "  1) Starter\n"
          + "  2) Main Course\n"
          + "  3) Desert\n"
          + "  4) Beverage"
        );
        String category = "";
        System.out.print("Enter category: ");
        switch(in.nextInt()) {
          case 1: {
            category = "Starter";
            break;
          }
          case 2: {
            category = "Main Course";
            break;
          }
          case 3: {
            category = "Desert";
            break;
          }
          case 4: {
            category = "Beverage";
            break;
          }
        }
        selectedItem.setCategory(category);
        break;
      }
      case 5: {
        System.out.print("\nEnter new offer: ");
        selectedItem.setDiscount(in.nextFloat());
        break;
      }
    }

    System.out.println();
    selectedItem.printDetails();
  }

  @Override
  public void setDiscount() {
    if (type.equals("Regular")) {
      System.out.println("Regular restaurants cannot set discount on bill value");
    } else {
      System.out.print("\nEnter discount on bill value (%): ");
      relativeDiscount = in.nextFloat();
    }
  }

  Restaurant(String name, String address) {
    this(name, address, "Regular");
  }

  Restaurant(String name, String address, String type) {
    super(name, address, type);
    relativeDiscount = 0;
    absoluteDiscount = 0;
    rewardUnit = 5;
    rewardPer = 100;
    orderCount = 0;
    foodItems = new HashMap<Integer, FoodItem>();
    in = new Scanner(System.in);
  }
}

class AuthenticRestaurant extends Restaurant {
  AuthenticRestaurant(String name, String address) {
    super(name, address, "Authentic");
    rewardUnit = 25;
    rewardPer = 200;
    absoluteDiscount = 50;
  }
}

class FastFoodRestaurant extends Restaurant {
  FastFoodRestaurant(String name, String address) {
    super(name, address, "Fast Food");
    rewardUnit = 10;
    rewardPer = 150;
  }
}
