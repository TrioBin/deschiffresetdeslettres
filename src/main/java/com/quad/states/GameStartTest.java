package com.quad.states;

import com.quad.core.GameContainer;
import com.quad.core.Input;
import com.quad.core.Renderer;
import com.quad.core.components.ImageUtils;
import com.quad.core.components.State;
import com.quad.core.fx.Image;

import fr.crusche.beziermanagement.BezierCurve;

public class GameStartTest extends State {

	private int windowMouseX = 1920;
	private int windowMouseY = 1080;

	@Override
	public void init(GameContainer gc) {
		// Initiate state
		System.out.println("Des Chiffres State Loaded");
	}

	@Override
	public void update(GameContainer gc, float dt) {
		// Update state
		Input input = gc.getInput();
		// if s
		if (input.isKeyPressed(27)) {
			gc.stop();
		}

		gc.getGame().cache.currentPlayer += 1;

		if (gc.getGame().cache.currentPlayer > 2) {
			gc.getGame().cache.currentPlayer = 1;
			gc.getGame().cache.currentRound = 2;
		}

		if (gc.getGame().cache.currentRound == 1) {
			gc.getGame().setState(gc, 3);
		}
	}

	@Override
	public void render(GameContainer gc, Renderer r, float dt) {
		// Render state
		r.drawFillRect(0, 0, 1920, 1080, 0xffff00, null);
	}

	@Override
	public void dipose() {
		// Dispose state
	}

}