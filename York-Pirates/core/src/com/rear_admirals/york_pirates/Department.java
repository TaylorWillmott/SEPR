package com.rear_admirals.york_pirates;

import static java.lang.Math.max;
import static java.lang.Math.pow;

public class Department {

    public final String name;

    public String getProduct() {
        return product;
    }

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
        if ( main.player.payGold(getPrice()) ) {
            if (product == "Defence") {
                main.player.playerShip.setDefence(main.player.playerShip.getDefence() + 1);
                return true;
            } else {
                main.player.playerShip.setAttack(main.player.playerShip.getAttack() + 1);
                return true;
            }
        } else {
            return false;
        }
    }

    public int getPrice() {
        if (product == "Defence") { return (int) (base_price * pow(2, max(0, main.player.playerShip.getDefence() - 3))); }
        else{ return (int) (base_price * pow(2, main.player.playerShip.getDefence())); }
    }

    public String getName() { return name; }


}
