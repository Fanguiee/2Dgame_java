package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("I kill 1000 Mosquitos per second.");
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		window.pack();// fit to the size of subcomponents
		
		window.setLocationRelativeTo(null);//window in center of the screen
		window.setVisible(true);
		
		gamePanel.startGameThread();
	}

}
