package com.corey.silcoish.tiles;

import java.awt.image.BufferedImage;

import com.corey.silcoish.graphics.SpriteSheet;

public class Tile {
	public BufferedImage sprite;
	private String tileName;
	
	private int solid = 0;
	private int id = 0;
	
	public Tile(int id, String tileName, int solid, int x, int y, String spriteSheet){
		this.solid = solid;
		this.id = id;
		this.tileName = tileName;
		switch(spriteSheet){
		case "test":
			sprite = SpriteSheet.tiles.getTile(x * 16, y * 16);
			break;
		default:
			sprite = SpriteSheet.tiles.getTile(x * 16, y * 16);
		}
	}
	
	public int solid(){
		return solid;
	}
	
	public String tileName(){
		return tileName;
	}
	
	public int tileID(){
		return id;
	}
}
