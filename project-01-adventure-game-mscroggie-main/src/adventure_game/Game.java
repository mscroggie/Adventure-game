package adventure_game;

/*
 * Project-01: Adventure Game
 * Name: Mary Ella Scroggie
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import adventure_game.items.HealingPotion;
import adventure_game.items.ManaPotion;

import java.io.FileNotFoundException;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



/**Class Game runs the game */
public class Game {
    static Scanner in = new Scanner(System.in);
    public static Random rand = new Random();
    private static Player player;
    private Room currentRoom;
    private ArrayList<Room> myMap;
    
    public static void main(String[] args)throws IOException{

        Game game = new Game();
        
        
        game.createPlayer();
        System.out.println(game.player.toString());
        
        ArrayList<Room> myMap = game.createMap();
        Room currentRoom = myMap.get(0);
        currentRoom.setLoot();
        currentRoom.setOpponents();
        currentRoom.setOpponent(1);
        System.out.println(currentRoom);
        NPC opponent = currentRoom.opponent;
        System.out.println(opponent.toString());
        
        game.enterCombat(opponent);
        game.explore(currentRoom);

       while(currentRoom.getRoomNum() < 16){
        currentRoom.setLoot();
        currentRoom.setOpponent(Game.rand.nextInt(0,9));
        //System.out.println(myMap.get(currentRoom.getRoomNum()));
        opponent = currentRoom.opponent;
        System.out.println(opponent.toString());
        game.enterCombat(opponent);
        if (player.getHealth() <= 0){
            break;
        }
        game.explore(currentRoom);
        
       }
        in.close();
    }


    public Game() {
        
    }

    /**
     * Creates Arrray List of rooms and all the rooms from the-stilts.txt\n
     * Goes through the file and breaks it into lines, then goes through lines to get roomNum, roomName, and roomDescription\n
     * Then it does the same thing with the doors.
     * @return
     * A list of all the rooms
     * @throws IOException
     */
    public ArrayList<Room> createMap() throws IOException{
        BufferedReader bufReader = new BufferedReader(new FileReader("src/adventure_game/the-stilts.txt"));
        ArrayList<String> listOfLines = new ArrayList<>();
    
        String line = bufReader.readLine();
        while (line != null) {
        if (line.charAt(0) != '#'){
          listOfLines.add(line);
        }
          line = bufReader.readLine();
        }
        
        bufReader.close();
        ArrayList<Room>map = new ArrayList<Room>();
        int i = 0;
        int roomNum;
        String roomName;
        String roomDescription;
        String currRoom;
        int mapSize = Integer.valueOf(listOfLines.get(0));
        listOfLines.remove(0);
        
        int j = 0;
       /*  while (j < 36){
            currRoom = (listOfLines.get(j));
            if (currRoom.charAt(0)=='#'){
                listOfLines.remove(j);
            }
            
            j++;
        }
        */
        
        
        
        while (i<mapSize){
            currRoom = listOfLines.get(i);
            
            String[] myRoom = currRoom.split(": ",3);
            roomNum = Integer.valueOf(myRoom[0]);
            roomName = myRoom[1];
            roomDescription = myRoom[2];         
            map.add(new Room(roomNum, roomName, roomDescription));
            i++;
        }
        j = 16;
        int x = 1;
        while (j<32){
            
            String[] myDoors = listOfLines.get(j).split(": ",5);
            myDoors[1].strip();
            myDoors[2].strip();
            myDoors[3].strip();
            myDoors[4].strip();
            int east = Integer.valueOf(myDoors[1]);
            int north = Integer.valueOf(myDoors[2]);
            int west = Integer.valueOf(myDoors[3]);
            int south = Integer.valueOf(myDoors[4]);
            //System.out.printf("%s, %d, %d, %d, %d,\n",myDoors[0], east, north, west, south);
            map.get(x-1).setDoors(east, north, west, south);
            j++;
            x++;
        }
        return map;
        }
            

