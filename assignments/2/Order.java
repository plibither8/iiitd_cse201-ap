import java.util.*;

public class Order {
  private final ArrayList<FoodItem> foodItems;
  private final String restaurantName;
  private final String customerName;
  private final float finalBillAmount;
  private final float deliveryCharge;

  public ArrayList<FoodItem> getFoodItems() {
    return this.foodItems;
  }

  public String getRestaurantName() {
    return this.restaurantName;
  }

  public String getCustomerName() {
    return this.customerName;
  }

  public float getFinalBillAmount() {
    return this.finalBillAmount;
  }

  public float getDeliveryCharge() {
    return this.deliveryCharge;
  }

  Order(
    ArrayList<FoodItem> foodItems,
    String restaurantName,
    String customerName,
    float finalBillAmount,
    float deliveryCharge
  ) {
    this.foodItems = foodItems;
    this.restaurantName = restaurantName;
    this.customerName = customerName;
    this.finalBillAmount = finalBillAmount;
    this.deliveryCharge = deliveryCharge;
  }
}
