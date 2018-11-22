package demo1;

public class Loser extends GenericElement{

	
	public Loser(int x, int y) {
		super(x,y);
		initLoser();
	}
	
	public void initLoser() {
		loadImage("src/images/dog.gif");
		getImageDimensions();
	}
}
