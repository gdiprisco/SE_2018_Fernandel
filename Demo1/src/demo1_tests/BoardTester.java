package demo1_tests;

import demo1.Board;
import demo1.MainCharacter;
import demo1.MapElement;
import demo1.Mob;
import demo1.ShooterMob;
import demo1.Shot;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Button;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class BoardTester {
	
	Board b;
	List<MapElement> test_map;
	List<Mob> test_mob;

	@SuppressWarnings("deprecation")
	@Test
	public void testCheckCollisionfromRight() {
		b = new Board();
		test_map = new ArrayList<MapElement>();
		test_mob = new ArrayList<Mob>();

		test_map.add(new MapElement(200, 200, "src/assets/box_40x40.png"));

		b.setMap_element(test_map);
		b.setMobs(test_mob);

		int EXPECTED_X = test_map.get(0).getX() + test_map.get(0).getWidth();
		int EXPECTED_Y = 200;
		b.getMc().setX(EXPECTED_X);
		b.getMc().setY(EXPECTED_Y);

		Button left = new Button("left");
		b.getMc().keyPressed(new KeyEvent(left, KeyEvent.VK_A, 0, 0, KeyEvent.VK_A));
		b.getMc().move();
		b.getMc().keyReleased(new KeyEvent(left, KeyEvent.VK_A, 0, 0, KeyEvent.VK_A));
		b.checkCollisions();
		
		assertEquals(EXPECTED_X, b.getMc().getX());
		assertEquals(EXPECTED_Y, b.getMc().getY());

	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testCheckCollisionfromLeft() {
		b = new Board();
		test_map = new ArrayList<MapElement>();
		test_mob = new ArrayList<Mob>();

		test_map.add(new MapElement(200, 200, "src/assets/box_40x40.png"));

		b.setMap_element(test_map);
		b.setMobs(test_mob);

		int EXPECTED_X = test_map.get(0).getX() - b.getMc().getWidth();
		int EXPECTED_Y = 200;
		b.getMc().setX(EXPECTED_X);
		b.getMc().setY(EXPECTED_Y);
		
		Button right = new Button("right");
		b.getMc().keyPressed(new KeyEvent(right,KeyEvent.VK_D,0,0,KeyEvent.VK_D));
		b.getMc().move();
		b.getMc().keyReleased(new KeyEvent(right,KeyEvent.VK_D,0,0,KeyEvent.VK_D));
		b.checkCollisions();
		
		assertEquals(EXPECTED_X, b.getMc().getX());
		assertEquals(EXPECTED_Y, b.getMc().getY());

	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testCheckCollisionfromBottom() {
		b = new Board();
		test_map = new ArrayList<MapElement>();
		test_mob = new ArrayList<Mob>();

		test_map.add(new MapElement(200, 200, "src/assets/box_40x40.png"));

		b.setMap_element(test_map);
		b.setMobs(test_mob);

		int EXPECTED_X = 200;
		int EXPECTED_Y = test_map.get(0).getY()+ test_map.get(0).getHeight();
		b.getMc().setX(EXPECTED_X);
		b.getMc().setY(EXPECTED_Y);
		Button up = new Button("up");
		b.getMc().keyPressed(new KeyEvent(up,KeyEvent.VK_W,0,0,KeyEvent.VK_W));
		b.getMc().move();
		b.getMc().keyReleased(new KeyEvent(up,KeyEvent.VK_W,0,0,KeyEvent.VK_W));
		b.checkCollisions();
		
		assertEquals(EXPECTED_X, b.getMc().getX());
		assertEquals(EXPECTED_Y, b.getMc().getY());

	}

	@SuppressWarnings("deprecation")
	@Test
	public void testCheckCollisionfromTop() {
		b = new Board();
		test_map = new ArrayList<MapElement>();
		test_mob = new ArrayList<Mob>();

		test_map.add(new MapElement(200, 200, "src/assets/box_40x40.png"));

		b.setMap_element(test_map);
		b.setMobs(test_mob);

		int EXPECTED_X = 200;
		int EXPECTED_Y = test_map.get(0).getY() - b.getMc().getHeight();
		b.getMc().setX(EXPECTED_X);
		b.getMc().setY(EXPECTED_Y);
		Button down = new Button("down");
		b.getMc().keyPressed(new KeyEvent(down,KeyEvent.VK_S,0,0,KeyEvent.VK_S));
		b.getMc().move();
		b.getMc().keyReleased(new KeyEvent(down,KeyEvent.VK_S,0,0,KeyEvent.VK_S));
		b.checkCollisions();
		
		assertEquals(EXPECTED_X, b.getMc().getX());
		assertEquals(EXPECTED_Y, b.getMc().getY());

	}
	
	@SuppressWarnings("deprecation")
	@Test
	void testCheckCollisionbeetwenMcandMob() {
		//TEST OBSTACLE
		b = new Board();
		MainCharacter mc = b.getMc();
		test_map = new ArrayList<MapElement>();
		test_mob = new ArrayList<Mob>();
		
		
		test_mob.add(new ShooterMob(mc.getX()+mc.getWidth(), mc.getY()-(mc.getHeight()/2)));
		b.setMobs(test_mob);
		
		b.setMap_element(test_map);
		
		
		Button right = new Button("right");
		mc.keyPressed(new KeyEvent(right,KeyEvent.VK_D,0,0,KeyEvent.VK_D));
		mc.move();
		mc.keyReleased(new KeyEvent(right,KeyEvent.VK_D,0,0,KeyEvent.VK_D));
		b.checkCollisions();

		assertEquals(b.isDeath(),true);
		assertEquals(b.isIngame(),false);
		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void testCheckCollisionofShots() {
		b = new Board();
		b.setMap_element(new ArrayList<MapElement>());
		test_mob = new ArrayList<Mob>();

		//set main character position
		MainCharacter mc = b.getMc();
		mc.setX(100);
		mc.setY(200);
		mc.move();
		
		test_mob.add(new ShooterMob(250,200));
		b.setMobs(test_mob);

		//shot to right button
		Button button = new Button("button");
		mc.keyPressed(new KeyEvent(button,KeyEvent.VK_RIGHT,0,0,KeyEvent.VK_RIGHT));
		mc.setY(500);
		mc.move();
		mc.keyReleased(new KeyEvent(button,KeyEvent.VK_RIGHT,0,0,KeyEvent.VK_RIGHT));
		
		//main character shot
		Shot shot = mc.GetShots().get(0);
	
		//checks shot position
		while(!shot.getBounds().intersects(b.getMobs().get(0).getBounds())){
			b.updateShots();
			b.checkCollisions();
		}

		assertFalse(b.getMobs().get(0).isVisible());
		
	}
}
