package demo1_tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import demo1.MainCharacter;
import demo1.MapElement;
import demo1.MapGenerator;
import demo1.ShooterMob;

class MapGeneratorTester {

	MapGenerator mg = new MapGenerator();
	List<MapElement> map_element;
	MainCharacter mc = new MainCharacter();
	ShooterMob sm = new ShooterMob(100, 100);

	@Test
	public void testGetRandomMap() {
		map_element = new ArrayList<MapElement>();
		mc.setX(375);
		mc.setY(275);
		mg.addNotStackable(mc.getBounds());
		mg.addNotStackable(sm.getBounds());
		map_element = mg.getRandomMap();
		int index = 0;
		for(MapElement me: map_element) {
			assertFalse(checkCollision(me.getBounds(),index));
			assertFalse(checkMinDist(me.getBounds(),index));
			index++;
		}		
		
	}
	
	public boolean checkMinDist(Rectangle rect, int index) {
		for(int i = 0; i < map_element.size(); i++) {
			if(i != index) {
				Rectangle r = map_element.get(i).getBounds();
				if (Math.abs(rect.getX() - (r.getX() + r.getWidth())) < 40
						|| Math.abs(r.getX() - (rect.getX() + rect.getWidth())) < 40
						|| Math.abs(rect.getY() - (r.getY() + r.getHeight())) < 40
						|| Math.abs(r.getY() - (rect.getY() + rect.getHeight())) < 40) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean checkCollision(Rectangle r, int index) {
		for(int i = 0; i < map_element.size(); i++) {
			if(i != index) {
				if(r.intersects(map_element.get(i).getBounds())) {
					return true;
				}
			}
		}
		if (r.intersects(mc.getBounds()))
			return true;
		if (r.intersects(sm.getBounds()))
			return true;
		return false;
	}
	


}
