package demo1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MapGenerator mg;
	private List<MapElement> map_element;
	private boolean change = true;

	private final int WIDTH = 800; // window's width
	private final int HEIGHT = 600; // window's height
	private final int LOSER_X = 325; // x position of losing image
	private final int LOSER_Y = 416; // y position of losing image
	private final int DELAY = 13; // timer delay

	private Timer timer; // timer to repaint the window
	private boolean ingame; // flag to see if the game is still going on
	private MainCharacter mc; // player's character
	private List<Mob> mobs; // list of enemies to defeat

	private boolean death; // flag to see if the player died
	private int shooterMobStop = 0; // counter used to define the fire rate of the shooting mob

	/// Defines the mobs' positions
	private final int[][] pos = { { 500, 500 }, { 700, 100 } };

	/// Constructor of the Board class
	public Board() {
		initBoard();
	}

	/// Initialize the window, the main character and the mobsS
	private void initBoard() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		ingame = true;

		mc = new MainCharacter();

		initMobs();
		initMap();

		timer = new Timer(DELAY, this);
		timer.start();

	}

	/// Initialize the mobs
	private void initMobs() {
		
		mobs = new ArrayList<>();
		for (int[] p : pos) {
			mobs.add(new ShooterMob(p[0], p[1]));
			change = true;
		}
	}

	// Initialize the Map Generator
	private void initMap() {
		mg = new MapGenerator();

		mg.addNotStackable(mc.getBounds());
		for (Mob m : mobs)
			mg.addNotStackable(m.getBounds());

		map_element = new ArrayList<MapElement>();
	}

	/// Draws all the objects
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (change) {
			map_element = mg.getRandomMap();
			map_element.addAll(mg.getBorders());
			change = false;
		}
		if (ingame)
			drawObjects(g);
		else
			drawGameOver(g);
		Toolkit.getDefaultToolkit().sync();
	}

	// Draws the objects if the game is not over yet
	// (Main character, mobs, generic map element, shot)
	public void drawObjects(Graphics g) {

		if (mc.isVisible())
			g.drawImage(mc.getImage(), mc.getX(), mc.getY(), this);

		List<Shot> shots_list = mc.GetShots();
		for (Shot shot : shots_list) {
			if (shot.isVisible())
				g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
		}
		for (Mob mob : mobs) {
			if (mob.isVisible()) {
				g.drawImage(mob.getImage(), mob.getX(), mob.getY(), this);
			}
			if (mob instanceof ShooterMob) {
				List<Shot> mobShots = ((ShooterMob) mob).GetShots();
				for (Shot shot : mobShots) {
					if (shot.isVisible())
						g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
				}
			}
		}

		for (MapElement mp : map_element) {
			if (mp.isVisible())
				g.drawImage(mp.getImage(), mp.getX(), mp.getY(), this);
		}

		g.setColor(Color.white);
		g.drawString("Mobs left: " + mobs.size(), 5, 15);
	}

	/// Draws the objects when the game ends
	public void drawGameOver(Graphics g) {
		String msg;
		Font font;
		if (death) {
			Loser loser = new Loser(LOSER_X, LOSER_Y);
			g.drawImage(loser.getImage(), loser.getX(), loser.getY(), loser.getWidth(), loser.getHeight(), this);
			msg = "YOU DIED";
			font = new Font("Helvetica", Font.BOLD, 20);
			g.setColor(Color.RED);
			g.setFont(font);

		} else {
			msg = "YOU WIN";
			font = new Font("Helvetica", Font.BOLD, 14);
			g.setColor(Color.WHITE);
			g.setFont(font);
		}
		FontMetrics fm = getFontMetrics(font);
		g.drawString(msg, (WIDTH - fm.stringWidth(msg)) / 2, HEIGHT / 2);
	}

	/// Adapter defining the KeyPressed and KeyReleased events for the main
	/// character
	private class TAdapter extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			mc.keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (!death)
				mc.keyPressed(e);

			if (e.getKeyCode() == KeyEvent.VK_G)
				change = true;
		}
	}

	/// Repaints all the objects when an action is performed
	@Override
	public void actionPerformed(ActionEvent arg0) {
		inGame();
		updateMC();
		updateShots();
		updateMobs();

		checkCollisions();

		repaint();
	}

	/// Stops the game
	private void inGame() {
		if (!ingame) {
			timer.stop();
		}
	}

	/// Updates the position for the main character
	private void updateMC() {

		if (mc.isVisible())
			mc.move();
	}

	/// Updates the position for the main character's shots
	public void updateShots() {
		List<Shot> shots = mc.GetShots();
		for (int i = 0; i < shots.size(); i++) {
			Shot s = shots.get(i);
			int direction = s.getDirection();
			if (s.isVisible())
				switch (direction) {
				case 0:
					s.moveUp();
					break;
				case 1:
					s.moveDown();
					break;
				case 2:
					s.moveLeft();
					break;
				case 3:
					s.moveRight();
					break;
				}
			else
				shots.remove(i);
		}
	}

	/// Updates the position for the mobs
	private void updateMobs() {
		if (mobs.isEmpty()) {
			ingame = false;
			return;
		}
		for (int i = 0; i < mobs.size(); i++) {
			Mob m = mobs.get(i);
			if ((m instanceof ShooterMob && m.isVisible())) {
				updateShooterMobs((ShooterMob) m);
			} else {
				if(!((ShooterMob) m).GetShots().isEmpty())
					updateShooterMobs((ShooterMob) m);
				else
					mobs.remove(i);
			}
		}
	}

	/// Updates the position for the mobs shooting
	private void updateShooterMobs(ShooterMob m) {
		if (shooterMobStop == 0) {
			((ShooterMob) m).shoot();
			shooterMobStop = 200;
		} else
			shooterMobStop--;
		List<Shot> mobShots = ((ShooterMob) m).GetShots();
		for (int i = 0; i < mobShots.size(); i++) {
			Shot s = mobShots.get(i);
			int direction = s.getDirection();
			if (s.isVisible())
				switch (direction) {
				case 0:
					s.moveUp();
					break;
				case 1:
					s.moveDown();
					break;
				case 2:
					s.moveLeft();
					break;
				case 3:
					s.moveRight();
					break;
				}
			else
				mobShots.remove(i);
		}
	}

	// CHECK COLLISION BETWEEN A GENERIC ELEMENT WITH AN RECTANGLE
	public void obstacleCollision(Rectangle r1, GenericCharacter c) {
		for (MapElement mp : map_element) {
			Rectangle r4 = mp.getBounds();
			if (r1.intersects(r4)) {
				if (r1.getCenterX() >= r4.getX() && r1.getCenterX() <= r4.getX() + r4.getWidth()) {

					if (r1.getY() <= r4.getY() - (r1.getHeight() / 2)) // UP
						c.setY((int) r4.getY() - (int) r1.getHeight());
					else if (r1.getY() >= r4.getY() + (r1.getHeight() / 2)) // DOWN
						c.setY((int) r4.getY() + (int) r4.getHeight());

				} else if (r1.getCenterY() >= r4.getY() && r1.getCenterY() <= r4.getY() + r4.getHeight()) {

					if (r1.getX() <= r4.getX()) // LEFT
						c.setX(c.getX() - c.getSpd());
					else if (r1.getX() >= r4.getX() - r1.getWidth()) // RIGHT
						c.setX(c.getX() + c.getSpd());
				}
			}

		}
	}

	/// Checks collisions between the main character, the mobs and the shots
	public void checkCollisions() {
		Rectangle r1 = mc.getBounds();

		// CHECK COLLISION BETWEEN MC AND ENEMIES
		if (!death) {
			for (Mob mob : mobs) {
				Rectangle r2 = mob.getBounds();

				if (mob instanceof ShooterMob) {
					// COLLISION WITH MOB
					if (r1.intersects(r2) && mob.isVisible()) {
						death = true;
						ingame = false;
					}
					List<Shot> mobShots = ((ShooterMob) mob).GetShots();
					for (Shot shot : mobShots) {
						Rectangle r3 = shot.getBounds();
						// HIT FROM SHOOTER
						if (r3.intersects(r1) && mob.isVisible()) {
							death = true;
							ingame = false;
						}
						// CHECK COLLISON BETWEEN MOB SHOTS AND WALLS
						for (MapElement r : map_element) {
							Rectangle r4 = r.getBounds();
							if (r3.intersects(r4)) {
								shot.setVisible(false);
							}
						}
					}
				}
			}
		}

		// CHECK COLLISION BETWEEN OUR SHOTS AND OBSTACLE (WALLS, ENEMIES...)
		List<Shot> shots = mc.GetShots();
		for (Shot shot : shots) {
			Rectangle r3 = shot.getBounds();
			if (!death) {
				for (Mob mob : mobs) {
					Rectangle r2 = mob.getBounds();
					if (r3.intersects(r2)) {
						shot.setVisible(false);
						mob.setVisible(false);
						((ShooterMob) mob).setEnableShot(false);
					}
				}
				for (MapElement r : map_element) {
					Rectangle r4 = r.getBounds();
					if (r3.intersects(r4)) {
						shot.setVisible(false);
					}
				}
			}
		}

		// CHECK COLLISION BETWEEN MC AND ANY OBSTACLE
		obstacleCollision(r1, mc);
	}
	
	public MapGenerator getMg() {
		return mg;
	}

	public void setMg(MapGenerator mg) {
		this.mg = mg;
	}

	public List<MapElement> getMap_element() {
		return map_element;
	}

	public void setMap_element(List<MapElement> map_element) {
		this.map_element = map_element;
	}

	public boolean isChange() {
		return change;
	}

	public void setChange(boolean change) {
		this.change = change;
	}

	public boolean isIngame() {
		return ingame;
	}

	public void setIngame(boolean ingame) {
		this.ingame = ingame;
	}

	public MainCharacter getMc() {
		return mc;
	}

	public void setMc(MainCharacter mc) {
		this.mc = mc;
	}

	public List<Mob> getMobs() {
		return mobs;
	}

	public void setMobs(List<Mob> mobs) {
		this.mobs = mobs;
	}

	public boolean isDeath() {
		return death;
	}

	public void setDeath(boolean death) {
		this.death = death;
	}

	public int[][] getPos() {
		return pos;
	}

}
