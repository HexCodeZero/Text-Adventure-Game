import java.util.*;
import java.io.*;
import java.lang.*;

class Game {
  //Input
  private Scanner sc = new Scanner(System.in);
  //Positions and map
  Tile[][] map;
  private int MAP_COLS = 28;
  private int MAP_ROWS = 19;
  //The player
  Player player;
  //Random
  Random rand = new Random();

  public Game() {
    initGame();
  }

  public void initGame() {
    //Initialize the map
    File inFile;
    Scanner in;
    try {
      inFile = new File("map.txt");
      in = new Scanner(inFile);
      int MAP_ROWS = in.nextInt();
      int MAP_COLS = in.nextInt();
      in.nextLine();
      map = new Tile[MAP_ROWS][MAP_COLS];
      for(int i=0; i<MAP_ROWS; i++) {
        String[] line = in.nextLine().split(" ");
        for(int j=0; j<line.length; j++) {
          map[i][j] = new Tile( Integer.parseInt(line[j]) );
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }

    //Initialize the player
    System.out.println("Welcome to the Legend of Gandor.");
    System.out.println("Enter your name, adventurer: ");
    player = new Player(sc.nextLine(), 10, 1, 1, 24, 3, MAP_ROWS, MAP_COLS);

    //Begin game loop
    System.out.println("Welcome, " + player.getName() + ". You have arrived and have been told of a great chalice that holds immeasurable wealth. It is locked behind a gate that needs a key. The key lies ontop a podium in a small castle. Find it and use it to unlock the chalice gate, which is located in a large castle surrounded by hills. Take the chalice and head to the mountains and look for a tunnel to escape. Good luck. Type help for help... obviously. Also, watch out for monsters. Your journey begins now.");
    gameLoop();
  }

  public void gameLoop() {
    System.out.print("\n> ");
    String cmd = sc.nextLine();
    processCommand(cmd);
    gameLoop();
  }

  public void processCommand(String cmd) {
    if(cmd == null) {
      return;
    }
    String[] cmds = cmd.toLowerCase().split(" ");
    if(cmds[0].equals("go")) {
      if(cmds.length != 1) {
        if(cmds[1].equals("n") || cmds[1].equals("north")) {
          player.goNorth();
        } else if (cmds[1].equals("s") || cmds[1].equals("south")) {
          player.goSouth();
        } else if (cmds[1].equals("e") || cmds[1].equals("east")) {
          player.goEast();
        } else if (cmds[1].equals("w") || cmds[1].equals("west")) {
          player.goWest();
        }
        if(!map[player.getXPos()][player.getYPos()].hasBeenHere) {
          observeArea();
        }
          
      } else {
        System.out.println("Go where?");
      }
    } else if (cmds[0].equals("inventory") || cmds[0].equals("inv")) {
      player.printInventory();
    } else if (cmds[0].equals("stats")) {
      player.printStats();
    } else if(cmds[0].equals("look")) {
      observeArea();
    } else if(cmds[0].equals("take")) {
      if(cmds.length != 1) {
        if(cmds[1].equals("chalice")) {
          player.collectItem("chalice");
          if(player.hasChalice) {
            map[player.getXPos()][player.getYPos()].removeItem("chalice");
          }
        } else if(map[player.getXPos()][player.getYPos()].removeItem(cmds[1])) {
            player.collectItem(cmds[1]);
          } else {
            System.out.println("There is no such thing here.");
          }
      } else {
        System.out.println("Take what?");
      }
    } else if(cmds[0].equals("equip") && cmds.length != 1) {
      player.equipItem(cmds[1]);
    } else if(cmds[0].equals("unequip") && cmds.length != 1) {
      player.unequipItem(cmds[1]);
    } else if(cmds[0].equals("help")) {
      System.out.println("go (n/e/s/w) (north/east/south/west), inventory, inv, stats, look, use (item), take (item), equip (item), unequip (item), map, leave, help");
    } else if(cmds[0].equals("map")) {
      System.out.println("You pull out your convenient map and take a look.");
      printMap();
    } else if(cmds[0].equals("leave")) {
      if(map[player.getXPos()][player.getYPos()].getId() == 16) {
        if(player.hasChalice) {
          completeGame();
        } else {
          System.out.println("You came for the chalice. You are not leaving without it.");
        }
      } else {
        System.out.println("You must be at the tunnel in the mountains to leave. The mountains are south-west.");
      }
    } else if(cmds[0].equals("use") && cmds.length != 1) {
      player.useItem(cmds[1]);
    }
    if(map[player.getXPos()][player.getYPos()].isMonsterHere){
      battle();
    }
    map[player.getXPos()][player.getYPos()].hasBeenHere = true;
  }

  public void observeArea() {
    System.out.println("You are now at " + map[player.getXPos()][player.getYPos()].getDescription());
    map[player.getXPos()][player.getYPos()].itemsHere();
  }

  public void printMap() {
    for(int i=0; i<MAP_ROWS; i++) {
      for(int j=0; j<MAP_COLS; j++) {
        if(i==player.getXPos() && j ==player.getYPos()) {
          System.out.print(" x");
        }
        else if(map[i][j].hasBeenHere) {
          System.out.print(" #");
        } else {
          System.out.print("  ");
        }
      }
      System.out.println();
    }
  }

  public void battle() {
    boolean playerTurn = true;
    map[player.getXPos()][player.getYPos()].isMonsterHere = false;
    System.out.println("You walk straight into a monster. Time for a brawl!");
    int monsterHealth = rollDice();
    System.out.println("The monster has " + monsterHealth + " health.");
    String line;
    System.out.print("* ");
    line = sc.nextLine();
    while(monsterHealth > 0 && player.getHealth() > 0) {
      if(playerTurn) {
        int damage = player.getAttack() + rollSmallDice();
        monsterHealth -= damage;
        System.out.println("You deal " + damage + " damage.");
        System.out.println("The monster has " + monsterHealth + " health.");
        System.out.print("* ");
        line = sc.nextLine();

        playerTurn = false;
      } else {
        int monsDamage = rollSmallDice() - player.getDefense();
        if(monsDamage > 0) {
          player.changeHealth(-monsDamage);
          System.out.println("The beast deals " + monsDamage + " damage.");
        } else {
          System.out.println("The beast fails to pierce your defense");
        }
        System.out.println("You have " + player.getHealth() + " health.");
        System.out.print("* ");
        line = sc.nextLine();

        playerTurn = true;
      }
    }
    if(monsterHealth <= 0) {
      System.out.println("You defeat the monster.");
    } else if(player.getHealth() <= 0) {
      gameOver();
    }
    map[player.getXPos()][player.getYPos()].isMonsterHere = false;
  }

  public void completeGame() {
    System.out.println("You head out... the chalice is yours.");
    System.out.println("Congratulations! Thank you for playing. This was a game by Hasan Gandor.");
    while(true) {
      String done = sc.nextLine();
    }
  }

  public void gameOver() {
    System.out.println("You succumb to your injuries from the monster and die. Game over.");
    while(true) {
      String done = sc.nextLine();
    }
  }

  public int rollDice() {
    return (int)(Math.random()*20) + 1;
  }

  public int rollSmallDice() {
    return (int)(Math.random()*6) + 1;
  }

}