package com.corey.silcoish.gamestate;

import java.awt.Graphics;

public class LoadingGameState extends GameState {
	
	int timer = 0;

	public LoadingGameState(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}
	
	public void init() {
		
	}
	
	public void update() {
		timer++;
		if(timer == 10)
			gsm.setState(GameStateManager.GAME);
	}

	public void render(Graphics g) {
		g.drawString("Loading...", 330, 250);
	}

	public void keyPressed(int k) {
		
	}

	public void keyReleased(int k) {
		
	}

	
}
