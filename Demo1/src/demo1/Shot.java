package demo1;

public class Shot extends GenericElement {
	private final int SPEED = 2;
	private int direction;

	public Shot(int x, int y, int direction, boolean isEnemyShot) {
		super(x, y);
		this.direction = direction;
		initShot(isEnemyShot);
	}

	public void initShot(boolean isEnemyShot) {
		if (isEnemyShot) {
			loadImage("src/images/redshot.png");
		} else
			loadImage("src/images/blueshot.png");
		getImageDimensions();
	}

	public int getDirection() {
		return direction;
	}

	public void moveLeft() {
		x -= SPEED;
	}

	public void moveRight() {
		x += SPEED;
	}

	public void moveUp() {
		y -= SPEED;

	}

	public void moveDown() {
		y += SPEED;

	}

}
