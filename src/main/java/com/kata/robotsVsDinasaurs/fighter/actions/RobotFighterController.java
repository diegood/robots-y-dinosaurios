package com.kata.robotsVsDinasaurs.fighter.actions;


import com.kata.robotsVsDinasaurs.combatGrid.domain.CombatGrid;
import com.kata.robotsVsDinasaurs.combatGrid.domain.Coordinates;
import com.kata.robotsVsDinasaurs.enums.Compass;
import com.kata.robotsVsDinasaurs.enums.Direction;
import com.kata.robotsVsDinasaurs.fighter.domain.Fighter;
import com.kata.robotsVsDinasaurs.fighter.domain.FighterRobot;
import com.kata.robotsVsDinasaurs.fighter.domain.Movement;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/robot")
@SessionScope
@Api(value = "/robot", description = "Operaciones con robots")
public class RobotFighterController {

    @Autowired
    CombatGrid combatGrid;

    @RequestMapping(value = "/alive", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String isAlive(){
        return "is alive";
    }

    @GetMapping("/")
    public List<Fighter> getAll(){
        return combatGrid.getAll().stream().filter(f -> f instanceof FighterRobot).collect(Collectors.toList());
    }

    @PostMapping("/add/{x}/{y}")
    public FighterRobot addRobot(@RequestBody FighterRobot fighterRobot, @PathVariable(value = "x") int x, @PathVariable(value = "y") int y){
        combatGrid.addFighter(fighterRobot, new Coordinates(x,y));
        return fighterRobot;
    }

    @PostMapping("/find")
    public Coordinates find(@RequestBody FighterRobot fighterRobot){
        return combatGrid.findFighter(fighterRobot);
    }

    @PutMapping("/move/forward")
    public Coordinates moveForward(@RequestBody FighterRobot fighterRobot){
        combatGrid.moveFighter(new Movement(fighterRobot, Direction.FORWARD)).blockingAwait();
        return combatGrid.findFighter(fighterRobot);

//        return combatGrid.getFighterById(id).map(f ->
//                new Movement(f, Direction.FORWARD))
//                .flatMapCompletable(m -> combatGrid.moveFighter(m))
//                .andThen(combatGrid.getFighterById(id))
//                .map(fighter -> combatGrid.findFighter(fighter)).onErrorComplete();
    }

    @PutMapping(value = "/move/backward")
    public Coordinates moveBackward(@RequestBody FighterRobot fighterRobot){
        combatGrid.moveFighter(new Movement(fighterRobot, Direction.BACKWARD)).blockingAwait();
        return combatGrid.findFighter(fighterRobot);
    }

    @PutMapping(value = "/rotate/{commpass}")
    public Fighter rotate(@RequestBody FighterRobot fighterRobot, @PathVariable(value = "commpass") Compass compass){
        Fighter fighter = combatGrid.getFighterById(fighterRobot.getId());
        fighter.setDirection(compass);
        return fighter;
    }

    @PutMapping(value = "/attack")
    @ResponseStatus(HttpStatus.OK)
    public void attack(@RequestBody FighterRobot fighterRobot){
        combatGrid.getFighterById(fighterRobot.getId()).attack();
    }
}
