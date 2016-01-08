package com.corey.silcoish.battle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import com.corey.silcoish.GamePanel;
import com.corey.silcoish.entity.Character;
import com.corey.silcoish.entity.enemy.Enemy;
import com.corey.silcoish.graphics.Animation;
import com.corey.silcoish.graphics.SpriteSheet;

public class Battle {
	
	private Character player;
	
	public ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	//images
	public BufferedImage character;
	public BufferedImage gui;
	private BufferedImage background;
	public ArrayList<BufferedImage> healthBars;
	
	//Animation stuff
	Animation animation1;
	private boolean animationComplete = false;
	private Animation arrow;
	private Animation arrow1;
	
	//Variable to keep track of battle
	private int currentState = 0;
	private String text = "";
	private boolean renderAttackGUI = false;
	private String[] options = {"Attack", "Defend", "Item", "Run"};
	private int selected = 0;
	private int selected2 = 0;

	//A list of all the states
	//TODO: Swap this to an enum
	private static final int OPENING = 0;
	private static final int OPENING2 = 1;
	private static final int GUI = 2;
	private static final int SELECTATTACK = 3;
	private static final int ATTACK = 4;
	private static final int FINISH = 5;
	private static final int FINISHLEAVE = 6;
	
	private static final int RUNNING = 10;
	
