package demo1_tests;

import static org.junit.Assert.assertEquals;

import java.awt.AWTException;
import java.awt.Button;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import demo1.MainCharacter;
import demo1.Shot;

class MainCharacterTester {

	private List<Shot> shots;

	@SuppressWarnings("deprecation")
	@Test
	public void testMove() throws AWTException {
		int expected_x;
		int expected_y;
		
		MainCharacter mc = new MainCharacter();
		int SPEED = mc.getSpd();
		
		// test UP
		expected_y = mc.getY()-SPEED;
		Button up = new Button("up");
		mc.keyPressed(new KeyEvent(up,KeyEvent.VK_W,0,0,KeyEvent.VK_W));
		mc.move();
		mc.keyReleased(new KeyEvent(up,KeyEvent.VK_W,0,0,KeyEvent.VK_W));
		System.out.println("MOVE UP " + expected_y + " " + mc.getY());
		assertEquals(expected_y,mc.getY());
		
		// test LEFT
		expected_x = mc.getX()-SPEED;
		Button left = new Button("left");
		mc.keyPressed(new KeyEvent(left,KeyEvent.VK_A,0,0,KeyEvent.VK_A));
		mc.move();
		mc.keyReleased(new KeyEvent(left,KeyEvent.VK_A,0,0,KeyEvent.VK_A));
		System.out.println("MOVE LEFT " + expected_x + " " + mc.getX());
		assertEquals(expected_x,mc.getX());
		
		// test RIGHT
		expected_x = mc.getX()+SPEED;
		Button right = new Button("right");
		mc.keyPressed(new KeyEvent(right,KeyEvent.VK_D,0,0,KeyEvent.VK_D));
		mc.move();
		mc.keyReleased(new KeyEvent(right,KeyEvent.VK_D,0,0,KeyEvent.VK_D));
		System.out.println("MOVE RIGHT " + expected_x + " " + mc.getX());
		assertEquals(expected_x,mc.getX());

		// test DOWN
		expected_y = mc.getY()+SPEED;
		Button down = new Button("down");
		mc.keyPressed(new KeyEvent(down,KeyEvent.VK_S,0,0,KeyEvent.VK_S));
		mc.move();
		mc.keyReleased(new KeyEvent(down,KeyEvent.VK_S,0,0,KeyEvent.VK_S));
		System.out.println("MOVE DOWN " + expected_y + " " + mc.getY());
		assertEquals(expected_y,mc.getY());
		

	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testShot() throws AWTException {
		shots = new ArrayList<>();
		
		MainCharacter mc = new MainCharacter();
		int x = mc.getX();
		int y = mc.getY();
		int width = mc.getWidth();
		int height = mc.getWidth();
		
		
		// test UP
		
		shots.add(new Shot(x + width / 2, y - 10, 0, false));
		Button up = new Button("up");
		mc.keyPressed(new KeyEvent(up,KeyEvent.VK_UP,0,0,KeyEvent.VK_UP));
		mc.move();
		mc.keyReleased(new KeyEvent(up,KeyEvent.VK_UP,0,0,KeyEvent.VK_UP));
		System.out.println("SHOT UP " + shots.size() + " " + mc.GetShots().size());
		assertEquals(shots.size(),mc.GetShots().size());
		
		// test LEFT
		shots.add(new Shot(x - 10, y + height / 2, 2, false));
		Button left = new Button("left");
		mc.keyPressed(new KeyEvent(left,KeyEvent.VK_LEFT,0,0,KeyEvent.VK_LEFT));
		mc.move();
		mc.keyReleased(new KeyEvent(left,KeyEvent.VK_LEFT,0,0,KeyEvent.VK_LEFT));
		System.out.println("SHOT LEFT " + shots.size() + " " + mc.GetShots().size());
		assertEquals(shots.size(),mc.GetShots().size());
		
		// test RIGHT
		shots.add(new Shot(x + width, y + height / 2, 3, false));
		Button right = new Button("right");
		mc.keyPressed(new KeyEvent(right,KeyEvent.VK_RIGHT,0,0,KeyEvent.VK_RIGHT));
		mc.move();
		mc.keyReleased(new KeyEvent(right,KeyEvent.VK_RIGHT,0,0,KeyEvent.VK_RIGHT));
		System.out.println("SHOT RIGHT " + shots.size() + " " + mc.GetShots().size());
		assertEquals(shots.size(),mc.GetShots().size());

		// test DOWN
		shots.add(new Shot(x + width / 2, y + 10 + height, 1, false));
		Button down = new Button("down");
		mc.keyPressed(new KeyEvent(down,KeyEvent.VK_DOWN,0,0,KeyEvent.VK_DOWN));
		mc.move();
		mc.keyReleased(new KeyEvent(down,KeyEvent.VK_DOWN,0,0,KeyEvent.VK_DOWN));
		System.out.println("SHOT DOWN " + shots.size() + " " + mc.GetShots().size());
		assertEquals(shots.size(),mc.GetShots().size());
		

	}

}
