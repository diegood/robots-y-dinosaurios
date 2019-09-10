package com.kata.robotsVsDinasaurs.fighter.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kata.robotsVsDinasaurs.combatGrid.domain.Coordinates;
import com.kata.robotsVsDinasaurs.enums.Compass;
import com.kata.robotsVsDinasaurs.fighter.CombatStrategy;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Component
public class FighterRobot implements Fighter {

    private final PublishSubject<Fighter> subjectAttack = PublishSubject.create();
    Long id;
    Compass direction;
    @JsonIgnore
    Integer step = 1;

    public FighterRobot() {
        id = System.currentTimeMillis();
        this.direction = Compass.E;
    }

    public FighterRobot(Long id) {
        this.id = id;
        this.direction = Compass.E;
    }

    @Override
    public void attack() {
        subjectAttack.onNext((this));
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Integer getStep() {
        return step;
    }

    @Override
    public Compass getDirection() {
        return direction;
    }

    @Override
    public Observable<Fighter> attacks() {
        return subjectAttack;
    }

    @Override
    public Function<Coordinates, List<Coordinates>> attackStrategy() {
        return CombatStrategy.robotAttack();
    }

    @Override
    public void setDirection(Compass direction) {
        this.direction = direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FighterRobot that = (FighterRobot) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public Boolean isEnemy(Fighter fighter){
        return fighter instanceof FighterDinosaur;
    }

}
