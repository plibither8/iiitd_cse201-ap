public class FoodItem {
  private final int id;
  private String name;
  private float price;
  private int quantity;
  private String category;
  private float discount;

  private static int lastId = 0;

  public void printDetails() {
    String text = "  " + id + ") "
      + name + ", INR "
      + price + "/-, "
      + quantity + ", "
      + discount + "% off" + ", "
      + category;
    System.out.println(text);
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public float getPrice() {
    return price;
  }

  public int getQuantity() {
    return quantity;
  }

  public float getDiscount() {
    return discount;
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
    quantity += delta;
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
    id = foodItem.id;
    name = foodItem.name;
    price = foodItem.price;
    discount = foodItem.discount;
    category = foodItem.category;
    this.quantity = quantity;
  }
}
