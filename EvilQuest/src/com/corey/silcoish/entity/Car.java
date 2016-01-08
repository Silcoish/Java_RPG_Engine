package com.corey.silcoish.entity;

import java.awt.Graphics;

import com.corey.silcoish.graphics.SpriteSheet;
import com.corey.silcoish.levels.Level;

public class Car extends Entity{

	public Car(int x, int y, Level level) {
		super(x, y, level);
		image = SpriteSheet.cars.getTile(0, 0);
	}
	
	public void update(){
		x += dx;
		y += dy;
	}
	
	public void render(Graphics g){
		g.drawImage(image, x - level.camera.xOffset, y - level.camera.yOffset, null);
	}

}
