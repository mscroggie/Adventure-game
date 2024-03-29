package adventure_game;
    /**
     * 
     * Player character
     * actions are determined by users numerical input
     * Takes in the other character which will be affected by the players turn
     * Right now: can attack, defend, and use items
     * 
     * 
     */
public class Player extends Character{
    public Player(String name, int health, int mana, int baseDamage){
        super(name, health, mana, baseDamage);
    }
    /**
     *The players actions are dictated by numerical input
     *
     */
    @Override
    public void takeTurn(Character other){
        if(this.isStunned()){
            this.decreaseTurnsStunned();
            System.out.printf("%S is unable to take any actions this turn!", this.getName());
            return;
        }
        System.out.println();
        System.out.printf("----------------------------\n");
        System.out.printf("|%s has %d of %d health.|\n", this.getName(), this.getHealth(), this.getMaxHealth());
        System.out.printf("|%s has %d of %d mana.    |\n", this.getName(), this.getMana(), this.getMaxMana());
        System.out.printf("---------------------------\n");
        /*System.out.printf("%s has %d health.\n", other.getName(), other.getHealth());
        System.out.printf("-------------------\n");
        System.out.printf("|Do you want to...|\n");
        System.out.printf("-------------------\n");
        System.out.printf("|  1: Attack?     |\n");
        System.out.printf("|  2: Defend?     |\n");
        if(this.hasItems())
            System.out.printf("|  3: Use an item?|\n");
        if (this.getMana() > 0){
            System.out.print("|  4: Cast a spell|\n");
            
        }
        System.out.printf("-------------------\n");
        System.out.printf("Enter your choice: ");
        */

        int choice = Game.in.nextInt();
        switch(choice){
            case 1:
                this.attack(other);
                break;
            case 2:
                this.defend(other);
                break;
            case 3:
                if(hasItems()){
                    this.useItem(this, other);
                } else {
                    System.out.println("You dig through your bag but find no items. You lose a turn!!");
                }
                break;
            case 4:
                if (this.getMana() >0){
                    this.castSpell(other);
                }else{
                    System.out.println("You are out of mana!");
                }

                break;
            case 5:
                this.modifyMana(1);
            
        }
    }
    public void search(Room room){
        this.obtain(room.loot);
        System.out.printf("You found %s\n", room.loot.getClass().getName());
    }
}