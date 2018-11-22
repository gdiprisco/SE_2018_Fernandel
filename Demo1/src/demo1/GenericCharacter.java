package demo1;

public class GenericCharacter extends GenericElement{
	private int atk;
	private int hp;
	private int spd;
	

	public GenericCharacter(int x, int y, int atk, int hp, int spd) {
		super(x, y);
		this.atk = atk;
		this.hp = hp;
		this.spd = spd;
		
	}
	
	public int getAtk() {
		return atk;
	}
	
	public void setAtk(int atk) {
		this.atk = atk;
	}
	
	public int getHp() {
		return hp;
	}
	
	public void setHp(int hp) {
		this.hp = hp;
	}
	
	public int getSpd() {
		return spd;
	}
	
	public void setSpd(int spd) {
		this.spd = spd;
	}
}
