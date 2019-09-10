package com.kata.robotsVsDinasaurs.combatGrid.domain;

import com.kata.robotsVsDinasaurs.fighter.domain.Fighter;
import com.kata.robotsVsDinasaurs.fighter.domain.Movement;
import io.reactivex.rxjava3.core.Completable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope("singleton")
public class CombatGrid {

    private List<List<Fighter>> grid;

    Integer xAxisMax = 50, yAxisMax = 50;


    public CombatGrid() {
        setGrid(xAxisMax,yAxisMax);
    }

    public CombatGrid(Integer xAxis, Integer yAxis) {
        setGrid(xAxis,yAxis);
    }

    public void setGrid(Integer xAxis, Integer yAxis) {
        xAxisMax = xAxis ;
        yAxisMax = yAxis;
        grid = new ArrayList<>(yAxis);
        for (int i = 0; i < yAxis; i++) {
            grid.add(i, new ArrayList<>(xAxis));
            for(int j = 0; j < xAxis; j++){
                grid.get(i).add(null);
            }
        }
    }

    public List<Fighter> getAll(){
        return grid.stream().flatMap(v ->
                v.stream().filter(f -> f != null)
        ).collect(Collectors.toList());
    }

    public Fighter getFighterById(Long id){
       return grid.stream().flatMap(v ->
                       v.stream().filter(f -> f != null && f.getId() == id)
               ).findFirst().get();
    }

    public List<Fighter> getRow(int row) {
        return grid.get(row);
    }

    public List<List<Fighter>> getGrid() {
        return grid;
    }

    public void addFighter(Fighter fighter, Coordinates position) {
        if(getElementFromPosition(position) == null){
            setFighter(fighter, position);
        }
    }

    public Completable moveFighter(Movement movement){
        return Completable.fromSupplier(()-> {
            Coordinates coordinatesFrom = findFighter(movement.getFighter());
            toggleSectionFighter(coordinatesFrom,  getPositionToMove(movement, coordinatesFrom));
            return null;
        });
    }

    public void removeFighter(Coordinates coordinates) {
        grid.get(coordinates.getYAxis()).set(coordinates.getXAxis(), null);
    }

    public Fighter getElementFromPosition(Coordinates position){
        return grid.get(position.getYAxis()).get(position.getXAxis());
    }

    private void setFighter(Fighter fighter, Coordinates position) throws ArrayIndexOutOfBoundsException{
        grid.get(position.getYAxis()).set(position.getXAxis(), fighter);
        fighter.attacks().subscribe(f ->  attackByFighter(f));
    }


    public void toggleSectionFighter(Coordinates coordinatesFrom, Coordinates coordinatesTo){
       this.addFighter(getElementFromPosition(coordinatesFrom), coordinatesTo);
       removeFighter(coordinatesFrom);
    }

    public Coordinates getPositionToMove(Movement movement, Coordinates coordinatesFrom){
        int x = coordinatesFrom.getXAxis(), y = coordinatesFrom.getYAxis();
        switch (movement.getFighter().getDirection()){
            case N:
                y += movement.getDistance();
                break;
            case O:
                x -= movement.getDistance();
                break;
            case S:
                y -= movement.getDistance();
                break;
            case E:
                x += movement.getDistance();
                break;
        }
        return new Coordinates(x,y);
    }

    public void attackByFighter(Fighter fighter){
        List<Coordinates> position = fighter.attackStrategy().apply(findFighter(fighter));
        position.stream().filter(v -> fighter.isEnemy(getElementFromPosition(v)) ).forEach(f -> removeFighter(f));
    }

    public Coordinates findFighter(Fighter fighter){
        for (int yAxis = 0; yAxis < yAxisMax; yAxis++) {
            for (int xAxis = 0; xAxis < xAxisMax; xAxis++) {
                if(fighter.equals(grid.get(yAxis).get(xAxis))) return new Coordinates(xAxis,yAxis);
            }
        }
        return null;
    }

    public void clearGrid(){
        setGrid(xAxisMax, yAxisMax);
    }
}
