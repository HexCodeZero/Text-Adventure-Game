import java.util.*;
import java.io.*;

class Tile {
  private int id;
  private String description;
  private ArrayList<String> items = new ArrayList<String>();
  public boolean hasBeenHere = false;

  private Random rand = new Random(); 

  public boolean isMonsterHere = false;

  public Tile(int num) {
    id = num;
    if(id == 1) { //taiga
      description = "an extremely cold taiga covered with snow and pine trees.";
    } else if(id == 2) { //rainforest
      description = "a dense rainforest with wild animals running about.";
    } else if(id == 3) { //tundra
      description = "an unforgiving tundra that spans as far as the eye can see.";
    } else if(id == 4)  { //grassland -- low chance monster
      description = "some peaceful and quiet grasslands.";
    } else if(id == 5) { //desert
      description = "a barren desert few have crossed.";
    } else if(id == 6) { //pyramid -- high chance of monster
      description = "a pyramid hidden within the dunes of the desert.";
    } else if(id == 7) { //savannah
      description = "a vast savannah covered by shrubs and lonely trees.";
    } else if(id == 8) { //wasteland -- high chance of monster
      description = "what used to be a village, but now is a wasteland.";
    } else if(id == 9) { //volcano -- high chance of monster
      description = "a dangerous volcano that could go off at any moment.";
    } else if(id == 10) { //mountains
      description = "towering mountains that overlook the land.";
    } else if(id == 11) { //lake
      description = "a small pond near a small castle home to colonies of algae and small fish.";
    } else if(id == 12) { //hills
      description = "some rolling hills surrounding a grand castle.";
    } else if(id == 13) { //grand castle
      description = "a large castle with great treasure.";
    } else if(id == 14) { //normal castle
      description = "a small castle that doesn't hold much.";
    } else if(id == 15) { //chalice gate in grand castle
      description = "the chalice gate.";
      items.add("chalice");
    } else if(id == 16) { //exit
      description = "a tunnel through the mountains that leads to the exit.";
    } else if(id == 17) {
      description = "a small podium within the small castle.";
      items.add("key");
    }
    else {
      description = "... where am I?";
    }
    if(id != 15 || id != 17 || id != 11 || id != 16) {
      int roll = rollDice();
      if(roll==3 || roll==4) {
        items.add("sword");
      } else if(roll==5 || roll==6) {
        items.add("helmet");
      }
      if(roll > 8  && roll < 13) {
        items.add("potion");
      }
      if(id==6 || id==8 || id==9) {
        roll+= 5;
      } else if(id==4) {
        roll-= 5;
      }
      if(roll > 38) {
        isMonsterHere = true;
      }
    }
  }

  public String getDescription() {
    return description;
  }

  public int getId() {
    return id;
  }

  public void itemsHere() {
    if(items.size()==0) {
      System.out.println("Theres nothing here.");
    } else {
      for(String item : items) {
        System.out.println("There's a " + item + " on the floor.");
      }
    }
  }

  public boolean removeItem(String item) {
    return items.remove(item);
  }

  private int rollDice() {
    return (int)(Math.random()*45+1); 
  }

}