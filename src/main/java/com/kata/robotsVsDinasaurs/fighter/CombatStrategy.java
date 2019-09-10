package com.kata.robotsVsDinasaurs.fighter;

import com.kata.robotsVsDinasaurs.combatGrid.domain.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class CombatStrategy {

    public static Function<Coordinates, List<Coordinates>> robotAttack(){
        return (position) ->  {
            List<Coordinates> coordinatesAround = new ArrayList<Coordinates>();
            coordinatesAround.add(new Coordinates(position.getXAxis(), position.getYAxis()+1));
            coordinatesAround.add(new Coordinates(position.getXAxis()-1, position.getYAxis()));
            coordinatesAround.add(new Coordinates(position.getXAxis(), position.getYAxis()-1));
            coordinatesAround.add(new Coordinates(position.getXAxis()+1, position.getYAxis()));
            return coordinatesAround;
        };
    }

    public static Function<Coordinates, List<Coordinates>> dinosaurAttack(){
        return (position) -> new ArrayList<>();
    }



}
