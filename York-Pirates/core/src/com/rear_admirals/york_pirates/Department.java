package com.rear_admirals.york_pirates;

public class Department {

    public final String name;
    public String product;

    public Department(String name, String product) {
        this.name = name;
        this.product = product;
    }

    public String getName() { return name; }

    public static Department Chemistry = new Department("Chemistry", "+Damage");

}
