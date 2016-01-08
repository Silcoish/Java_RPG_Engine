package com.corey.silcoish.levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import com.corey.silcoish.GamePanel;
import com.corey.silcoish.entity.Actor;
import com.corey.silcoish.entity.Camera;
import com.corey.silcoish.entity.Character;
import com.corey.silcoish.entity.Entity;
import com.corey.silcoish.gamestate.MainGameState;
import com.corey.silcoish.tiles.Tile;
import com.corey.silcoish.xml.XMLReader;

public class Level {

	//Systems
	public XMLReader xml;
	public MainGameState mgs;
	public Camera camera;
	
	public int ID;
	public String mapName;
	public BufferedImage map;

	//two sets of tiles for 2 depths
	public Tile[] level;
	public Tile[] level2;
	private Tile defaultTile;
	
	public ArrayList<Actor> actors;

	public int width, height;

	Random rand = new Random();

	public ArrayList<Entity> enemies;
	public ArrayList<Door> doors;

	public Character character;

	public Level(XMLReader xml, MainGameState mgs, int ID, String mapName, int w, int h, Tile defaultTile) {
		this.xml = xml;
		this.mgs = mgs;
		this.ID = ID;
		this.mapName = mapName;
		this.width = w;
		this.height = h;
		this.defaultTile = defaultTile;
		character = new Character(16 * 13, 16 * 12, this);
		camera = new Camera(character.getX(), character.getY(), character);
		doors = new ArrayList<Door>();
		init();
	}

	public void init() {
		level = new Tile[width * height];
		level2 = new Tile[width * height];
		actors = new ArrayList<Actor>();
	}

	public void update() {
		if(!character.inBattle){
			character.update();
			camera.update();
			for(int i = 0; i < actors.size(); i++){
				actors.get(i).update();
			}
			
			//Determine wild battles
			
		}else
			character.battle.Update();
	}

	//renders world or battle
	public void render(Graphics g) {
		//character.inBattle = false;
		if(!character.inBattle){
			int x0 = camera.xOffset >> 4;
			int x1 = (camera.xOffset + GamePanel.WIDTH + 16) >> 4;
			int y0 = camera.yOffset >> 4;
			int y1 = (camera.yOffset + GamePanel.HEIGHT + 16) >> 4;
			
			//Render the first layer
			for (int x = x0; x < x1; x++) {
				for (int y = y0; y < y1; y++) {
					if (x >= width || x < 0 || y >= height || y < 0)
						g.drawImage(defaultTile.sprite, (x * 16) - camera.xOffset, (y * 16) - camera.yOffset, null);
					else{
						g.drawImage(level[x + y * width].sprite, (x * 16) - camera.xOffset, (y * 16) - camera.yOffset, null);
					}
				}
			}
			
			//Render the actors
			for(int i = 0; i < actors.size(); i++){
				actors.get(i).render(g, camera);
			}
			
			//Render the character
			character.render(g);
			
			//Render the second layer
			for (int x = x0; x < x1; x++) {
				for (int y = y0; y < y1; y++) {
					if (x >= width || x < 0 || y >= height || y < 0)
						g.drawImage(defaultTile.sprite, (x * 16) - camera.xOffset, (y * 16) - camera.yOffset, null);
					else{
						if(level2[x+y*width].tileID() != 0)
							g.drawImage(level2[x + y * width].sprite, (x * 16) - camera.xOffset, (y * 16) - camera.yOffset, null);
					}
				}
			}
			
			//Render Strings
			g.drawString(mapName, 10, 15);
		}else{
			//render battle
			character.battle.render(g);
		}
	}

	public void keyPressed(int k) {
		if (k == 37) character.setDx(-2);
		if (k == 39) character.setDx(2);
		if (k == 38) character.setDy(-2);
		if (k == 40) character.setDy(2);
	}
		
	public void keyReleased(int k) {
	if(!character.inBattle){
		if (k == 37) character.setDx(0);
		if (k == 39) character.setDx(0);
		if (k == 38) character.setDy(0);
		if (k == 40) character.setDy(0);
		if (k == 32) mgs.flags.save();
	}else
		character.setDx(0);
		character.setDy(0);
		if(character.battle != null)
			character.battle.keyReleased(k);
	}
	
	public Tile getTile(int x, int y){
		//Checks weather the tile is out of bounds, and if it is, returns the default tile
		if(x + y * width < 0 || x + y * width > width * height)
			return defaultTile;
		
		return level[x + y * width];
	}
}
