public abstract class Villager implements VillagerInterface {
  protected final int id;
  protected final String type;
  protected int healthPoints;
  protected boolean dead;
  protected int voteCount = 0;

  public void voteUp() {
    voteCount++;
  }

  public int getVote() {
    return voteCount;
  }

  public void clearVote() {
    voteCount = 0;
  }

  public int getId() {
    return id;
  }

  public String getType() {
    return type;
  }

  public boolean isDead() {
    return dead;
  }

  public int getHP() {
    return healthPoints;
  }

  public void setHP(int hp) {
    healthPoints = hp;
  }

  public void heal() {
    healthPoints += 500;
  }

  public void kill() {
    dead = true;
  }

  @Override
  public String toString() {
    return "Player " + id;
  }

  public abstract void printPrompt();

  Villager(int id, int healthPoints, String type) {
    this.id = id;
    this.healthPoints = healthPoints;
    this.type = type;
  }
}

class Mafia extends Villager {
  @Override
  public void printPrompt() {
    System.out.print("Select target: ");
  }

  Mafia(int id) {
    super(id, 2500, "Mafia");
  }
}

class Detective extends Villager {
  @Override
  public void printPrompt() {
    System.out.print("Select player to test: ");
  }

  Detective(int id) {
    super(id, 800, "Detective");
  }
}

class Healer extends Villager {
  @Override
  public void printPrompt() {
    System.out.print("Select player to heal: ");
  }

  Healer(int id) {
    super(id, 800, "Healer");
  }
}

class Commoner extends Villager {
  @Override
  public void printPrompt() {}

  Commoner(int id) {
    super(id, 1000, "Commoner");
  }
}
