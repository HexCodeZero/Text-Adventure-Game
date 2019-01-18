import java.util.*;

class Player {
  private String name;
  private int health;
  private int attack;
  private int defense;
  private ArrayList<String> inventory = new ArrayList<String>();
  
  private int xPos;
  private int yPos;

  private final int mapRows;
  private final int mapCols;

  private boolean isSwordEquipped = false;
  private boolean isHelmetEquipped = false;
  public boolean hasChalice = false;

  public Player(String name, int hp, int at, int def, int x, int y, int mapR, int mapC) {
    this.name = name;
    health = hp;
    attack = at;
    defense = def;
    xPos = x;
    yPos = y;
    mapRows = mapR;
    mapCols = mapC;
  }
  public String getName() {
    return name;
  }
  public int getHealth() {
    return health;
  }
  public int getAttack() {
    return attack;
  }
  public int getDefense() {
    return defense;
  }
  public void changeHealth(int change){ //positive for heal, negative for damage
    health += change;
  }

  public void printInventory() {
    if(inventory.size() == 0) {
      System.out.println("You have nothing in your inventory.");
      return;
    }
    System.out.println("You check your inventory.");
    for(String item : inventory) {
      System.out.println("You have a " + item);
    }
  }

  public void printStats() {
    System.out.println("Here are your stats: ");
    System.out.println("Health: " + health);
    System.out.println("Attack: " + attack);
    if(isSwordEquipped){
      System.out.println("- You have a sword equipped.");
    }
    System.out.println("Defense: " + defense);
    if(isHelmetEquipped) {
      System.out.println("- You have a helmet on.");
    }
    
  }

  public boolean hasItem(String item) {
    if(inventory.contains(item)) {
      return true;
    } else {
      return false;
    }
  }

  public void collectItem(String item) {
    if(item.equals("chalice")) {
      if(inventory.contains("key") && !hasChalice) {
        System.out.println("You pick up the chalice! Now get out of here.");
        inventory.add(item);
        hasChalice = true;
      } else {
        System.out.println("The gate needs a key to unlock.");
      }
    } else {
      System.out.println("You collect the " + item);
      inventory.add(item);
    }
  }

  public void equipItem(String item) {
    if(inventory.contains(item) && item.equals("sword") && !isSwordEquipped) {
      inventory.remove(item);
      isSwordEquipped = true;
      System.out.println("You brandish the sword.");
      attack += 5;
    } else if(inventory.contains(item) && item.equals("helmet") && !isHelmetEquipped) {
        inventory.remove(item);
        isHelmetEquipped = true;
        System.out.println("You place the helmet on your head. A tight fit.");
        defense += 3;
      }
    else {
      System.out.println("You are unable to equip this.");
    }
  }

  public void unequipItem(String item) {
    if(item.equals("sword") && isSwordEquipped) {
      System.out.println("You unequip the sword");
      inventory.add("sword");
      isSwordEquipped = false;
      attack -= 5;
    } else if(item.equals("helmet") && isHelmetEquipped) {
      System.out.println("You take off your helmet.");
      inventory.add("helmet");
      isHelmetEquipped = false;
      defense -= 3;
    } else {
      System.out.println("You arent wielding any such thing");
    }
  }

  public void useItem(String item) {
    if(hasItem(item)) {
      if(item.equals("potion")) {
        changeHealth(5);
        System.out.println("You drink the potion, gaining health");
        inventory.remove(item);
      }
    }
  }
  
  //MOVEMENT
  public int getXPos() {
    return yPos;
  }
  public int getYPos() {
    return xPos;
  }
  
  public void goNorth() {
    if(yPos-1>=0) {
      yPos-=1;
      System.out.println("You head north.");
    } else {
      System.out.println("There is a wall in your way.");
    }
  }

  public void goSouth() {
    if(yPos+1<mapRows) {
      yPos+= 1;
      System.out.println("You head south.");
    } else {
      System.out.println("There is a wall in your way.");
    }
  }

  public void goEast() {
    if(xPos+1<mapCols) {
      xPos+=1;
      System.out.println("You head east.");
    } else {
      System.out.println("There is a wall in your way.");
    }
  }

  public void goWest() {
    if(xPos-1>=0) {
      xPos-=1;
      System.out.println("You head west.");
    } else {
      System.out.println("There is a wall in your way.");
    }
  }

  public void debug() {
    System.out.println("xpos=" + xPos + " ypos=" + yPos);
    System.out.println("maprow=" + mapRows + " mapcol=" + mapCols);
  }
}