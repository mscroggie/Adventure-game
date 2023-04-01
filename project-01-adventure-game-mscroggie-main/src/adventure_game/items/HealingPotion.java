package adventure_game.items;

import adventure_game.Character;
import adventure_game.Game;


/** implements Consumable. 
*Replenishes health points to Character with a chance of up to full health and
 * prints out how much health you have now
 */
public class HealingPotion implements Consumable {
    public void consume(Character owner){
        int hitPoints = calculateHealing();
        int hitPointsFromMax = owner.getMaxHealth() - owner.getHealth();

        if(hitPoints > hitPointsFromMax){
            hitPoints = hitPointsFromMax;
        }
        System.out.printf("You heal for %d points, back up to %d/%d.\n", hitPoints, owner.getHealth(), owner.getMaxHealth());
        owner.modifyHealth(hitPoints);
    }

    private int calculateHealing(){
        // Equivalent to rolling 4d4 + 4
        // sum up four random values in the range [1,4] and
        // add 4 to that.
        int points = Game.rand.nextInt(4)+1;
        points += Game.rand.nextInt(4)+1;
        points += Game.rand.nextInt(4)+1;
        points += Game.rand.nextInt(4)+1;
        return points + 4;
    }
}
