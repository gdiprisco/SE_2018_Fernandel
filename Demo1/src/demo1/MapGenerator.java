package demo1;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapGenerator {

	private List<MapElement> mapele;
	private List<Rectangle> notStackable;

	// BOUND OF RANDOM NUMBER
	private static final int MIN = 1;
	private static final int MAX_X = 18;
	private static final int MAX_Y = 13;
	private static final int MIN_BLOCK = 0;
	private static final int MAX_BLOCK = 6;
	private static final int MIN_NUM_BLOCK = 1;
	private static final int MAX_NUM_BLOCK = 10;
	
	// LIST OF IMAGES STRINGS
	private static final String[] imageList = { "src/assets/box_40x40.png", "src/assets/box_40x80.png",
			"src/assets/box_40x120.png", "src/assets/box_40x160.png", "src/assets/box_80x40.png",
			"src/assets/box_120x40.png", "src/assets/box_160x40.png" };

	private Random r = new Random();

	public MapGenerator() {
		notStackable = new ArrayList<Rectangle>();
	}

	// Function that generate randomly the scenario
	private MapElement obstacleGenerator(int num_block) {
		String img = imageList[num_block];
		MapElement mp = new MapElement(0, 0, img);
		setRandom(mp);
		return mp;
	}

	// Function that avoids comparing the blocks on the character or mobs
	private void setRandom(MapElement mp) {
		do {
			int mol_x = r.nextInt((MAX_X - MIN) + 1) + MIN;

			int mol_y = r.nextInt((MAX_Y - MIN) + 1) + MIN;
			int x = 40 * mol_x;
			int y = 40 * mol_y;

			mp.setX(x);
			mp.setY(y);

		} while (checkMinDist(mp.getBounds()) || checkCollision(mp.getBounds()));
	}

	// Function that checks if the blocks collide on the mob or character
	private boolean checkCollision(Rectangle rect) {
		for (MapElement mp : mapele) {
			Rectangle r = mp.getBounds();
			if (r.intersects(rect)) {
				return true;
			}

		}
		for (Rectangle m : notStackable) {
			Rectangle r2 = m.getBounds();
			if (r2.intersects(rect))
				return true;
		}
		return false;
	}

	// Function that verifies if the blocks are, between them, at a distance that
	// allows the passage of the character
	private boolean checkMinDist(Rectangle rect) {
		for (MapElement mp : mapele) {
			Rectangle r = mp.getBounds();
			if (Math.abs(rect.getX() - (r.getX() + r.getWidth())) < 40
					|| Math.abs(r.getX() - (rect.getX() + rect.getWidth())) < 40
					|| Math.abs(rect.getY() - (r.getY() + r.getHeight())) < 40
					|| Math.abs(r.getY() - (rect.getY() + rect.getHeight())) < 40) {
				return true;
			}
		}
		return false;

	}

	// Function to init all map elements
	private List<MapElement> initMap() {
		mapele = new ArrayList<MapElement>();
		int NUM_OF_BLOCK = r.nextInt((MAX_NUM_BLOCK - MIN_NUM_BLOCK) + 1) + MIN_NUM_BLOCK;
		int NUM;

		for (int i = 0; i < NUM_OF_BLOCK; i++) {
			NUM = r.nextInt((MAX_BLOCK - MIN_BLOCK) + 1) + MIN_BLOCK;
			MapElement newObstacle = obstacleGenerator(NUM);
			if (newObstacle != null) {
				mapele.add(newObstacle);
			}
		}
		return mapele;
	}
	
	// Function that return list of MapElement witch contain borders
	public List<MapElement> getBorders(){
		List<MapElement> borders = new ArrayList<MapElement>();
		borders.add(new MapElement(0, 0, "src/assets/board_up.png"));
		borders.add(new MapElement(0, 560, "src/assets/board_down.png"));
		borders.add(new MapElement(0, 0, "src/assets/board_left.png"));
		borders.add(new MapElement(760, 0, "src/assets/board_right.png"));	
		return borders;
	}

	public List<MapElement> getRandomMap() {
		return initMap();
	}

	public void addNotStackable(Rectangle rectangle) {
		notStackable.add(rectangle);
	}

	public boolean removeNotStackable(Rectangle rectangle) {
		if (notStackable.remove(rectangle))
			return true;
		return false;
	}

	public void clearNotStackable() {
		if (!notStackable.isEmpty())
			notStackable.clear();
	}
	

}
