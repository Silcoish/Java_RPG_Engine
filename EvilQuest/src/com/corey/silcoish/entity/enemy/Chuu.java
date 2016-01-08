package com.corey.silcoish.entity.enemy;

public class Chuu extends Enemy{

	public Chuu(int level){
		super(level, 0, 0);
		name = "Chuu";
		baseHealth = 2;
		health = baseHealth + (level * 2);
		maxHealth = health;
		attack = 2;
		defense = 1;
		speed = 3;
	}
}
