package com.quad.states;

import java.util.ArrayList;

import com.quad.core.GameContainer;
import com.quad.core.Input;
import com.quad.core.Renderer;
import com.quad.core.components.ImageUtils;
import com.quad.core.components.State;
import com.quad.core.fx.Image;

import fr.crusche.beziermanagement.BezierCurve;
import fr.triobin.deschiffresetdeslettres.Score;

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

		if (gc.getGame().cache.currentRound == 1 && gc.getGame().cache.currentPlayer == 0) {
			gc.getGame().cache.scores = new ArrayList<Score>();
			for (int i = 0; i < gc.getGame().cache.playerNumber; i++) {
				gc.getGame().cache.scores.add(new Score());
			}
		}

		if (gc.getGame().cache.isGameSimultaneous) {
			if (gc.getGame().cache.currentRound == 1) {
				gc.getGame().cache.nextState = 4;
			}
		} else {
			gc.getGame().cache.currentPlayer += 1;

			if (gc.getGame().cache.currentPlayer > gc.getGame().cache.playerNumber) {
				gc.getGame().cache.currentPlayer = 1;
				gc.getGame().cache.currentRound += 1;
			}

			if (gc.getGame().cache.currentRound > gc.getGame().cache.roundList.length) {
				gc.getGame().cache.nextState = 7;
			} else if (gc.getGame().cache.roundList[gc.getGame().cache.currentRound - 1] == 1) {
				gc.getGame().cache.nextState = 3;
			} else if (gc.getGame().cache.roundList[gc.getGame().cache.currentRound - 1] == 2) {
				for (int i = 0; i < gc.getGame().cache.bestPlayerInTheRoundId.length; i++) {
					if (gc.getGame().cache.bestPlayerInTheRoundValue == 0) {
						gc.getGame().cache.scores.get(gc.getGame().cache.bestPlayerInTheRoundId[i] - 1)
								.addScore(10);
					} else {
						gc.getGame().cache.scores.get(gc.getGame().cache.bestPlayerInTheRoundId[i] - 1)
								.addScore(7);
					}
				}
				gc.getGame().cache.nextState = 5;
			}
		}
		
		gc.getGame().setState(gc, 8);
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