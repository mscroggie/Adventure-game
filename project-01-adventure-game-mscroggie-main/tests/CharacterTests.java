package tests;

import adventure_game.Character;
import adventure_game.Player;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class CharacterTests{

    private Character c;
    
    @BeforeEach
    void setup(){
        c = new Player("Hero", 100, 9, 7);
    }

    @Test
    void testModifyHealth(){
        assertTrue(c.getHealth() == 100);
        c.modifyHealth(-10);
        assertTrue(c.getHealth() == 90);
    }
    @Test
    void testAttack(){
        assertTrue(c.getHealth() == 100);
        c.attack(c);
        assertTrue(c.getHealth() <= 100);

    }
    @Test
    void testSetAsInvincible(){
        assertFalse(c.isInvincible());
        c.setAsInvincible(1);;
        assertTrue(c.isInvincible()); 
    }
    @Test
    void testSetAsVulnerable(){
        assertFalse(c.isVulnerable);
        c.setAsVulnerable(1);
        assertTrue(c.isVulnerable);
    }
    @Test
    void testSetAsStunned(){
        assertFalse(c.isStunned);
        c.setAsStunned(1);
        assertTrue(c.isStunned);
    }
    @Test
    void testObtain(){
        assertFalse(c.hasItems());
        c.obtain(item);
        assertTrue(c.hasItems());

    }
    @Test testUseItem(){
        assertTrue(c.hasItems());
        c.useItem();
        assertFalse(c.hasItems)();
    }

}