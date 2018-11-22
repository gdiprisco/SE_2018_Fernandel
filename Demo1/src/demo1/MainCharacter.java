package demo1;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class MainCharacter extends GenericCharacter {
	private List<Shot> shots;
	private int dx, dy;

	// KEY FLAGS
	private boolean moveDown;
	private boolean moveLeft;
	private boolean moveRight;
	private boolean moveUp;
	private boolean shotDown;
	private boolean shotLeft;
	private boolean shotRight;
	private boolean shotUp;

	// MOVING SPRITE
	private BufferedImage[] walkingDown = { Sprite.getSprite(0, 0), Sprite.getSprite(2, 0) };
	private BufferedImage[] walkingLeft = { Sprite.getSprite(0, 1), Sprite.getSprite(2, 1) };
	private BufferedImage[] walkingRight = { Sprite.getSprite(0, 2), Sprite.getSprite(2, 2) };
	private BufferedImage[] walkingUp = { Sprite.getSprite(0, 3), Sprite.getSprite(2, 3) };

	// STANDING SPRITE
	private BufferedImage[] standingDown = { Sprite.getSprite(1, 0) };
	private BufferedImage[] standingLeft = { Sprite.getSprite(1, 1) };
	private BufferedImage[] standingRight = { Sprite.getSprite(1, 2) };
	private BufferedImage[] standingUp = { Sprite.getSprite(1, 3) };

	// ANIMATION TO WALK
	private Animation walkDown = new Animation(walkingDown, 10);
	private Animation walkLeft = new Animation(walkingLeft, 10);
	private Animation walkRight = new Animation(walkingRight, 10);
	private Animation walkUp = new Animation(walkingUp, 10);

	// ANIMATION TO STAND
	private Animation standDown = new Animation(standingDown, 10);
	private Animation standLeft = new Animation(standingLeft, 10);
	private Animation standRight = new Animation(standingRight, 10);
	private Animation standUp = new Animation(standingUp, 10);

	// INITIAL STAND
	private Animation animation = standDown;

	// Constructor
	public MainCharacter() {
		super(375, 275, 1, 1, 2);
		initMain();
	}

	// Init main character
	private void initMain() {
		shots = new ArrayList<>();
		this.setImage(animation.getSprite());
		getImageDimensions();
	}

	@Override
	public BufferedImage getImage() {
		return animation.getSprite();
	}

	// This method updates the position of the main character
	// based on the keys that have been pressed.
	// Also, it updates the sprites.
	public void move() {
		if (moveDown) {
			animation = walkDown;
			animation.start();
			dy = getSpd();
		}
		if (moveLeft) {
			animation = walkLeft;
			animation.start();
			dx = -getSpd();
		}
		if (moveRight) {
			animation = walkRight;
			animation.start();
			dx = getSpd();
		}
		if (moveUp) {
			animation = walkUp;
			animation.start();
			dy = -getSpd();
		}
		x += dx;
		y += dy;
		animation.update();

	}

	public List<Shot> GetShots() {
		return shots;
	}

	// Key listener for the character
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			shotLeft = true;
			fireLeft();
		} else if (key == KeyEvent.VK_RIGHT) {
			shotRight = true;
			fireRight();
		} else if (key == KeyEvent.VK_UP) {
			shotUp = true;
			fireUp();
		} else if (key == KeyEvent.VK_DOWN) {
			shotDown = true;
			fireDown();
		}

		if (key == KeyEvent.VK_A) {
			moveLeft = true;
		}
		if (key == KeyEvent.VK_D) {
			moveRight = true;
		}
		if (key == KeyEvent.VK_W) {
			moveUp = true;
		}
		if (key == KeyEvent.VK_S) {
			moveDown = true;
		}
	}

	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_A) {
			moveLeft = false;
			dx = 0;
			animation.stop();
			animation.reset();
			animation = standLeft;

		}

		if (key == KeyEvent.VK_D) {
			moveRight = false;
			dx = 0;
			animation.stop();
			animation.reset();
			animation = standRight;
		}

		if (key == KeyEvent.VK_W) {
			moveUp = false;
			dy = 0;
			animation.stop();
			animation.reset();
			animation = standUp;
		}

		if (key == KeyEvent.VK_S) {
			moveDown = false;
			dy = 0;
			animation.stop();
			animation.reset();
			animation = standDown;
		}

		shotUp = shotDown = shotLeft = shotRight = false;

	}

	// Methods to add a shot
	private void fireUp() {
		if (shotUp && !shotDown && !shotLeft && !shotRight) {
			shots.add(new Shot(x + width / 2, y - 10, 0, false));
			shotDown = shotLeft = shotRight = false;
		}

	}

	private void fireDown() {
		if (!shotUp && shotDown && !shotLeft && !shotRight) {
			shots.add(new Shot(x + width / 2, y + 10 + height, 1, false));
			shotUp = shotLeft = shotRight = false;
		}

	}

	private void fireLeft() {
		if (!shotUp && !shotDown && shotLeft && !shotRight) {
			shots.add(new Shot(x - 10, y + height / 2, 2, false));
			shotUp = shotDown = shotRight = false;
		}

	}

	private void fireRight() {
		if (!shotUp && !shotDown && !shotLeft && shotRight) {
			shots.add(new Shot(x + width, y + height / 2, 3, false));
			shotUp = shotDown = shotLeft = false;
		}

	}
}
