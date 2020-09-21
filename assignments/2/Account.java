import java.util.*;

public class Account {
  protected final int id;
  protected final String name;
  protected final String address;
  protected final String type;
  protected float rewards;
  private static int lastId = 0;

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public void addRewards(float amount) {
    rewards += amount;
  }

  Account(String name, String address, String type) {
    this.id = ++lastId;
    this.name = name;
    this.address = address;
    this.type = type;
    this.rewards = 0;
  }
}
