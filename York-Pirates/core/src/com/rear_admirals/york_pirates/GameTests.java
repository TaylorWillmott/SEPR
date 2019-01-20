package com.rear_admirals.york_pirates;

import com.rear_admirals.york_pirates.screen.sailing.Ship;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameTests {

    @Test
    void addGoldTest(){
        Player testPlayer = new Player(new Ship());
        int oldGold = testPlayer.getGold();
        testPlayer.addGold(10);
        assertEquals(oldGold+10, testPlayer.getGold(),"Player's gold must increase by 10");
    }

    @Test
    void successfulPayGoldTest(){
        Player testPlayer = new Player(new Ship());
        testPlayer.setGold(50);
        assertTrue(testPlayer.payGold(10), "Function should return true in this case.");
        assertEquals(40, testPlayer.getGold(), "Player should now have 40 gold.");
    }

    @Test
    void failedPayGoldTest(){
        Player testPlayer = new Player(new Ship());
        testPlayer.setGold(10);
        assertFalse(testPlayer.payGold(50), "Function should return false in this case.");
        assertEquals(10, testPlayer.getGold(), "Player should still have 10 gold.");
    }

    @Test
    void zeroPayGoldTest(){
        Player testPlayer = new Player(new Ship());
        testPlayer.setGold(10);
        assertTrue(testPlayer.payGold(10), "Function should return true in this case.");
        assertEquals(0, testPlayer.getGold(), "Player should now have 0 gold.");
    }

    @Test
    void addPointsTest(){
        Player testPlayer = new Player(new Ship());
        testPlayer.setPoints(0);
        testPlayer.addPoints(10);
        assertEquals(10, testPlayer.getPoints(), "Player should now have 10 points.");
    }

    @Test
    void damageTest() {
        Ship testShip = new Ship();
        int oldHealth = testShip.getHealth();
        testShip.damage(5);
        assertEquals( oldHealth-5, testShip.getHealth(),"Ship health must decrease by 5.");
    }
}
