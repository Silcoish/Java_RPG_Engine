package com.corey.silcoish.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.corey.silcoish.graphics.Animation;
import com.corey.silcoish.levels.Door;
import com.corey.silcoish.levels.Level;

public abstract class Entity {

	public Level level;
	public int x, dx = 0;
	public int y, dy = 0;
	public int width, height = 0;
	public BufferedImage image;
	
	protected boolean moving = false;
	protected long movingTime = 0;
	protected int pixelsMoved = 0;
	
	public Animation animation;

	public Entity(int x, int y, Level level) {
		this.x = x;
		this.y = y;
		this.level = level;
	}
	
	public void update(){
		
	}
	
	public void render(Graphics g){
		
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setDx(int dx){
		this.dx = dx;
	}
	
	public void setDy(int dy){
		this.dy = dy;
	}
	
	public boolean checkEntityCollision(int dx, int dy){
		for(int i = 0; i < level.actors.size(); i++){
			if(dx > 0 && (x + 4 + dx) / 16 == level.actors.get(i).xtile && (y + dy) / 16 == level.actors.get(i).getYTile()){
				if(level.actors.get(i) instanceof Door)
					changeLevel(level.actors.get(i));
				return true;
			}
			if(dx < 0 && (x - 6 + dx) / 16 == level.actors.get(i).xtile && (y + dy) / 16 == level.actors.get(i).getYTile()) {
				if(level.actors.get(i) instanceof Door)
					changeLevel(level.actors.get(i));
				return true;
			}
			if(dy > 0 && (x + dx) / 16 == level.actors.get(i).xtile && (y + 6 + dy) / 16 == level.actors.get(i).getYTile()){
				if(level.actors.get(i) instanceof Door)
					changeLevel(level.actors.get(i));
				return true;
			}
			if(dy < 0 && (x + dx) / 16 == level.actors.get(i).xtile && (y - 2 + dy) / 16 == level.actors.get(i).getYTile()){
				if(level.actors.get(i) instanceof Door)
					changeLevel(level.actors.get(i));
				return true;
			}
		}
		return false;
	}
	
	private void changeLevel(Actor a){
		level.mgs.setLevel(a.getNewMapID());
		level.mgs.setCharXTile(a.getNewXTile());
		level.mgs.setCharYTile(a.getNewYTile());
	}
	
	public boolean checkCollision(int dx, int dy){
		if(checkEntityCollision(dx, dy)) return false;
		
		if(dx > 0 && level.getTile(((x + 4 + dx) / 16), (y / 16)).solid() != 1)
			x += dx;
		if(dx < 0 && level.getTile(((x - 6 + dx) / 16), (y / 16)).solid() != 1)
			x += dx;
		if(dy > 0 && level.getTile((x / 16), ((y + 6 + dy) / 16)).solid() != 1)
			y += dy;
		if(dy < 0 && level.getTile((x / 16), ((y + dy) / 16)).solid() != 1)
			y += dy;
		
		return false;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getXTile(){
		return x / 16;
	}
	
	public int getYTile(){
		return y / 16;
	}
}
