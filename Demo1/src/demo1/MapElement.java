package demo1;

public class MapElement extends GenericElement {

	public MapElement(int x, int y, String image_name) {
		super(x, y);
		// TODO Auto-generated constructor stub
		loadImage(image_name);
		getImageDimensions();
	}

}
