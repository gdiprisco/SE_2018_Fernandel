package demo1;
import java.awt.EventQueue;
import java.io.IOException;
import javax.swing.JFrame;

public class MainEx extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MainEx() {
		initUI();
	}
	
	public void initUI() {
		add(new Board());
		setResizable(false);
		pack();
		
		setTitle("Game Project");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Runtime.getRuntime().addShutdownHook(new Thread() {

		    @Override
		    public void run() {
	        	if(System.getProperty("os.name").startsWith("Mac OS")) {
			        enableMacWASD();
	        	}
		    }

		});
	}
	
	// This method disable or enable an option on Mac OS system
	// that cause a bug on a specific key.
	private static void disableMacWASD() {
		try {
			Runtime.getRuntime().exec("defaults write -g ApplePressAndHoldEnabled -bool false");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void enableMacWASD() {
		try {
			Runtime.getRuntime().exec("defaults write -g ApplePressAndHoldEnabled -bool true");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		}
	}
	
	public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
        	if(System.getProperty("os.name").startsWith("Mac OS")) {
            	disableMacWASD();
        	}
        	MainEx ex = new MainEx();
            ex.setVisible(true);
        });
    }
	
}
