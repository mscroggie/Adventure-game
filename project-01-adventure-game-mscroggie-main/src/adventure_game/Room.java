package adventure_game;
import java.util.ArrayList;

import adventure_game.items.Consumable;
import adventure_game.items.HealingPotion;
import adventure_game.items.ManaPotion;
public class Room {
    private String name;
    private String description;
    public Consumable loot;
    private int  roomNum;
    private int eastDoor;
    private int northDoor;
    private int westDoor;
    private int southDoor;
    private ArrayList<NPC> npcList = new ArrayList<NPC>();
    public NPC opponent;
    public Room(int roomNum, String name, String description){
        this.roomNum = roomNum;
        this.name = name;
        this.description = description;
        this.loot = loot;
        
        
    }
    @Override
    public String toString(){
        String output = "";
        output += " Room: " + this.getName() + "\n";
        output += this.getDescription() + "\n";
        return output;
    }
    public void setLoot(){
        int item = Game.rand.nextInt(2);
        if (item == 1){
        loot = new HealingPotion();
        }
        else{
            loot = new ManaPotion();
        }
    }
/**
 * makes list of possible opponents to randomly appear in the room
 */
public void setOpponents(){
    NPC opponent = new NPC("Geoff", 200, 20 ,20);
    npcList.add(opponent);
    opponent = new NPC("Slime", 50,0,10);
    opponent.setWeakness("Water");
    npcList.add(opponent);
    opponent = new NPC("Slime", 50,0,10);
    opponent.setWeakness("Electric");
    npcList.add(opponent);
    opponent = new NPC("Slime", 50,0,10);
    opponent.setWeakness("Ice");
    npcList.add(opponent);
    opponent = new NPC("Slime", 50,0,10);
    opponent.setWeakness("Fire");
    npcList.add(opponent);
    opponent = new NPC("Goblin", 200,0,25);
    opponent.setWeakness("Fire");
    npcList.add(opponent);
    opponent = new NPC("The Cube(tm)", 500,20,50);
    opponent.setWeakness("Electric");
    npcList.add(opponent);
    opponent = new NPC ("empty", 0,0,0);
    npcList.add(opponent);
    opponent = new NPC ("empty", 0,0,0);
    npcList.add(opponent);
}
public void setOpponent(int monster){

    this.opponent = npcList.get(monster);
    
}
/**
 * Sets where each door leads
 * @param east room to the easr
 * @param north room to the north
 * @param west room to the west
 * @param south rooom to the south
 */
    public void setDoors(int east, int north, int west, int south){
        this.eastDoor= east;
        this.westDoor = west;
        this.northDoor = north;
        this.southDoor = south;
    }
/**
 * Returns which room is behind which door\n
 * returns -1 if there is no room.
 * @param direction the side of the room where the door is
 * @return the room behind th edoor
 */
    public int getDoor(String direction){
        if (direction == "east"){
            if (this.eastDoor >=0){
                return this.eastDoor;
                
            }
        }
        else if (direction == "north"){
            if (this.northDoor >=0){
                return this.northDoor;
            }
        }
        else if (direction == "west"){
            if (this.westDoor >=0){
                return this.westDoor;
            }
        }
        else if (direction == "south"){
            if (this.southDoor >=0){
                return this.southDoor;
            }
        }
        return -1;

        
        
    }

    /** 
    * Get the name of this room
    * @return the name of this room
    */
   public String getName(){
       return this.name;
   }
   /**
    * Get the description of this room
    @return the description of this room
    */
    public String getDescription(){
        return this.description;
    }
    public int getRoomNum(){
        return this.roomNum;
    }
}
