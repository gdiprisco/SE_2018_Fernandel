package demo1;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {
	private static BufferedImage SpriteSheet;
	private static final String frame_url = "src/assets/pg_frames.png";
	
	private static final int TILE_SIZE_X = 31;
	private static final int TILE_SIZE_Y = 32;
	
	public static BufferedImage loadSprite(String url) {
		BufferedImage sprite = null;
		try {
			sprite = ImageIO.read(new File(url));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sprite;
	}
	
	public static BufferedImage getSprite(int xGrid, int yGrid) {
		if (SpriteSheet == null)
			SpriteSheet = loadSprite(frame_url);
		return SpriteSheet.getSubimage(xGrid*TILE_SIZE_X, yGrid*TILE_SIZE_Y, TILE_SIZE_X, TILE_SIZE_Y);
	}
	
	
}
