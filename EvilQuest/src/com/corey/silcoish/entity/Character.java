package com.corey.silcoish.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import com.corey.silcoish.battle.Battle;
import com.corey.silcoish.entity.enemy.Chuu;
import com.corey.silcoish.entity.enemy.Dragon;
import com.corey.silcoish.entity.enemy.Enemy;
import com.corey.silcoish.graphics.Animation;
import com.corey.silcoish.graphics.SpriteSheet;
import com.corey.silcoish.levels.Level;

public class Character extends Entity {

	//Directions
	//TODO: Make Enum
	private static final int IDLEDOWN = 0;
	private static final int IDLELEFT = 1;
	private static final int IDLERIGHT = 2;
	private static final int IDLEUP = 3;
	private static final int WALKINGDOWN = 4;
	private static final int WALKINGLEFT = 5;
	private static final int WALKINGRIGHT = 6;
	private static final int WALKINGUP = 7;
	private int currentAnimation = 0;
	
	private String direction = "down";
	private String previousDirection = "down";
	
	private Random rand = new Random();
	public boolean inBattle = false;
	public Battle battle;
	
	//Stats
	public int lvl;
	public int maxHealth;
	public int health;
	public int attack;
	public int defense;
	public int speed;

	public Character(int x, int y, Level level) {
		super(x, y, level);
		width = 16;
		height = 16;
		
		//Set stats
		lvl = 10;
		maxHealth = 20;
		health = maxHealth;
		attack = 10;
		defense = 6;
		speed = 10;

		// Idle animation
		BufferedImage[] idleDown = new BufferedImage[1];
		idleDown[0] = SpriteSheet.mainCharacter.getTile16(0, 0);
		BufferedImage[] idleLeft = new BufferedImage[1];
		idleLeft[0] = SpriteSheet.mainCharacter.getTile16(2, 0);
		BufferedImage[] idleRight = new BufferedImage[1];
		idleRight[0] = SpriteSheet.mainCharacter.getTile16(1, 0);
		BufferedImage[] idleUp = new BufferedImage[1];
		idleUp[0] = SpriteSheet.mainCharacter.getTile16(3, 0);
		//Walking Down
		BufferedImage[] walkingDown = new BufferedImage[2];
		walkingDown[0] = SpriteSheet.mainCharacter.getTile16(0, 1);
		walkingDown[1] = SpriteSheet.mainCharacter.getTile16(0, 2);
		//Walking Left
		BufferedImage[] walkingLeft = new BufferedImage[2];
		walkingLeft[0] = SpriteSheet.mainCharacter.getTile16(2, 0);
		walkingLeft[1] = SpriteSheet.mainCharacter.getTile16(2, 1);
		//Walking Right
		BufferedImage[] walkingRight = new BufferedImage[2];
		walkingRight[0] = SpriteSheet.mainCharacter.getTile16(1, 0);
		walkingRight[1] = SpriteSheet.mainCharacter.getTile16(1, 1);
		//Walking Up
		BufferedImage[] walkingUp = new BufferedImage[2];
		walkingUp[0] = SpriteSheet.mainCharacter.getTile16(3, 1);
		walkingUp[1] = SpriteSheet.mainCharacter.getTile16(3, 2);

		animation = new Animation();
		animation.addAnimation(idleDown);
		animation.addAnimation(idleLeft);
		animation.addAnimation(idleRight);
		animation.addAnimation(idleUp);
		animation.addAnimation(walkingDown);
		animation.addAnimation(walkingLeft);
		animation.addAnimation(walkingRight);
		animation.addAnimation(walkingUp);
		animation.setDealy(200);
		animation.setFrames(IDLEDOWN);
	}

	public void update() {
		// System.out.println("Current Tile: " + level.getTile(x/16 + 1,
		// y/16).tileName());
		// Collision testing
		checkCollision(dx, dy);
		
		
		if(!inBattle && (dx != 0 || dy != 0)){
			if(rand.nextInt(500) == 10){
				inBattle = true;
				setDy(0);
				setDx(0);
				ArrayList<Enemy> enemy = new ArrayList<Enemy>();
				enemy.add(new Chuu(2));
				enemy.add(new Dragon(4));
				enemy.add(new Chuu(2));
				enemy.add(new Chuu(2));
				enemy.add(new Chuu(2));
				battle = new Battle(enemy, this);
			}
		}
		
		setDirection();
		setAnimation();
		
		// update the animation
		animation.update();
	}
	
	private void setDirection(){
		previousDirection = direction;
		if(dx > 0) direction = "right";
		else if(dx < 0) direction = "left";
		else if(dy > 0) direction = "down";
		else if(dy < 0) direction = "up";
		else direction = "idle";
	}
	
	private void setAnimation(){
		if(direction.equals("left") && currentAnimation != WALKINGLEFT) {
			animation.setFrames(WALKINGLEFT);
			currentAnimation = WALKINGLEFT;
		}else if(direction.equals("right") && currentAnimation != WALKINGRIGHT){
			animation.setFrames(WALKINGRIGHT);
			currentAnimation = WALKINGRIGHT;
		}else if(direction.equals("up") && currentAnimation != WALKINGUP){
			animation.setFrames(WALKINGUP);
			currentAnimation = WALKINGUP;
		}else if(direction.equals("down") && currentAnimation != WALKINGDOWN){
			animation.setFrames(WALKINGDOWN);
			currentAnimation = WALKINGDOWN;
		}else if(dx == 0 && dy == 0){
			if(previousDirection.equals("down")){
				animation.setFrames(IDLEDOWN);
				currentAnimation = IDLEDOWN;
			}else if(previousDirection.equals("left")){
				animation.setFrames(IDLELEFT);
				currentAnimation = IDLELEFT;
			}else if(previousDirection.equals("right")){
				animation.setFrames(IDLERIGHT);
				currentAnimation = IDLERIGHT;
			}else if(previousDirection.equals("up")){
				animation.setFrames(IDLEUP);
				currentAnimation = IDLEUP;
			}
		}
	}

	public void render(Graphics g) {
		g.drawImage(animation.getImage(), x - level.camera.xOffset - (width / 2), y - level.camera.yOffset - (height / 2), null);
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}

}
