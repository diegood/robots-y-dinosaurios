package com.kata.robotsVsDinasaurs.fighter.domain;


import com.kata.robotsVsDinasaurs.combatGrid.domain.Coordinates;
import com.kata.robotsVsDinasaurs.enums.Compass;
import com.kata.robotsVsDinasaurs.fighter.CombatStrategy;
import io.reactivex.rxjava3.core.Observable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class FighterDinosaur implements Fighter {

    private Compass direction;

    public FighterDinosaur() {
        this.direction = Compass.O;
    }

    @Override
    public void attack() {
        System.out.println("mis brazos son muy cortos para atacar");
    }

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public Integer getStep() {
        return 0;
    }

    @Override
    public void setDirection(Compass direction) {
        this.direction = direction;
    }

    @Override
    public Compass getDirection() {
        return this.direction;
    }

    @Override
    public Boolean isEnemy(Fighter fighter) {
        return false;
    }

    @Override
    public Observable<Fighter> attacks() {
        return Observable.empty();
    }

    @Override
    public Function<Coordinates, List<Coordinates>> attackStrategy() {
        return CombatStrategy.dinosaurAttack();
    }

}
