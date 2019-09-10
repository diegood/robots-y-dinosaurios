package com.kata.robotsVsDinasaurs;


import com.kata.robotsVsDinasaurs.combatGrid.domain.CombatGrid;
import com.kata.robotsVsDinasaurs.combatGrid.domain.Coordinates;
import com.kata.robotsVsDinasaurs.enums.Compass;
import com.kata.robotsVsDinasaurs.enums.Direction;
import com.kata.robotsVsDinasaurs.fighter.domain.FighterRobot;
import com.kata.robotsVsDinasaurs.fighter.domain.Movement;
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

import java.util.stream.Collectors;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class CombatGridTest {

	private Integer xAxis, yAxis;
	private Coordinates robotPosition;
	private FighterRobot fighterRobot;

	@Autowired CombatGrid combatGrid;

	@Before
	public void setup() {
		xAxis = 50;
		yAxis = xAxis;
		robotPosition = new Coordinates(13, 45);
		combatGrid.setGrid(xAxis,yAxis);
	}

	@After
	public void cleanSetup(){
		combatGrid.clearGrid();
	}

	@Test
	public void  createCombatGrid(){
		Assert.assertTrue(combatGrid.getGrid().size() == 50 && combatGrid.getRow(1).size() == 50);
	}

	@Test
	public void putFighterInGrid(){
		putFighterRobotFromTestAndCreateGrid();
		Assert.assertEquals(combatGrid.getElementFromPosition(robotPosition), fighterRobot);
	}

	@Test
	public void removeFighter(){
		putFighterRobotFromTestAndCreateGrid();
		combatGrid.removeFighter(robotPosition);
		Assert.assertNull(combatGrid.getElementFromPosition(robotPosition));
	}

	@Test
	public void findFighterInGrid(){
		putFighterRobotFromTestAndCreateGrid();
		Assert.assertTrue(combatGrid.findFighter(fighterRobot) != null);
	}

	@Test
	public void giveMePositionToMove(){
		putFighterRobotFromTestAndCreateGrid();
		Coordinates coordinates = combatGrid.getPositionToMove(new Movement(fighterRobot, Direction.FORWARD), combatGrid.findFighter(fighterRobot));
		Assert.assertTrue(robotPosition.getXAxis() == coordinates.getXAxis()-1);
	}

	@Test
	public void toggleSectionFighter(){
		putFighterRobotFromTestAndCreateGrid();
		Coordinates expectedPlaceForFighter = new Coordinates(robotPosition.getXAxis()+1, robotPosition.getYAxis());
		combatGrid.toggleSectionFighter(robotPosition, expectedPlaceForFighter);
		Assert.assertEquals(combatGrid.findFighter(fighterRobot), expectedPlaceForFighter);
	}

	@Test
	public void moveFighter(){
		putFighterRobotFromTestAndCreateGrid();
		TestObserver asserter = combatGrid.moveFighter(new Movement(fighterRobot, Direction.FORWARD)).test();
		Coordinates expectedPlaceForFighter = new Coordinates(robotPosition.getXAxis()+1, robotPosition.getYAxis());
		asserter.assertComplete();
		Assert.assertTrue(combatGrid.findFighter(fighterRobot).equals(expectedPlaceForFighter) && combatGrid.getElementFromPosition(robotPosition) == null);
	}

	@Test
	public void moveRobotBackwards(){
		putFighterRobotFromTestAndCreateGrid();
		TestObserver asserter = combatGrid.moveFighter(new Movement(fighterRobot, Direction.BACKWARD)).test();
		asserter.assertComplete();
		Coordinates expectedPlaceForFighter = new Coordinates(robotPosition.getXAxis()-1, robotPosition.getYAxis());
		Assert.assertEquals(combatGrid.findFighter(fighterRobot), expectedPlaceForFighter);
	}

	@Test
	public void moveRobotUpwards(){
		putFighterRobotFromTestAndCreateGrid();
		fighterRobot.setDirection(Compass.N);
		TestObserver asserter = combatGrid.moveFighter(new Movement(fighterRobot, Direction.FORWARD)).test();
		asserter.assertComplete();
		Coordinates expectedPlaceForFighter = new Coordinates(robotPosition.getXAxis(), robotPosition.getYAxis()+1);
		Assert.assertEquals(combatGrid.findFighter(fighterRobot), expectedPlaceForFighter);
	}

	@Test
	public void moveRobotDownwards(){
		putFighterRobotFromTestAndCreateGrid();
		fighterRobot.setDirection(Compass.S);
		TestObserver asserter = combatGrid.moveFighter(new Movement(fighterRobot, Direction.FORWARD)).test();
		asserter.assertComplete();
		Coordinates expectedPlaceForFighter = new Coordinates(robotPosition.getXAxis(), robotPosition.getYAxis()-1);
		Assert.assertEquals(combatGrid.findFighter(fighterRobot), expectedPlaceForFighter);
	}

//	@Test
//	public void killDinosaursFightersAround(){
//		putFighterRobotFromTestAndCreateGrid();
//		Coordinates dinosaurPosition = new Coordinates(robotPosition.getXAxis(), robotPosition.getYAxis()+1);
//		combatGrid.addFighter( new FighterDinosaur(), dinosaurPosition);
//		combatGrid.killAllDinosaurAround(fighterRobot);
//		Assert.assertNull(combatGrid.getElementFromPosition(dinosaurPosition));
//	}

//	@Test
//	public void getDinosaursFightersAround(){
//		putFighterRobotFromTestAndCreateGrid();
//		List<Coordinates> coordinatesListExpected = new ArrayList<Coordinates>();
//		Coordinates dinosaurPosition = new Coordinates(robotPosition.getXAxis(), robotPosition.getYAxis()+1);
//		coordinatesListExpected.add(dinosaurPosition);
//		combatGrid.addFighter( new FighterDinosaur(), dinosaurPosition);
//		Assert.assertEquals(combatGrid.getDinosaurFightersPositionsAround(fighterRobot), coordinatesListExpected);
//	}

//	@Test
//	public void getAllPositionsWithHaveDinosaursAround(){
//		putFighterRobotFromTestAndCreateGrid();
//		Coordinates dinosaurPosition = new Coordinates(robotPosition.getXAxis(), robotPosition.getYAxis()+1);
//		combatGrid.addFighter( new FighterDinosaur(), dinosaurPosition);
//		List<Coordinates> listEspected = new ArrayList<Coordinates>();
//		listEspected.add(new Coordinates(robotPosition.getXAxis(), robotPosition.getYAxis()+1));
//		Assert.assertEquals(combatGrid.getDinosaurFightersPositionsAround(fighterRobot), listEspected);
//	}

//	@Test
//	public void getCoordinatesAround(){
//		Map<Compass, Coordinates> coordinatesAround = combatGrid.getCoordinatesAround(robotPosition);
//		Assert.assertThat(coordinatesAround.get(Compass.N).getYAxis(), IsSame.sameInstance(robotPosition.getYAxis()+1));
//		Assert.assertThat(coordinatesAround.get(Compass.O).getXAxis(), IsSame.sameInstance(robotPosition.getXAxis()-1));
//		Assert.assertThat(coordinatesAround.get(Compass.S).getYAxis(), IsSame.sameInstance(robotPosition.getYAxis()-1));
//		Assert.assertThat(coordinatesAround.get(Compass.E).getXAxis(), IsSame.sameInstance(robotPosition.getXAxis()+1));
//	}

	public void findFighterById(){
		putFighterRobotFromTestAndCreateGrid();
		combatGrid.addFighter(new FighterRobot(15l), new Coordinates(15, 9));
		combatGrid.getGrid().stream().flatMap(v -> v.stream().filter(f -> f != null && f.getId() == 15l)).collect(Collectors.toList());
//		combatGrid.getGrid().stream().flatMap(v -> v.stream().filter(f -> f != null)).collect(Collectors.toList());
//		combatGrid.getGrid().stream().anyMatch(v -> v.stream().anyMatch(f -> f.equals(fighterRobot)));
	}

	@Test
	public void putFighterInGridOffTheGrid(){
		robotPosition = new Coordinates(13, 55);
		try{
			combatGrid.addFighter( new FighterRobot(), robotPosition);
			Assert.assertTrue(false);
		}catch (Exception e){
			Assert.assertTrue(e instanceof IndexOutOfBoundsException);
		}
	}

	private void putFighterRobotFromTestAndCreateGrid(){
		fighterRobot = new FighterRobot();
		combatGrid.addFighter( fighterRobot, robotPosition);
	}

}
