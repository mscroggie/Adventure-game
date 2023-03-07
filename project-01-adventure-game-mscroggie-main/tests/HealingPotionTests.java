package tests;
import adventure_game.Character;
import adventure_game.Player;
import adventure_game.HealingPotion;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class HealingPotionTests {
    private Character c;
    
    @BeforeEach
    void setup(){
        
        c = new Player("Hero", 100, 9, 7);
        c.obtain(new HealingPotion());
    }

    @Test
    void testHealingPotion(){
        assertTrue(c.getHealth() == 50 );
        c.consume(healingPotion());
        assertTrue(c.getHealth() <= 100 );
    }
}
