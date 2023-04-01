package adventure_game.items;


import adventure_game.Character;
import adventure_game.Game;

public class ManaPotion implements Consumable {
    public void consume(Character owner){
    int manaRecovered = calculateMana();
    int manaFromMax = owner.getMaxMana() - owner.getMana();
    if (manaRecovered> manaFromMax){
        manaRecovered = manaFromMax;
    }
    System.out.printf("You recover %d mana, back up to %d/%d.\n",manaRecovered, owner.getMana(), owner.getMaxMana());
        owner.modifyMana(manaRecovered);
}
private int calculateMana(){
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
