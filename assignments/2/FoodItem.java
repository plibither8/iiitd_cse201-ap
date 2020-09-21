public class FoodItem {
  private final int id;
  private String name;
  private float price;
  private int quantity;
  private String category;
  private float discount;

  private static int lastId = 0;

  public void printDetails() {
    String text = "  " + this.id + ") "
      + this.name + ", "
      + "INR " + this.price + "/-, "
      + this.quantity + ", "
      + this.discount + "% off" + ", "
      + this.category;
    System.out.println(text);
  }

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public float getPrice() {
    return this.price;
  }

  public int getQuantity() {
    return this.quantity;
  }

  public float getDiscount() {
    return this.discount;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public void setQuantityDelta(int delta) {
    this.quantity += delta;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public void setDiscount(float discount) {
    this.discount = discount;
  }

  FoodItem(
    String name,
    float price,
    int quantity,
    String category,
    float discount
  ) {
    this.id = ++lastId;
    this.name = name;
    this.price = price;
    this.quantity = quantity;
    this.category = category;
    this.discount = discount;
  }

  FoodItem(FoodItem foodItem, int quantity) {
    this.id = foodItem.id;
    this.name = foodItem.name;
    this.price = foodItem.price;
    this.discount = foodItem.discount;
    this.category = foodItem.category;
    this.quantity = quantity;
  }
}
