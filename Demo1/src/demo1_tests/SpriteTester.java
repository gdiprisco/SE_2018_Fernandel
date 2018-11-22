package demo1_tests;

import demo1.Sprite;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;

class SpriteTester {

	@Test
	public void testLoadSprite() throws IOException {
		BufferedImage expected_frame = ImageIO.read(new File("src/assets/pg_frames.png"));
		BufferedImage frame = Sprite.loadSprite("src/assets/pg_frames.png");

		byte[] byte_expected_frame = ((DataBufferByte) expected_frame.getData().getDataBuffer()).getData();
		byte[] byte_frame = ((DataBufferByte) frame.getData().getDataBuffer()).getData();

		assertArrayEquals(byte_expected_frame, byte_frame);

	}

	@Test
	public void testGetSprite() throws IOException {
		BufferedImage frame = ImageIO.read(new File("src/assets/pg_frames.png"));
		BufferedImage bi;
		BufferedImage expected_bi;

		for (int xGrid = 0; xGrid < 3; xGrid++) {
			for (int yGrid = 0; yGrid < 4; yGrid++) {
				bi = Sprite.getSprite(xGrid, yGrid);
				expected_bi = frame.getSubimage(xGrid * 31, yGrid * 32, 31, 32);

				byte[] byte_bi = ((DataBufferByte) bi.getData().getDataBuffer()).getData();
				byte[] byte_expected_bi = ((DataBufferByte) expected_bi.getData().getDataBuffer()).getData();

				assertArrayEquals(byte_expected_bi, byte_bi);

			}
		}

	}

}
