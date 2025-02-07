package com.quad.states;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import org.json.simple.JSONObject;

import com.quad.core.CacheStorage;
import com.quad.core.GameContainer;
import com.quad.core.Input;
import com.quad.core.Renderer;
import com.quad.core.components.ButtonManager;
import com.quad.core.components.State;
import com.quad.core.fx.Font;
import com.quad.core.fx.Image;

import fr.crusche.quadextension.DrawScoreTable;
import fr.triobin.deschiffresetdeslettres.Score;

public class EndState extends State {

	private Image bgImage = new Image("/cinematics/one/0250.png");
	private Image returnImage = new Image("/images/return.png");

	private int player;

	private ButtonManager buttonManager;

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

		buttonManager = new ButtonManager(gc);

		buttonManager.addButton("return", new Callable<JSONObject>() {
            @Override
            public JSONObject call() throws Exception {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type", "return");
                return jsonObject;
            }
        }, 1920 - 150 - 100, 1080 - 50 - 100, 150, 50);
	}

	@Override
	public void update(GameContainer gc, float dt) {
		// Update state
		Input input = gc.getInput();
		// if s
		if (input.isKeyPressed(27)) {
			gc.stop();
		}

		buttonManager.linkInput(input);

		JSONObject buttonData = buttonManager.testButtons();

        if (buttonData != null) {
			if (buttonData.get("type").equals("return")) {
				CacheStorage cache = gc.getGame().cache;
				cache.isGameWithBot = false;
				cache.currentRound = 1;
				cache.botData = new JSONObject();
				cache.currentPlayer = 0;
				cache.bestPlayerInTheRoundId = new int[0];
				cache.bestPlayerInTheRoundValue = -1;
				cache.nextState = 3;
				cache.scores = new ArrayList<Score>();
				gc.getGame().setState(gc, 1);
			}
		}
	}

	@Override
	public void render(GameContainer gc, Renderer r, float dt) {
		// Render state
		r.drawImage(bgImage, 0, 0, 1920, 1080);

		Font font = new Font("Verdana", "bold", 100);
		r.setFont(font);
		int gap = 20;

		if (gc.getGame().cache.isGameWithBot) {
			if (player == 1) {
				int rectangleWidth = Math.max(font.getWidthOfString("Victoire du"),
						font.getWidthOfString("Joueur"))
						+ gap * 2;
				r.drawFillRect((1920 - rectangleWidth) / 2, 0, rectangleWidth, 200 + gap * 2, 0x000000);
				r.drawString("Victoire du", 0xffffff, (1920 - font.getWidthOfString("Victoire de")) / 2, 0 + gap);
				r.drawString("Joueur", 0xffffff, (1920 - font.getWidthOfString("Joueur")) / 2,
						100 + gap);
			} else if (player == 2) {
				int rectangleWidth = Math.max(font.getWidthOfString("Victoire du"),
						font.getWidthOfString("Bot"))
						+ gap * 2;
				r.drawFillRect((1920 - rectangleWidth) / 2, 0, rectangleWidth, 200 + gap * 2, 0x000000);
				r.drawString("Victoire du", 0xffffff, (1920 - font.getWidthOfString("Victoire de")) / 2, 0 + gap);
				r.drawString("Bot", 0xffffff, (1920 - font.getWidthOfString("Bot")) / 2,
						100 + gap);
			}
		} else {
			int rectangleWidth = Math.max(font.getWidthOfString("Victoire de"),
					font.getWidthOfString("Joueur " + player))
					+ gap * 2;
			r.drawFillRect((1920 - rectangleWidth) / 2, 0, rectangleWidth, 200 + gap * 2, 0x000000);
			r.drawString("Victoire de", 0xffffff, (1920 - font.getWidthOfString("Victoire de")) / 2, 0 + gap);
			r.drawString("Joueur " + player, 0xffffff, (1920 - font.getWidthOfString("Joueur " + player)) / 2,
					100 + gap);
		}

		r.drawFillRect(200, 600, 1920 - 400, 1080 - 800, 0xaaaaaa);

		DrawScoreTable.drawScoreTable(300, 700, 50, gc.getGame().cache, r, gc.getGame().cache.isGameWithBot);

		r.drawImage(returnImage, 1920 - 150 - 100, 1080 - 50 - 100, 150, 50);
	}

	@Override
	public void dipose() {
		// Dispose state
	}

}