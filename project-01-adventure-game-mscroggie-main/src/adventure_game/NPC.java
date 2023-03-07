package adventure_game;
public class NPC extends Character{

    /**
     * NPC extends character
     * Refers to character entities that are not controlled by the player
     * Right now, only can be stunned or attack the player
     * 
     * same type as player, and an potentially do the same things
     */

    public NPC(String name, int health, int mana, int baseDamage){
        super(name, health, mana, baseDamage);
    }
    /** takeTurn
     * takes in the other character 
     * CHecks if its stunned, if not it proceeds to attack
     * 
     */

    @Override
    public void takeTurn(Character other){
        if(this.isStunned()){
            this.decreaseTurnsStunned();
            System.out.printf("%S is unable to take any actions this turn!", this.getName());
            return;
        }
        this.attack(other);
    }
}