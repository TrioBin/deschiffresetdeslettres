package com.quad.states;

import java.util.ArrayList;

import com.quad.core.GameContainer;
import com.quad.core.Input;
import com.quad.core.Renderer;
import com.quad.core.components.State;
import com.quad.core.fx.Font;
import com.quad.core.fx.Image;

import fr.crusche.quadextension.DrawScoreTable;
import fr.triobin.deschiffresetdeslettres.Score;

public class EndState extends State {

	private Image bgImage = new Image("/cinematics/one/0250.png");
	private int player;

	@Override
	public void init(GameContainer gc) {
		// Initiate state
		ArrayList<Score> scores = gc.getGame().cache.scores;
		// get best player
		int max = 0;
		for (int i = 0; i < scores.size(); i++) {
			if (scores.get(i).getScore() > max) {
				max = scores.get(i).getScore();
				player = i + 1;
			}
		}
	}

	@Override
	public void update(GameContainer gc, float dt) {
		// Update state
		Input input = gc.getInput();
		// if s
		if (input.isKeyPressed(27)) {
			gc.stop();
		}
	}

	@Override
	public void render(GameContainer gc, Renderer r, float dt) {
		// Render state
		r.drawImage(bgImage, 0, 0, 1920, 1080);

		Font font = new Font("Verdana", "bold", 100);
		r.setFont(font);

		int gap = 20;
		int rectangleWidth = Math.max(font.getWidthOfString("Victoire de"), font.getWidthOfString("Joueur " + player))
				+ gap * 2;

		r.drawFillRect((1920 - rectangleWidth) / 2, 0, rectangleWidth, 200 + gap * 2, 0x000000);
		r.drawString("Victoire de", 0xffffff, (1920 - font.getWidthOfString("Victoire de")) / 2, 0 + gap);
		r.drawString("Joueur " + player, 0xffffff, (1920 - font.getWidthOfString("Joueur " + player)) / 2, 100 + gap);

		//r.drawFillRect(0, 200, 1920, 880, 0x000000);

		DrawScoreTable.drawScoreTable(300, 700, 50, gc.getGame().cache, r, gc.getGame().cache.isGameWithBot);
	}

	@Override
	public void dipose() {
		// Dispose state
	}

}