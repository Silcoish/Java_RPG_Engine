package com.corey.silcoish.gamestate;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.corey.silcoish.GamePanel;

public class MainMenuState extends GameState {
	BufferedImage bg;

	public MainMenuState(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}
	
	public void init() {
		try {
			bg = ImageIO.read(MainMenuState.class.getResource("/MainMenu/MainMenu.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		
	}

	public void render(Graphics g) {
		g.drawImage(bg, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
	}

	public void keyPressed(int k) {
		
	}

	public void keyReleased(int k) {
		
	}

}
