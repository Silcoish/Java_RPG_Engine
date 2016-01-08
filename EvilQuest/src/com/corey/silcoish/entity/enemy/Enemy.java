package com.corey.silcoish.entity.enemy;

import java.awt.image.BufferedImage;

import com.corey.silcoish.graphics.SpriteSheet;

public class Enemy {

	protected BufferedImage image;
	protected String name;
	
	//stats
	protected int baseHealth;
	protected int level;
	protected int maxHealth;
	protected int health;
	protected int attack;
	protected int defense;
	protected int speed;
	
	public Enemy(int level, int x, int y){
		this.level = level;
		image = SpriteSheet.enemies.getTile(x*64, y*64, 64);
	}
	
	public BufferedImage getImage(){
		return image;
	}
	
	public String getName(){
		return name;
	}
	
	public int getBaseHealth(){
		return baseHealth;
	}
	
	public int getLevel(){
		return level;
	}
	
	public int getMaxHealth(){
		return maxHealth;
	}
	
	public int getHealth(){
		return health;
	}
	
	public float getPercentage(){
		return (float)getHealth() / (float)getMaxHealth();
	}
	
	public void setHealth(int i){
		health += i;
	}
}
