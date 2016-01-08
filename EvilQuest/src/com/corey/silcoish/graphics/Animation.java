package com.corey.silcoish.graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/*
 * The animation system to keep track of the current
 * frame, all the frames of the current animation,
 * and the different animation sets. 
 */

public class Animation {
	
	public ArrayList<BufferedImage[]> animations;

	private BufferedImage[] frames;
	private int currentFrame;
	
	private long startTime;
	private long delay;
	
	private boolean playedOnce;
	
	public Animation(){
		playedOnce = false;
		animations = new ArrayList<BufferedImage[]>();
	}
	
	public void setFrames(int frame){
		this.frames = animations.get(frame);
		currentFrame = 0;
		startTime = System.nanoTime();
		playedOnce = false;
	}
	
	public void setDealy(long d){
		delay = d;
	}
	
	public void setFrame(int i){
		currentFrame = i;
	}
	
	public void addAnimation(BufferedImage[] bi){
		animations.add(bi);
	}
	
	public void update(){
		if(delay == -1) return;
		
		long elapsed = (System.nanoTime() - startTime) / 1000000;
		if(elapsed > delay){
			currentFrame++;
			startTime = System.nanoTime();
		}
		if(currentFrame == frames.length){
			currentFrame = 0;
			playedOnce = true;
		}
	}
	
	public int getFrame(){
		return currentFrame;
	}
	
	public BufferedImage getImage(){
		return frames[currentFrame];
	}
	
	public boolean hasPlayedOnce(){
		return playedOnce;
	}
}
