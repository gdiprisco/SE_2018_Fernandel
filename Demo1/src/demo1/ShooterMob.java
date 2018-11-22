package demo1;

import java.util.ArrayList;
import java.util.List;

public class ShooterMob extends Mob {
	private List<Shot> shots;
	private boolean enable_shot = true;

	public ShooterMob(int x, int y) {
		super(x, y);
		shots = new ArrayList<>();
		initShooterMob();
	}

	private void initShooterMob() {
		loadImage("src/images/mushroom.png");
		getImageDimensions();
	}

	public List<Shot> GetShots() {
		return shots;
	}
	
	public void setEnableShot(boolean enable_shot) {
		this.enable_shot = enable_shot;
	}

	public void shoot() {
		if (enable_shot) {
			shots.add(new Shot(x + width / 2, y, 0, true));
			shots.add(new Shot(x + width / 2, y + height, 1, true));
			shots.add(new Shot(x, y + height / 2, 2, true));
			shots.add(new Shot(x + width, y + height / 2, 3, true));
		}

	}

}
