package com.corey.silcoish.gamestate;

import java.awt.Color;
import java.awt.Graphics;

import com.corey.silcoish.GamePanel;
import com.corey.silcoish.flags.Flags;
import com.corey.silcoish.levels.Level;
import com.corey.silcoish.xml.XMLReader;

public class MainGameState extends GameState {
	Level level;
	public XMLReader xml;
	public Flags flags;
	
	long loadingTime = 0;
	boolean changingScreen = false;
	long changingTime = 0;
	long aimTime = 200;

	public MainGameState(GameStateManager gsm){
		this.gsm = gsm;
	}
	
	public void init() {
		flags = new Flags();
		loadingTime = System.currentTimeMillis();
		xml = new XMLReader(this);
		level = xml.level.get(0);
		loadingTime = (System.currentTimeMillis() - loadingTime);
		System.out.println("Time taken to load: " + ((float)loadingTime / 1000));
	}
	public void update() {
		if(!changingScreen)
			level.update();
		else{
			if(System.currentTimeMillis() - changingTime > aimTime)
				changingScreen = false;
		}
			
	}

	public void render(Graphics g) {
		if(!changingScreen)
			level.render(g);
		else{
			g.setColor(Color.black);
			g.drawRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
			g.setColor(Color.white);
			g.drawString("Loading...", 330, 250);
		}
	}

	public void keyPressed(int k) {
		level.keyPressed(k);
	}

	public void keyReleased(int k) {
		level.keyReleased(k);
	}
	
	public void setLevel(int ID){
		changingScreen = true;
		changingTime = System.currentTimeMillis();
		level = xml.level.get(ID);
	}
	
	public void setCharXTile(int x){
		level.character.setX(x * 16);
	}
	
	public void setCharYTile(int y){
		level.character.setY(y * 16);
	}

}
