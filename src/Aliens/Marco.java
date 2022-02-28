package Aliens;
import javax.swing.JFrame;

public class Marco extends JFrame {

	Marco()
	{
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Game2D game = new Game2D();
		
		add(game);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
}
