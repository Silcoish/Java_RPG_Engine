package com.corey.silcoish.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * This is where all the spritesheets are loaded
 * ready to have the tiles and entities cut out
 */

public class SpriteSheet {

	private String path;
	public final int SIZE;
	public BufferedImage image;

	public static SpriteSheet tiles = new SpriteSheet("/spriteSheets/test.png", 1024);
	public static SpriteSheet mainCharacter = new SpriteSheet("/spriteSheets/Characters/mainCharacter.png", 64);
	public static SpriteSheet actors = new SpriteSheet("/spriteSheets/Characters/actors.png", 64);
	public static SpriteSheet enemies = new SpriteSheet("/spriteSheets/enemies.png", 576);
	
	public static SpriteSheet cars = new SpriteSheet("/spriteSheets/cars.png", 64);

	public SpriteSheet(String path, int size) {
		this.path = path;
		this.SIZE = size;
		loadSheet();
	}

	private void loadSheet() {
		try {
			image = ImageIO.read(this.getClass().getResource(path));
		} catch (IOException e) {
			System.exit(0);
			e.printStackTrace();
		}
	}

	public BufferedImage getTile(int x, int y) {
		return image.getSubimage(x, y, 16, 16);
	}
	
	public BufferedImage getTile(int x, int y, int size){
		return image.getSubimage(x, y, size, size);
	}
	
	public BufferedImage getTile16(int x, int y) {
		return image.getSubimage(x * 16, y * 16, 16, 16);
	}
}
