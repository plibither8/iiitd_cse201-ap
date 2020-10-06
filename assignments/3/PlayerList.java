import java.util.*;

public class PlayerList<PlayerType extends VillagerInterface> {
  private ArrayList<PlayerType> list;
  Scanner in;

  public void add(PlayerType player) {
    list.add(player);
  }

  public PlayerType get(int id) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getId() == id) {
        return list.get(i);
      }
    }
    return null;
  }

  public int vote(Villager userPlayer) {
    for (int i = 0; i < list.size(); i++) {
      list.get(i).clearVote();
    }
    for (int i = 0; i < list.size(); i++) {
      Villager v = (Villager) list.get(i);
      if (v.isDead()) continue;
      if (userPlayer.getId() == v.getId()) {
        int userVote;
        boolean correctInput = false;
        PlayerType selectedPlayer = null;
        do {
          try {
            System.out.print("Select player ID to vote: ");
            userVote = in.nextInt();
            if (userVote == userPlayer.getId()) {
              System.out.println("Select player other than you.");
              correctInput = false;
              continue;
            }

            selectedPlayer = get(userVote);

            if (selectedPlayer == null || selectedPlayer.isDead()) {
              System.out.println("Entered player not in game");
              correctInput = false;
              continue;
            }

            correctInput = true;
          } catch (InputMismatchException e) {
            System.out.println("Invalid input, please enter an integer");
          }
        } while (!correctInput);
        v.voteUp();
      } else {
        int randomAlive = getRandomAlive("");
        while (randomAlive == v.getId()) randomAlive = getRandomAlive("");
        get(randomAlive).voteUp();
      }
    }
    PriorityQueue<Villager> votePq= new PriorityQueue<Villager>(
      aliveCount(), new VoteComparator());
    for (PlayerType player : list) {
      if (!player.isDead()) {
        votePq.add((Villager) player);
      }
    }
    Villager v1 = votePq.poll();
    Villager v2 = votePq.poll();
    if (v1.getVote() == v2.getVote()) {
      System.out.println("There has been a tie! Vote again.");
      return vote(userPlayer);
    }
    return v1.getId();
  }

  public boolean contains(int id) {
    for (PlayerType player : list) {
      if (player.getId() == id) return true;
    }
    return false;
  }

  public int getTotalHP() {
    int sum = 0;
    for (PlayerType player : list) sum += player.getHP();
    return sum;
  }

  public ArrayList<PlayerType> getList() {
    return list;
  }

  public int aliveCount() {
    int count = 0;
    for (PlayerType player : list) count += player.isDead() ? 0 : 1;
    return count;
  }

  public int aliveCount(String playerType) {
    int count = 0;
    for (PlayerType player : list) {
      if (player.getType().equals(playerType) && !player.isDead()) {
        count += 1;
      }
    }
    return count;
  }

  public void printAll(int userId) {
    for (PlayerType player : list) {
      System.out.print(player.toString());
      if (player.getId() == userId) {
        System.out.print(" [user]");
      }
      System.out.print("  ");
    }
    System.out.println();
  }

  public void printRemaining() {
    System.out.print(aliveCount() + " Players remaining: ");
    for (PlayerType player : list) {
      if (!player.isDead()) {
        System.out.print(player.toString() + "  ");
      }
    }
    System.out.println();
  }

  public void printRest(PlayerType player) {
    ArrayList<Integer> otherIds = new ArrayList<Integer>();
    for (PlayerType other : list) {
      if (
        other.getId() != player.getId() &&
        other.getType().equals(player.getType())
      ) {
        otherIds.add(other.getId());
      }
    }
    System.out.print("The other " + player.getType() + "s are: ");
    if (otherIds.size() > 0) {
      for (int id : otherIds) System.out.print("Player " + id + "  ");
    } else {
      System.out.print("No one");
    }
    System.out.println();
  }

  @SuppressWarnings("unchecked")
  public int getRandomAlive(String skipType) {
    ArrayList<PlayerType> clonedList = new ArrayList(list);
    Collections.shuffle(clonedList);
    int i = 0;
    PlayerType currPlayer = clonedList.get(i);
    while (currPlayer.getType().equals(skipType) || currPlayer.isDead()) {
      currPlayer = clonedList.get(++i);
    }
    return currPlayer.getId();
  }

  PlayerList(int count) {
    list = new ArrayList<PlayerType>(count);
    in = new Scanner(System.in);
  }
}

class VoteComparator implements Comparator<Villager> {
  @Override
  public int compare(Villager v1, Villager v2) {
    if (v1.getVote() < v2.getVote())
      return 1;
    else if (v1.getVote() > v2.getVote())
      return -1;
    return 0;
  }
}
