package com.kata.robotsVsDinasaurs;


import com.kata.robotsVsDinasaurs.combatGrid.domain.CombatGrid;
import com.kata.robotsVsDinasaurs.combatGrid.domain.Coordinates;
import com.kata.robotsVsDinasaurs.fighter.domain.FighterDinosaur;
import com.kata.robotsVsDinasaurs.fighter.domain.FighterRobot;
import io.reactivex.rxjava3.observers.TestObserver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@SpringBootTest
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class RobotFighterTest {

    private Integer xAxis, yAxis;
    private Coordinates robotPosition;
    @Autowired
    private CombatGrid combatGrid;
    private FighterRobot fighterRobot;

    @Before
    public void setup() {
        xAxis = 50;
        yAxis = xAxis;
        robotPosition = new Coordinates(22, 19);
        combatGrid.setGrid(xAxis, yAxis);
        fighterRobot = new FighterRobot();
        combatGrid.addFighter( fighterRobot, robotPosition);
    }

    @After
    public void cleanSetup(){
        combatGrid.clearGrid();
    }


    @Test
    public void attack(){
        FighterDinosaur fighterDinosaur = new FighterDinosaur();
        Coordinates dinosaurPosition = new Coordinates(robotPosition.getXAxis(), robotPosition.getYAxis()+1);
        combatGrid.addFighter( fighterDinosaur, dinosaurPosition);
        fighterRobot.attack();
        Assert.assertNull(combatGrid.findFighter(fighterDinosaur));
    }

    @Test
	public void killDinosaursFightersAround(){
		Coordinates dinosaurPosition = new Coordinates(robotPosition.getXAxis(), robotPosition.getYAxis()+1);
		combatGrid.addFighter( new FighterDinosaur(), dinosaurPosition);
        TestObserver asserter  = fighterRobot.attacks().test();
		fighterRobot.attack();
		asserter.assertValue(v -> combatGrid.getElementFromPosition(dinosaurPosition) == null);
	}



}
