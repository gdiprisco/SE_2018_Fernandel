package demo1_tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;

import demo1.Loser;

class LoserTester {

	@Test
	public void testInit() throws IOException {
		int expected_x = 0, expected_y = 50;
		BufferedImage expected_loser = ImageIO.read(new File("src/images/dog.gif"));
		Loser loser = new Loser(0,50);
		BufferedImage actual_loser = loser.getImage();
		
		byte[] byte_expected_loser = ((DataBufferByte) expected_loser.getData().getDataBuffer()).getData();
		byte[] byte_actual_loser = ((DataBufferByte) actual_loser.getData().getDataBuffer()).getData();
		
		assertArrayEquals(byte_expected_loser,byte_actual_loser);
		assertEquals(expected_x,loser.getX());
		assertEquals(expected_y,loser.getY());
		
	}
}
