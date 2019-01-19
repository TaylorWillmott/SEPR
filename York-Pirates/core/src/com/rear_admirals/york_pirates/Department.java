package com.rear_admirals.york_pirates;

import static java.lang.Math.pow;

public class Department {

    public final String name;
    public String product;
    public int base_price;
    public PirateGame main;

    public Department(String name, String product, PirateGame main) {
        this.name = name;
        this.product = product;
        this.base_price = 10;
        this.main = main;
    }

    public boolean purchase(){
        if (product == "Defence") {
            if (main.player.payGold((int)(base_price * pow(2, main.player.playerShip.getDefence())) )) {
                main.player.playerShip.setDefence(main.player.playerShip.getDefence() + 1);
                return true;
            }
        }
        else{
            if (main.player.payGold((int)(base_price * pow(2, main.player.playerShip.getAttack())) )) {
                main.player.playerShip.setAttack(main.player.playerShip.getAttack() + 1);
                return true;
            }
        }
        return false;

    }

    public String getName() { return name; }


}
