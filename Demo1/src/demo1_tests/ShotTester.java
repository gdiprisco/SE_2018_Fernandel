package demo1_tests;
import demo1.Shot;

import static org.junit.jupiter.api.Assertions.*;


import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;

class ShotTester {

	@Test
	public void testInitShot() throws IOException {
		
		BufferedImage expected_shot;
		BufferedImage actual_shot;
		Shot s;
		byte[] byte_expected_shot;
		byte[] byte_actual_shot;
		
		expected_shot = ImageIO.read(new File("src/images/redshot.png"));
		s = new Shot(0,0,1,true);
		actual_shot = s.getImage();
		
		byte_expected_shot = ((DataBufferByte) expected_shot.getData().getDataBuffer()).getData();
		byte_actual_shot = ((DataBufferByte) actual_shot.getData().getDataBuffer()).getData();
		
		assertArrayEquals(byte_expected_shot,byte_actual_shot);
		
		expected_shot = ImageIO.read(new File("src/images/blueshot.png"));
		s = new Shot(0,0,1,false);
		actual_shot = (BufferedImage) s.getImage();
		
		byte_expected_shot = ((DataBufferByte) expected_shot.getData().getDataBuffer()).getData();
		byte_actual_shot = ((DataBufferByte) actual_shot.getData().getDataBuffer()).getData();
		
		assertArrayEquals(byte_expected_shot,byte_actual_shot);

	}
	
	@Test
	public void testMoveLeft() {
		Shot s = new Shot(0,0,1,true);
		int expected_x = s.getX()-2;
		s.moveLeft();
		assertEquals(expected_x,s.getX());
	}
	
	@Test
	public void testMoveRight() {
		Shot s = new Shot(0,0,1,true);
		int expected_x = s.getX()+2;
		s.moveRight();
		assertEquals(expected_x,s.getX());
	}
	
	@Test
	public void testMoveUp() {
		Shot s = new Shot(0,0,1,true);
		int expected_y = s.getY()-2;
		s.moveUp();
		assertEquals(expected_y,s.getY());
	}
	
	@Test
	public void testMoveDown() {
		Shot s = new Shot(0,0,1,true);
		int expected_y = s.getY()+2;
		s.moveDown();
		assertEquals(expected_y,s.getY());
	}

}
