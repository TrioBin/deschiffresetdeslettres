package com.quad.states;

import java.util.concurrent.Callable;

import org.json.simple.JSONObject;

import com.quad.core.GameContainer;
import com.quad.core.Input;
import com.quad.core.Renderer;
import com.quad.core.components.ButtonManager;
import com.quad.core.components.ImageUtils;
import com.quad.core.components.State;
import com.quad.core.fx.Image;

import fr.crusche.beziermanagement.BezierCurve;

public class MenuState extends State {

	private Image image;
	private Image[] menus = new Image[4];
	private float fadeFactor = 0f;

	ButtonManager buttonManager;

	@Override
	public void init(GameContainer gc) {
		// Initiate state
		image = new Image("/cinematics/one/0250.png");
		menus[0] = new Image("/images/Menu1.png");
		menus[1] = new Image("/images/Menu2.png");
		menus[2] = new Image("/images/Menu3.png");
		menus[3] = new Image("/images/MenuAlpha.png");

		buttonManager = new ButtonManager(gc);

		buttonManager.addButton("HvsH", new Callable<JSONObject>() {
            @Override
            public JSONObject call() throws Exception {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type", "HvsH");
                return jsonObject;
            }
        }, 300, 700, 300, 200);

		buttonManager.addButton("HvsAI", new Callable<JSONObject>() {
			@Override
			public JSONObject call() throws Exception {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("type", "HvsAI");
				return jsonObject;
			}
		}, 1320, 700, 300, 200);
	}

	@Override
	public void update(GameContainer gc, float dt) {
		// Update state
		Input input = gc.getInput();
		// if s
		if (input.isKeyPressed(27)) {
			gc.stop();
		}

		// if (input.isButton(1)) {
		// 	System.out.print(input.mouseX + " " + input.mouseY);
		// }

		buttonManager.linkInput(input);

        JSONObject buttonData = buttonManager.testButtons();

		if (buttonData != null) {
			if (buttonData.get("type").equals("HvsH")) {
				gc.getGame().cache.isGameWithBot = false;
				gc.getGame().setState(gc, 2);
			} else if (buttonData.get("type").equals("HvsAI")) {
				gc.getGame().cache.isGameWithBot = true;
				gc.getGame().setState(gc, 2);
			}
		}
	}

	@Override
	public void render(GameContainer gc, Renderer r, float dt) {
		// Render state
		r.drawFillRect(0, 0, 100, 100, 0xffff00, null);

		if (image != null) {
			r.drawImage(image, 0, 0);
			r.drawImage(image, 0, 0, 1920, 1080);
		} else {
			System.out.println("Image not loaded");
		}

		if (fadeFactor <= 10) {
			fadeFactor += 0.1f;
		}

		r.drawTransparentImage(menus[0], ImageUtils.lightenImage(menus[3], fadeFactor), 300, 700, 300,
				200);
		r.drawTransparentImage(menus[1], ImageUtils.lightenImage(menus[3], fadeFactor), 1320, 700, 300,
				200);
	}

	@Override
	public void dipose() {
		// Dispose state
	}

}