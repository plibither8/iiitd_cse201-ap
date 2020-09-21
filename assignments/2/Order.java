import java.util.*;

public class Order {
  private final ArrayList<FoodItem> foodItems;
  private final String restaurantName;
  private final String customerName;
  private final float finalBillAmount;
  private final float deliveryCharge;

  public ArrayList<FoodItem> getFoodItems() {
    return foodItems;
  }

  public String getRestaurantName() {
    return restaurantName;
  }

  public String getCustomerName() {
    return customerName;
  }

  public float getFinalBillAmount() {
    return finalBillAmount;
  }

  public float getDeliveryCharge() {
    return deliveryCharge;
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
