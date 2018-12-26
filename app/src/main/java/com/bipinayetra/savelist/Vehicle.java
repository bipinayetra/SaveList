package com.bipinayetra.savelist;

import java.io.Serializable;

public class Vehicle implements Serializable {
    String type;
    int engineCC;

    public Vehicle(String type, int engineCC) {
        this.type = type;
        this.engineCC = engineCC;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "type='" + type + '\'' +
                ", engineCC=" + engineCC +
                '}';
    }
}
