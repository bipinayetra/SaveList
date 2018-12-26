package com.bipinayetra.savelist;

import java.io.Serializable;

public class Animal implements Serializable {
    boolean isPet;
    String name;

    public Animal(boolean isPet, String name) {
        this.isPet = isPet;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "isPet=" + isPet +
                ", name='" + name + '\'' +
                '}';
    }
}
