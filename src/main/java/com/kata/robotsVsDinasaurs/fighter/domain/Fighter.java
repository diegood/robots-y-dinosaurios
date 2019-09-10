package com.kata.robotsVsDinasaurs.fighter.domain;


import com.kata.robotsVsDinasaurs.combatGrid.domain.Coordinates;
import com.kata.robotsVsDinasaurs.enums.Compass;
import io.reactivex.rxjava3.core.Observable;

import java.util.List;
import java.util.function.Function;

public interface Fighter {

    void attack();
    Long getId();
    Integer getStep();
    void setDirection(Compass direction);
    Compass getDirection();
    Boolean isEnemy(Fighter fighter);
    Observable<Fighter> attacks();
    Function<Coordinates, List<Coordinates>> attackStrategy();
}