	public Battle(ArrayList<Enemy> enemies, Character player){
		this.player = player;
		this.enemies = enemies;
		character = SpriteSheet.enemies.getTile(512, 512, 64);
		gui = SpriteSheet.enemies.getTile(448, 512, 64);
		try {
			background = ImageIO.read(this.getClass().getResource("/textures/background.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//Animation stuff
		try {
			BufferedImage[] animation1_1 = new BufferedImage[4];
			animation1_1[0] = ImageIO.read(this.getClass().getResource("/textures/battle/battleAnimations/1/1.png"));
			animation1_1[1] = ImageIO.read(this.getClass().getResource("/textures/battle/battleAnimations/1/2.png"));
			animation1_1[2] = ImageIO.read(this.getClass().getResource("/textures/battle/battleAnimations/1/3.png"));
			animation1_1[3] = ImageIO.read(this.getClass().getResource("/textures/battle/battleAnimations/1/4.png"));
			animation1 = new Animation();
			animation1.addAnimation(animation1_1);
			animation1.setDealy(300);
			animation1.setFrames(0);
			
			BufferedImage[] a1 = new BufferedImage[2];
			a1[0] = ImageIO.read(this.getClass().getResource("/textures/battle/icons/arrow0.png"));
			a1[1] = ImageIO.read(this.getClass().getResource("/textures/battle/icons/arrow1.png"));
			arrow = new Animation();
			arrow.addAnimation(a1);
			arrow.setDealy(250);
			arrow.setFrames(0);
			
			BufferedImage[] a2 = new BufferedImage[2];
			a2[0] = ImageIO.read(this.getClass().getResource("/textures/battle/icons/arrow2.png"));
			a2[1] = ImageIO.read(this.getClass().getResource("/textures/battle/icons/arrow3.png"));
			arrow1 = new Animation();
			arrow1.addAnimation(a2);
			arrow1.setDealy(250);
			arrow1.setFrames(0);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//Health bars
		healthBars = new ArrayList<BufferedImage>();
		try {
			healthBars.add(ImageIO.read(this.getClass().getResource("/spriteSheets/battle/0.png")));
			healthBars.add(ImageIO.read(this.getClass().getResource("/spriteSheets/battle/1.png")));
			healthBars.add(ImageIO.read(this.getClass().getResource("/spriteSheets/battle/2.png")));
			healthBars.add(ImageIO.read(this.getClass().getResource("/spriteSheets/battle/3.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		action();
	}
	
	public void Update(){
		for(int i = 0; i < enemies.size(); i++){
			if(enemies.get(i).getHealth() <= 0) enemies.remove(i);
		}
		
		if(!animationComplete) animation1.update();
		if(animation1.hasPlayedOnce()) animationComplete = true;
		arrow.update();
		arrow1.update();
	}
	
	//renders all the enemies, player, and GUI
	public void render(Graphics g){
		g.setColor(Color.white);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		//Draw background
		g.drawImage(background, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		g.setColor(Color.black);
		for(int i = 0; i < enemies.size(); i++){
			if(enemies.size() == 1){
				g.drawImage(enemies.get(i).getImage(), (GamePanel.WIDTH - 64) / 2, 30, null);
				g.drawString("Lvl: " + enemies.get(i).getLevel(), (GamePanel.WIDTH - 64) / 2 + 20, 30);
				g.drawImage(healthBarColor(enemies.get(i).getPercentage()), (GamePanel.WIDTH - 64) / 2 + 8, 100, (int)(50 * (enemies.get(i).getPercentage())), 5, null);
				if(currentState == SELECTATTACK)
					g.drawImage(arrow.getImage(), GamePanel.WIDTH - 74 - 96 - 32 - (selected2 * 64), 8, null);
			}
			else if(enemies.size() == 2){
				g.drawImage(enemies.get(i).getImage(), GamePanel.WIDTH / 2 - (64 * i), 30, null);
				g.drawString("Lvl: " + enemies.get(i).getLevel(), GamePanel.WIDTH / 2 - (64 * i) + 20, 30);
				g.drawImage(healthBarColor(enemies.get(i).getPercentage()), GamePanel.WIDTH / 2 - (64 * i) + 8, 100, (int)(50 * (enemies.get(i).getPercentage())), 5, null);
				if(currentState == SELECTATTACK)
					g.drawImage(arrow.getImage(), GamePanel.WIDTH - 74 - 96 - (selected2 * 64), 8, null);
			}
			else if(enemies.size() == 3){
				g.drawImage(enemies.get(i).getImage(), (GamePanel.WIDTH + 64) / 2 - (64 * i), 30, null);
				g.drawString("Lvl: " + enemies.get(i).getLevel(), (GamePanel.WIDTH + 64) / 2 - (64 * i) + 20, 30);
				g.drawImage(healthBarColor(enemies.get(i).getPercentage()), (GamePanel.WIDTH + 64) / 2 - (64 * i) + 8, 100, (int)(50 * (enemies.get(i).getPercentage())), 5, null);
				if(currentState == SELECTATTACK)
					g.drawImage(arrow.getImage(), GamePanel.WIDTH - 74 - 64 - (selected2 * 64), 8, null);
			}
			else if(enemies.size() == 4){
				g.drawImage(enemies.get(i).getImage(), (GamePanel.WIDTH + 128) / 2 - (64 * i), 30, null);
				g.drawString("Lvl: " + enemies.get(i).getLevel(), (GamePanel.WIDTH + 128) / 2 - (64 * i) + 20, 30);
				g.drawImage(healthBarColor(enemies.get(i).getPercentage()), (GamePanel.WIDTH + 128) / 2 - (64 * i) + 8, 100, (int)(50 * (enemies.get(i).getPercentage())), 5, null);
				if(currentState == SELECTATTACK)
					g.drawImage(arrow.getImage(), GamePanel.WIDTH - 74 - 32 - (selected2 * 64), 8, null);
				
			}
			else{
				g.drawImage(enemies.get(i).getImage(), (GamePanel.WIDTH + 192) / 2 - (64 * i), 30, null);
				g.drawString("Lvl: " + enemies.get(i).getLevel(), (GamePanel.WIDTH + 192) / 2 - (64 * i) + 20, 30);
				g.drawImage(healthBarColor(enemies.get(i).getPercentage()), (GamePanel.WIDTH + 170) / 2 - (64 * i) + 20, 100, (int)(50 * (enemies.get(i).getPercentage())), 5, null);
				if(currentState == SELECTATTACK)
					g.drawImage(arrow.getImage(), GamePanel.WIDTH - 74 - (selected2 * 64), 8, null);
			}
		}
		
		g.drawImage(character, (GamePanel.WIDTH - 64) / 2, GamePanel.HEIGHT - 132, null);
		g.drawImage(gui, 10, GamePanel.HEIGHT - 70, GamePanel.WIDTH - 20, 64, null);
		
		//Render the attacking gui if time is right
		if(renderAttackGUI){
			//Draw the background
			g.drawImage(gui, GamePanel.WIDTH - 110, 115, 100, 80, null);
			g.drawImage(arrow1.getImage(), GamePanel.WIDTH - 30, GamePanel.HEIGHT - 140 + (selected * 17), null);
			for(int i = 0; i < options.length; i++){
				g.drawString(options[i], GamePanel.WIDTH - 90, GamePanel.HEIGHT - 132 + (i * 17));
			}
		}

		
		//Displays messages
		renderText(g);
		
		g.drawString("[Press Z]", GamePanel.WIDTH - 85, GamePanel.HEIGHT - 15);
		
		//Opening Animation to be drawn over everything
		if(!animationComplete) g.drawImage(animation1.getImage(), 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
	}
	
	private void renderText(Graphics g){
		g.setColor(Color.black);
		g.drawString(text, 30, GamePanel.HEIGHT - 35);
		g.drawImage(arrow.getImage(), GamePanel.WIDTH - 30, GamePanel.HEIGHT - 25, null);
		
		System.out.println(selected2);
	}
	
	private void action(){
		if(currentState == OPENING){ 
			text = "A " + enemies.get(0).getName() + " and " + (enemies.size() - 1) + " other monsters attacked!";
			return;
		}
		else if(currentState == OPENING2){
			text = "What will Charmander do?";
			return;
		}
		else if(currentState == GUI){
			renderAttackGUI = true;
			return;
		}else if(currentState == RUNNING){
			text = "Charmander is attempting to run...";
		}else if(currentState == ATTACK){
			Random rand = new Random();
			int damageCal = rand.nextInt(3) + 5;
			enemies.get(selected2).setHealth(-damageCal);
			text = "Charmander attacked! Did " + damageCal + " damage!";
		}
			
	}
	
	private BufferedImage healthBarColor(float percentage){
		if(percentage > 0.75)
			return healthBars.get(0);
		else if(percentage > 0.5)
			return healthBars.get(1);
		else if(percentage > 0.25)
			return healthBars.get(2);
		else
			return healthBars.get(3);
	}
	
	public void keyReleased(int k){
		if(k == 32)
			enemies.get(0).setHealth(-1);
		if(k == 90) {//Key = Z
			if(currentState == OPENING || currentState == OPENING2) currentState++;
			else if(currentState == GUI && selected == 0) currentState = SELECTATTACK;
			else if(currentState == GUI && selected == 3) currentState = RUNNING;
			else if(currentState == RUNNING) player.inBattle = false;
			else if(currentState == SELECTATTACK) currentState = ATTACK;
			else if(currentState == ATTACK) {
				if(enemies.size() != 0)
					currentState = SELECTATTACK;
				else{
					currentState = FINISH;
					text = "Charmander won the battle!";
				}
			}else if(currentState == FINISH) player.inBattle = false;
			action();
		}if(k == 88)
			currentState--;
		if((k == 40 || k == 83) && currentState == GUI){
			if(selected < (options.length - 1))
				selected++;
		}
		if((k == 38 || k == 87) && currentState == GUI){
			if(selected > 0)
				selected--;
		}if((k == 37 || k == 65) && currentState == SELECTATTACK){
			if(selected2 < (enemies.size() - 1))
				selected2++;
		}if((k == 39 || k == 68) && currentState == SELECTATTACK){
			if(selected2 > 0)
				selected2--;
		}
	}
}
