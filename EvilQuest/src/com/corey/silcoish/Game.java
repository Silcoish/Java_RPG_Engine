package com.corey.silcoish;

import javax.swing.JFrame;

public class Game {

	private static final String NAME = "Epic Game";
	private static final String VERSION = "0.0.5a";
	
	//Enter application here
	public static void main(String[] args){
		JFrame window = new JFrame(NAME + " v" + VERSION);
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
}
