package com.quad.core;

import com.quad.core.components.State;
import com.quad.states.CinematicStart;
import com.quad.states.DesChiffresNotSim;
import com.quad.states.DesChiffresSim;
import com.quad.states.DesLettresNotSim;
import com.quad.states.GameStartTest;
import com.quad.states.MenuState;

/**
 * 
 * 
 * @author Dillan Spencer
 * This class handles Game State Changes
 * Updates and renders current state  
 * 
 *
 */

public class AbstractGame{
	
	private State[] states;
	private int currentState = 2;
	
	//Pause
	private boolean paused;
	
	//states
	public static final int NUMSTATES = 6;

	public CacheStorage cache = new CacheStorage();
	
	public AbstractGame(){
		states = new State[NUMSTATES];
		
		/*
		 * Load the first state when the game runs
		 */
		loadState(currentState);
	}
	
	private void loadState(int state) {
		if(state == 0) states[0] = new CinematicStart();
		if(state == 1) states[1] = new MenuState();
		if(state == 2) states[2] = new GameStartTest();
		if(state == 3) states[3] = new DesChiffresNotSim();
		if(state == 4) states[4] = new DesChiffresSim();
		if(state == 5) states[5] = new DesLettresNotSim();
	}
	
	private void unloadState(int state) {
		states[state] = null;
	}

	private void loadCinematic(String name, int length) {
		
	}
	
	public void setState(GameContainer gc,int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
		init(gc);
	}
	
	public void setPaused(GameContainer gc, boolean i){
		paused = i;
		if(paused) init(gc);
	}
	
	public void init(GameContainer gc){
		/*
		 * Initiate the pause sequence
		 */
		if(paused){
			return;
		}
		/*
		 * Update current state
		 */
		if(states[currentState] != null) states[currentState].init(gc);
	}
	
	public void update(GameContainer gc, float dt){
		/*
		 * update paused state
		 */
		if(paused){
			return;
		}
		/*
		 * update current state
		 */
		if(states[currentState] != null) states[currentState].update(gc, dt);
	}
	
	public void render(GameContainer gc, Renderer r, float dt){
		/*
		 * render paused state
		 */
		if(paused){
			return;
		}
		//render black screen
		r.drawFillRect(0, 0, Settings.WIDTH, Settings.HEIGHT, 0x000000);
		/*
		 * render current state
		 */
		if(states[currentState] != null) states[currentState].render(gc, r, dt);
		
	}
	
}
