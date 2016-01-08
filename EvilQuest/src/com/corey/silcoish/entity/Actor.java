package com.corey.silcoish.entity;

import java.awt.Graphics;
import java.util.ArrayList;

import com.corey.silcoish.graphics.SpriteSheet;
import com.corey.silcoish.levels.Level;

public class Actor extends Entity {
	
	public int xtile, ytile;
	public String name;
	public ArrayList<String> text;
	
	//Door variables
	public int x, y, newMapID, newXTile, newYTile;

	public Actor(String name, int xsprite, int ysprite, int xtile, int ytile, Level level) {
		super(xtile * 16, ytile * 16, level);
		this.xtile = xtile;
		this.ytile = ytile;
		this.name = name;
		text = new ArrayList<String>();
		image = SpriteSheet.actors.getTile16(xsprite, ysprite);
	}
	
	public void update(){
		
	}

	public void render(Graphics g, Camera camera){
		g.drawImage(image, x - camera.xOffset, y - camera.yOffset, null);
	}
	
	//Door methods
	public int getNewMapID(){
		return newMapID;
	}
	
	public int getNewXTile(){
		return newXTile;
	}
	
	public int getNewYTile(){
		return newYTile;
	}
}
