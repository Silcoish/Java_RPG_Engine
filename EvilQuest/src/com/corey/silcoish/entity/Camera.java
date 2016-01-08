package com.corey.silcoish.entity;

import com.corey.silcoish.GamePanel;

public class Camera {

	public int xOffset = 0;
	public int yOffset = 0;
	public Entity focusEntity;
	
	public Camera(int x, int y, Entity entity){
		xOffset = x;
		yOffset = y;
		focusEntity = entity;
	}
	
	public void update(){
		this.xOffset = focusEntity.x - (GamePanel.WIDTH / 2);
		this.yOffset = focusEntity.y - (GamePanel.HEIGHT / 2);
	}
	
	public void moveCameraPixels(int x, int y){
		xOffset += x;
		yOffset += y;
	}
	
	public void moveCameraTiles(int x, int y){
		xOffset += x * 16;
		yOffset += y * 16;
	}
	
	public void setOffset(int x, int y){
		xOffset = x;
		yOffset = y;
	}
}
