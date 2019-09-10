package com.kata.robotsVsDinasaurs.fighter.actions;


import com.kata.robotsVsDinasaurs.combatGrid.domain.CombatGrid;
import com.kata.robotsVsDinasaurs.combatGrid.domain.Coordinates;
import com.kata.robotsVsDinasaurs.fighter.domain.Fighter;
import com.kata.robotsVsDinasaurs.fighter.domain.FighterDinosaur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dinosaur")
@SessionScope
public class DinosaurFighterController {

    @Autowired
    CombatGrid combatGrid;

    @GetMapping("/")
    public List<Fighter> getAll(){
        return combatGrid.getAll().stream().filter(f -> f instanceof FighterDinosaur).collect(Collectors.toList());
    }

    @PostMapping("/add/{x}/{y}")
    public FighterDinosaur add(@RequestBody FighterDinosaur fighterDinosaur, @PathVariable(value = "x") int x, @PathVariable(value = "y") int y){
        combatGrid.addFighter(fighterDinosaur, new Coordinates(x,y));
        return fighterDinosaur;
    }

    @PostMapping("/find")
    public Coordinates find(@RequestBody FighterDinosaur fighterDinosaur){
        return combatGrid.findFighter(fighterDinosaur);
    }

}
