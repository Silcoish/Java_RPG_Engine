package com.corey.silcoish.entity.enemy;

public class Dragon extends Enemy {

	public Dragon(int level){
		super(level, 1, 0);
		name = "Dragon";
		baseHealth = 2;
		health = baseHealth + (level * 2);
		maxHealth = health;
		attack = 3;
		defense = 2;
		speed = 1;
	}
}
