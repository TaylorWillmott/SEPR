package com.rear_admirals.york_pirates;

public class College {

    public String getName() {
        return name;
    }

    public String name;
//    public College ally;
//    public College enemy;


    public College(String name) {
        this.name = name;
    }

    public static College Derwent = new College("Derwent");

}
