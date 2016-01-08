package com.corey.silcoish.levels;

import com.corey.silcoish.entity.Actor;

public class Door extends Actor{
	
	public Door(int xsprite, int ysprite, int xtile, int ytile, Level level, int newMapID, int newXTile, int newYTile){
		super("Door", xsprite, ysprite, xtile, ytile, level);
		this.newMapID = newMapID;
		this.newXTile = newXTile;
		this.newYTile = newYTile;
	}
}
