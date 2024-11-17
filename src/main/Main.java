package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //lets the window properly close when user clicks the 'x' button
		window.setResizable(false);
		window.setTitle("BlueBoyAdventure");
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		window.pack(); //makes the window to be size to fit the preferred size and layouts of its subcomponenets (gamePanel)
		
		window.setLocationRelativeTo(null); //window will be displayed at the center of the screen
		window.setVisible(true);
		
		gamePanel.setupGame();
		gamePanel.startGameThread();
		

	}

}
