package adventure_game;
import java.util.ArrayList;

import adventure_game.items.Consumable;
/**
 * returns a character object that can attack and use items
 * Character class
 * This is used both for the player as well as npcs and opponents
 * @param maxHealth the maximum health for the character at this time
 * @param health the characters current health points
 * 
 * @param maxMana the maximum amount of mana the character can have
 * @param mana the current amount of mana for this character
 * 
 * @param baseDamage the base amount of damage this character can deal without any modifiers
 * @param damage is the damge that is done
 * 
 * @param name the name of the character
 */
abstract public class Character{
    private int maxHealth;
    private int health;

    private int maxMana;
    private int mana;

    private int baseDamage;

    private String name;

    private ArrayList<Consumable> items;

    // Character Conditions:
    private int turnsVulnerable; // number of turns Character is vulnerable
    private int turnsInvincible; // number of turns Character takes no damage
    private int turnsStunned; // number of turns Character gets no actions
    // buffer factor for next attack
    // E.g, if 2.0, the next attack will do double damage
    private double tempDamageBuff;
    /**
     * Constructor
     */
    public Character(String name, int health, int mana, int damage){
        this.name = name;
        this.maxHealth = health;
        this.health = health;
        this.maxMana = mana;
        this.mana = mana;
        this.baseDamage = damage;
        this.tempDamageBuff = 1.0;
        items = new ArrayList<Consumable>();
    }

    @Override
    public String toString(){
        String output;
        output = "";
        output += "Name " + getName() + "\n";
        output += "hp " + getHealth() + "\n";
        output += "mana " + getMana() + "\n";
        output += "damage " + getBaseDamage() + "\n";
        return output;
    }

    /**
     * Get the name of this Character
     * @return the name of this Character
     */
    public String getName(){
        return this.name;
    }
    /**@return this characters current health */
    public int getHealth(){
        return this.health;
    }
    /**@return this characters maximum health */
    public int getMaxHealth(){
        return this.maxHealth;
    }
    /**@return this character's current mana */
    public int getMaxMana(){
        return this.maxMana;
    }
    /**@return this character's maximum mana */
    public int getMana(){
        return this.mana;
    }
    /**@return this character's base damage */
    public int getBaseDamage(){
        return this.baseDamage;
    }
    /**
     * @return if this character is alive 
     *  */
    public boolean isAlive(){
        return this.health > 0;
    }

    abstract void takeTurn(Character other);


/**checks if the opponent is currently invincible
 * if not, reduce opponents health by an amount from 80-120% of damage
 * 
 * @param other the character this one is attacking
 */
    public void attack(Character other){
        if(other.isInvincible()){
            System.out.printf("%S is unable to attack %S!\n", 
                                this.getName(), 
                                other.getName());
            other.decreaseTurnsInvincible();
            return;
        }
        double modifier = Game.rand.nextDouble();
        modifier = (modifier*0.4) + 0.8;
        int damage = (int)(this.baseDamage * modifier);
        // apply temporary damage buff, then reset it back to 1.0
        damage *= this.tempDamageBuff;
        this.tempDamageBuff = 1.0;

        if(other.isVulnerable()){
            damage *= 1.5;
            other.decreaseTurnsVulnerable();
        }
       

        System.out.printf("%s dealt %d damage to %s\n", 
                            this.getName(), 
                            damage, 
                            other.getName());
        other.modifyHealth(-damage);
    }

    /**
     * 
     *character has a chance to become invincible for some time and gain a temporary buff
     * in exchnage for not attacking that turn
     * it has a 75% chance of working, if not, character is vulnerable to more damage next turn
     * 
     * @param other the character that this is defending against
     */

    public void defend(Character other){
        double chance = Game.rand.nextDouble();
        if(chance <=0.75){
            System.out.printf("%s enters a defensive posture and charges up their next attack!\n", this.getName());
            this.setAsInvincible(1);
            this.setTempDamageBuff(2.0);
        } else {
            System.out.printf("%s stumbles. They are vulnerable for the next turn!\n", this.getName());
            this.setAsVulnerable(1);
        }
    }
    /**
     *used to both heal and damage character 
     * can only be between 0 and max
     * @param modifier how much the health is increased or decreased
    */
    public void modifyHealth(int modifier) {
        this.health += modifier;
        if(this.health < 0){
            this.health = 0;
        }
        if(this.health > this.getMaxHealth()){
            this.health = this.getMaxHealth();
        }
    }

    /* CONDITIONS */
    /** sets character as vulnerable for a number of turns
     * @param numTurns the number of turns the character is vulnerable
     * if vulnerable the character will take more damage when attacked
     */
    public void setAsVulnerable(int numTurns){
        this.turnsVulnerable = numTurns;
    }
    /**@return if character is vulnerable*/
    public boolean isVulnerable(){
        return this.turnsVulnerable > 0;
    }
    /** decreases the number of turns the vulnerable condition is applied */
    public void decreaseTurnsVulnerable(){
        this.turnsVulnerable--;
    }
    /** sets character as invincible for a number of turns
     * 
     * if character is invincible it cannot take damage
     * @param numTurns the number of turns the character is invincible
     * 
     * 
     */
    public void setAsInvincible(int numTurns){
        this.turnsInvincible = numTurns;
    }
    /**@return true if character is invincible*/
    
    public boolean isInvincible(){
        return this.turnsInvincible > 0;
    }
    /** decreases the number of turns the invincible condition is applied */
    public void decreaseTurnsInvincible(){
        this.turnsInvincible--;
    }
   /** sets character as stunned for a number of turns
    * if the character is stunned it cannot do anyh actions this turn
     * @param numTurns the number of turns the character is stunned
     * 
     */
    public void setAsStunned(int numTurns){
        this.turnsStunned = numTurns;
    }
   /**@return true if character is stunned*/
    public boolean isStunned(){
        return this.turnsStunned > 0;
    }
    /** decreases the number of turns the character is stunned */
    public void decreaseTurnsStunned(){
        this.turnsStunned--;
    }

    /**
     * Set the temporary damage buffer. 
     * 
     * This is a multiplicative factor which will modify the damage 
     * for the next attack made by this Character. After the next 
     * attack, it will get reset back to 1.0
     * 
     * @param buff the multiplicative factor for the next attack's
     * damage.
     */
    public void setTempDamageBuff(double buff){
        this.tempDamageBuff = buff;
    }
    /**Adds item to characters inventory */
    public void obtain(Consumable item){
        items.add(item);
    }
    /** useItem
     * 
     * Uses consumable item in inventory
     * 
     * takes the owner of the item and the character the item will be used on
     * gets player input to determine which item will be used
     * 
     * each item can only be used once in game and will be removed from inventory once used
     * @param owner the character using the item
     * @param other the chaaracter the item will be used on
     */
    public void useItem(Character owner, Character other){
        int i = 1;
        System.out.printf("  Do you want to use:\n");
        for(Consumable item : items){
            System.out.printf("    %d: %S\n", i, item.getClass().getName());
            i++;
        }
        System.out.print("  Enter your choice: ");
        int choice = Game.in.nextInt();
        items.get(choice-1).consume(owner);
        items.remove(choice-1);
    }
/**checks to see if character has items, if not returns false 
 * @return if character has items
*/
    public boolean hasItems(){
        return !items.isEmpty();
    }
}