    /**
     * distribute points to different attributes */
    public void createPlayer(){
        int points = 20;  
        System.out.print("Name:");
        String username = Game.in.next();
        int myAttack = 0;
        int myHealth = 0;
        int myMana = 0;
        int choice = 0;
        while(points > 0){
        System.out.printf("Assign points to each value.  You have %d left \n", points);
        System.out.printf(" (1) Health: %d\n", myHealth);
        System.out.printf(" (2) Mana: %d\n", myMana);
        System.out.printf(" (3) Base Damage: %d\n", myAttack);
        System.out.println(" (4) Quick Start");
        choice = Game.in.nextInt();
        switch(choice){
            case 1:
                System.out.print("How many points do you want to add to health\n");
                int pointsAdd = Game.in.nextInt();
                points -= pointsAdd;
                myHealth = pointsAdd * 20;
                System.out.printf(" Health: %d\n", myHealth);
                break;
            case 2:
                System.out.print("How many points do you want to add to mana\n");
                 pointsAdd = Game.in.nextInt();
                points -= pointsAdd;
                 myMana = pointsAdd * 3;
                System.out.printf(" Mana: %d\n", myMana*3);
                break;
            case 3:
                System.out.print("How many points do you want to add to Attack\n");
                 pointsAdd = Game.in.nextInt();
                points -= pointsAdd;
                myAttack = pointsAdd;
                System.out.printf(" Attack: %d\n", myAttack);
                break;
            case 4:
                points = 0;
                myHealth = 200;
                myMana = 12;
                myAttack = 10;
                break;
        }

            
        }

        player = new Player(username, myHealth, myMana, myAttack);
        
        player.obtain(new HealingPotion());
        player.obtain(new ManaPotion());
    }
    /**
     * Combat loop
     * 
     * player and opponent take turns until one reaches 0 hp
     * @param opponent is the NPC the player is fighting
     */
    public void enterCombat(NPC opponent){
        System.out.printf("%s and %s are in a brawl to the bitter end.\n", this.player.getName(), opponent.getName());
        
        while(true){
            graphics(player, opponent);
            this.player.takeTurn(opponent);
            if(!opponent.isAlive()){
                System.out.printf("%S is SLAIN!!\n",opponent.getName());
                break;
            }

            opponent.takeTurn(this.player);
            if(!this.player.isAlive()){
                System.out.printf("%S is SLAIN!!\n",this.player.getName());
                break;
            }
        }
    }
    public  void explore(Room room) throws IOException{
        System.out.println("Do you want to...");
        int choice;
        System.out.println( "(1)Search this room?");
        System.out.println( "(2)Go to the next room?");
        myMap = createMap();
        Room nextRoom;
        choice = Game.in.nextInt();
        switch(choice){
            case 1:
                player.search(room);
                
            case 2:
                System.out.println("Which door do you choose?");
                if ((room.getDoor("east")!= -1)){
                    System.out.println("(1) East");
                }
                if ((room.getDoor("north")!= -1)){
                    System.out.println("(2) North");
                }if ((room.getDoor("west")!= -1)){
                    System.out.println("(3) West");
                }if ((room.getDoor("south")!= -1)){
                    System.out.println("(4) South");
                }
                int roomChoice = Game.in.nextInt();
                switch(roomChoice){
                    case 1:
                    if(room.getDoor("east")>=0){
                        System.out.print(this.myMap.get(room.getDoor("east")));
                         currentRoom= this.myMap.get(room.getDoor("east"));
                    } 
                        break;
                    case 2:
                    if(room.getDoor("north")>=0){
                        currentRoom = this.myMap.get(room.getDoor("north"));
                    }
                    break;
                    case 3:
                    if(room.getDoor("west")>=0){
                        currentRoom = this.myMap.get(room.getDoor("west"));
                    }
                    break;
                    case 4:
                    if(room.getDoor("south")>=0){
                        currentRoom = this.myMap.get(room.getDoor("south"));
                    }
                    break;
        }
        System.out.print(currentRoom);
    
       
    }
    }
    /**
     * Makes an ascii graphic inspired by pokemon\n
     * I added this when I asked my sister to test the game and she told me it needed pictures
     * @param player is the player character
     * @param opponent is the opponent character
     */
    public void graphics(Player player,NPC opponent) {
        String output = "";
        output += " -----------------------\n";
        output+="|   ";
        output+= opponent.getName();
        output+= "                |\n";
        output+="|   ";
        output+= opponent.getHealth();
        output+= "                  |\n";
        output += (" ------------------------\n");
        output += ("                                 .'.   \n");
        output += ("                              __/   \\__\n");
        output += ("                               [O O ] \n");
        output += ("                                /''\\  \n");
        output +=( "                               /    \\  \n");
        output +=("\n");
        output +=("\n");
        output+= ("                                      __________________\n");
        output +=("                                     | Do you want to...|\n");
        output += ("       ,___          .-;'             _________________\n");
        output += ("        `'-.`\\_...._/`.`             | (1) Attack?      |\n");
        output += ("   ,       \\        /                | (2) Defend?      |\n");
        output += (" .-' ',    /        \\                | (3) Use item?    |\n");
        output += ("`'._   \\  /          |               | (4) Cast spell?  |\n");
        output += ("    > .' ;,          /                | (5) Charge mana? |\n");  
        output += ("-------------------------");
    
        System.out.printf(output);
    }
}
        
        

    
    
    


