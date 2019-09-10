package com.kata.robotsVsDinasaurs.enums;

public enum Compass {
    N (true),
    O (false),
    S (true),
    E (false);

    private final Boolean direction;

    Compass(Boolean direction){
        this.direction = direction;
    }

    public boolean isVertical() {
        return this.direction;
    }

}
