import java.util.*;

public class Account {
  protected final int id;
  protected final String name;
  protected final String address;
  protected final String type;
  protected float rewards;
  private static int lastId = 0;

  public String getName() {
    return this.name;
  }

  public String getType() {
    return this.type;
  }

  public void addRewards(float amount) {
    this.rewards += amount;
  }

  Account(String name, String address, String type) {
    this.id = ++lastId;
    this.name = name;
    this.address = address;
    this.type = type;
    this.rewards = 0;
  }
}
