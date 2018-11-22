package demo1_tests;
import demo1.ShooterMob;
import demo1.Shot;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class ShooterMobTester {

	@Test
	public void testShoot() {
		ShooterMob sm = new ShooterMob(100,100);
		List<Shot> fake_shot = new ArrayList<Shot>();
		fake_shot.add(new Shot(sm.getX()+sm.getWidth()/2,sm.getY(),0,true));
		fake_shot.add(new Shot(sm.getX()+sm.getWidth()/2,sm.getY()+sm.getHeight(),1,true));
		fake_shot.add(new Shot(sm.getX(),sm.getY()+sm.getHeight()/2,2,true));
		fake_shot.add(new Shot(sm.getX()+sm.getWidth(),sm.getY()+sm.getHeight()/2,3,true));
		sm.shoot();
		List<Shot> real_shot = sm.GetShots();
		for(int index = 0; index < real_shot.size(); index++) {
			assertEquals(fake_shot.get(index).getX(),real_shot.get(index).getX());
			assertEquals(fake_shot.get(index).getY(),real_shot.get(index).getY());
		}
	}

}
