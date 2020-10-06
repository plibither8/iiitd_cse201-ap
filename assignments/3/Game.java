import java.util.*;

public class Game {
  private static PlayerList<Villager> villagers;
  private static PlayerList<Mafia> mafias;
  private static PlayerList<Detective> detectives;
  private static PlayerList<Commoner> commoners;
  private static PlayerList<Healer> healers;
  private static Villager userPlayer;
  private static Scanner in;

  private static int selectTarget(String playerType) {
    if (villagers.aliveCount(playerType) == 0) {
      return -1;
    }

    int target;
    boolean correctInput = false;
    Villager v = null;
    if (!userPlayer.isDead() && userPlayer.getType().equals(playerType)) {
      target = 0;
      do {
        try {
          userPlayer.printPrompt();
          target = in.nextInt();
          v = villagers.get(target);
          if (v.getType().equals(playerType) && !playerType.equals("Healer")) {
            System.out.println("You cannot select one of your own kind.");
            correctInput = false;
            continue;
          }
          if (v == null || v.isDead()) {
            System.out.println("Entered player not in game");
            correctInput = false;
            continue;
          }
          correctInput = true;
        } catch (InputMismatchException e) {
          System.out.println("Invalid input, please enter an integer");
        }
        in.nextLine();
      } while (!correctInput);
    } else {
      target = villagers.getRandomAlive(playerType.equals("Healer") ? "" : playerType);
    }
    return target;
  }

  private static int aliveMafiaCount() {
    int count = 0;
    for (Mafia mafia : mafias.getList()) {
      if (mafia.getHP() > 0) count++;
    }
    return count;
  }

  private static void applyMafiaDamage(int damage) {
    int aliveMafias = aliveMafiaCount();
    ArrayList<Mafia> list = mafias.getList();
    boolean callAgain = false;
    for (int i = 0; i < list.size(); i++) {
      Mafia mafia = list.get(i);
      if (
        mafia.getHP() > 0 &&
        mafia.getHP() < (int) (damage / aliveMafias)
      ) {
        damage -= mafia.getHP();
        mafia.setHP(0);
        callAgain = true;
      }
    }
    if (callAgain) {
      applyMafiaDamage(damage);
    } else {
      for (int i = 0; i < list.size(); i++) {
        Mafia mafia = list.get(i);
        if (mafia.getHP() > 0) {
          mafia.setHP((int) (damage / aliveMafias));
        }
      }
    }
  }

  private static void printStartMenu() {
    String text = "\nChoose a character\n"
      + "1. Mafia\n"
      + "2. Detective\n"
      + "3. Healer\n"
      + "4. Commoner\n"
      + "5. Assign randomly";
    System.out.println(text);
  }

