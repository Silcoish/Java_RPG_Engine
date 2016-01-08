package com.corey.silcoish.gamestate;

import java.awt.Graphics;
import java.util.ArrayList;

public class GameStateManager {

	public ArrayList<GameState> gameStates;
	private int currentState;
	
	
	public static final int LOADING = 0;
	public static final int MENU = 1;
	public static final int GAME = 2;
	
	public GameStateManager(){
		currentState = LOADING;
		
		gameStates = new ArrayList<GameState>();
		gameStates.add(new LoadingGameState(this));
		gameStates.add(new MainMenuState(this));
		gameStates.add(new MainGameState(this));
		//ADD THE GAME STATE FROM LOADING GAME STATE
		
		setState(currentState);
	}
	
	public void setState(int state){
		currentState = state;
		gameStates.get(currentState).init();
	}
	
	public void update(){
		gameStates.get(currentState).update();
	}
	
	public void render(Graphics g){
		gameStates.get(currentState).render(g);
	}
	
	public void keyPressed(int k){
		gameStates.get(currentState).keyPressed(k);
	}
	
	public void keyReleased(int k){
		gameStates.get(currentState).keyReleased(k);
	}
	
}
