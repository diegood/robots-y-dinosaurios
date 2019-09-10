package com.kata.robotsVsDinasaurs.fighter.domain;


import com.kata.robotsVsDinasaurs.enums.Direction;

public class Movement {
   private Fighter fighter;
   private Direction direction;

    public Movement(Fighter fighter, Direction direction) {
        this.fighter = fighter;
        this.direction = direction;
    }

    public Fighter getFighter() {
        return fighter;
    }

    private String directionToString(){
        return this.direction == Direction.FORWARD ? "pa Delante" : "pa tras";
    }

    public Integer getDistance(){
        return this.direction == Direction.FORWARD ? fighter.getStep() : -1*fighter.getStep();
    }

    @Override
    public String toString() {
        return "Movement{" +
                "fighter=" + fighter +
                ", direccion ="+ directionToString()+
                ", distance=" + fighter.getStep() +
                '}';
    }
}