  public static void main(String[] args) {
    in = new Scanner(System.in);

    System.out.println("Welcome to Mafia\n");
    int villagerCount = 0;

    do {
      try {
        System.out.print("Enter number of villagers: ");
        villagerCount = in.nextInt();
        if (villagerCount < 6) {
          System.out.println("Please enter an integer > 5");
        }
      } catch (InputMismatchException e) {
        System.out.println("Invalid input, please enter an integer > 5");
      }
      in.nextLine();
    } while (villagerCount < 6);

    int healerCount = villagerCount < 10 ? 1 : villagerCount / 10;
    int mafiaCount = villagerCount / 5;
    int detectiveCount = villagerCount / 5;
    int commonerCount = villagerCount - healerCount - mafiaCount - detectiveCount;

    villagers = new PlayerList<Villager>(villagerCount);
    mafias = new PlayerList<Mafia>(mafiaCount);
    detectives = new PlayerList<Detective>(detectiveCount);
    commoners = new PlayerList<Commoner>(commonerCount);
    healers = new PlayerList<Healer>(healerCount);

    printStartMenu();
    int choice = 0;
    do {
      try {
        System.out.print("Select choice: ");
        choice = in.nextInt();
        if (choice > 5 || choice < 1) {
          System.out.println("Please enter an integer < 6 and > 0");
        }
      } catch (InputMismatchException e) {
        System.out.println("Please enter an integer < 6 and > 0");
      }
      in.nextLine();
    } while (choice > 5 || choice < 1);

    if (choice == 5) {
      choice = (int) Math.floor(Math.random() * 5);
    }

    ArrayList<Integer> playerIds = new ArrayList<Integer>(villagerCount);
    for (int i = 1; i <= villagerCount; i++) playerIds.add(i);
    Collections.shuffle(playerIds);

    int playerCount = 0;
    int playerId = playerIds.get(playerCount++);

    // assign user
    switch (choice) {
      case 1: {
        Mafia user = new Mafia(playerId);
        userPlayer = (Villager) user;
        mafias.add(user);
        villagers.add(user);
        mafiaCount--;
        break;
      }
      case 2: {
        Detective user = new Detective(playerId);
        userPlayer = (Villager) user;
        detectives.add(user);
        villagers.add(user);
        detectiveCount--;
        break;
      }
      case 3: {
        // USER CANNOT CHOOSE HEALER IF HEALER COUNT 0
        Healer user = new Healer(playerId);
        userPlayer = (Villager) user;
        healers.add(user);
        villagers.add(user);
        healerCount--;
        break;
      }
      case 4: {
        Commoner user = new Commoner(playerId);
        userPlayer = (Villager) user;
        commoners.add(user);
        villagers.add(user);
        commonerCount--;
        break;
      }
      default: {
        // ERROR
      }
    }

    // assign rest of the players
    while (mafiaCount-- > 0) {
      playerId = playerIds.get(playerCount++);
      Mafia mafia = new Mafia(playerId);
      mafias.add(mafia);
      villagers.add(mafia);
    }
    while (detectiveCount-- > 0) {
      playerId = playerIds.get(playerCount++);
      Detective detective = new Detective(playerId);
      detectives.add(detective);
      villagers.add(detective);
    }
    while (healerCount-- > 0) {
      playerId = playerIds.get(playerCount++);
      Healer healer = new Healer(playerId);
      healers.add(healer);
      villagers.add(healer);
    }
    while (commonerCount-- > 0) {
      playerId = playerIds.get(playerCount++);
      Commoner commoner = new Commoner(playerId);
      commoners.add(commoner);
      villagers.add(commoner);
    }

    System.out.println("You are " + userPlayer.toString());
    System.out.println("You are a " + userPlayer.getType());

    if (!(userPlayer instanceof Commoner)) {
      villagers.printRest(userPlayer);
    }

    int roundCount = 0;

    int mafiaAliveCount = mafias.aliveCount();
    int restAliveCount = villagers.aliveCount() - mafiaAliveCount;

    while (mafias.aliveCount() > 0 && mafiaAliveCount < restAliveCount) {
      System.out.println("\n\n--Round " + (++roundCount) + "--");
      villagers.printRemaining();

      int targetId = selectTarget("Mafia");
      System.out.println("The Mafia have selected their target");

      int testId = selectTarget("Detective");

      if (userPlayer instanceof Detective) {
        Villager testPlayer = villagers.get(testId);
        if (mafias.contains(testId)) {
          System.out.println(testPlayer.toString() + " is a mafia");
        } else {
          System.out.println(testPlayer.toString() + " is not a mafia");
        }
      } else {
        System.out.println("Detectives have tested someone");
      }

      int healedId = selectTarget("Healer");
      System.out.println("Healers have chosen someone to heal");

      System.out.println("\n\n--End of actions--");

      Villager targetPlayer = villagers.get(targetId);
      int targetHP = targetPlayer.getHP();

      int totalMafiaHP = mafias.getTotalHP();
      if (totalMafiaHP >= targetHP) {
        targetPlayer.setHP(0);
      } else {
        targetPlayer.setHP(targetHP - totalMafiaHP);
      }

      applyMafiaDamage(targetHP);

      if (healedId > 0) {
        villagers.get(healedId).heal();
      }

      if (targetPlayer.getHP() == 0) {
        targetPlayer.kill();
        System.out.println(targetPlayer.toString() + " has died.");
      }

      int voteOut;

      if (mafias.contains(testId)) {
        voteOut = testId;
      } else {
        voteOut = villagers.vote(userPlayer);
      }

      Villager voteOutPlayer = villagers.get(voteOut);
      voteOutPlayer.kill();
      System.out.println(voteOutPlayer.toString() + " has been voted out");

      mafiaAliveCount = mafias.aliveCount();
      restAliveCount = villagers.aliveCount() - mafiaAliveCount;

      System.out.println("--End of Round " + roundCount + "--");
    }

    System.out.println("Game over.");

    if (mafiaAliveCount > 0) {
      System.out.println("The Mafias have won");
    } else {
      System.out.println("The Mafias have lost");
    }

    System.out.print("Mafia: ");
    mafias.printAll(userPlayer.getId());

    System.out.print("Detectives: ");
    detectives.printAll(userPlayer.getId());

    System.out.print("Healers: ");
    healers.printAll(userPlayer.getId());

    System.out.print("Commoners: ");
    commoners.printAll(userPlayer.getId());

    in.close();
  }
}
