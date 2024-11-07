package com.quad.states;

import com.quad.core.GameContainer;
import com.quad.core.Input;
import com.quad.core.Renderer;
import com.quad.core.components.ImageUtils;
import com.quad.core.components.State;
import com.quad.core.fx.Image;

import fr.crusche.beziermanagement.BezierCurve;

public class MenuState extends State {

	private Image image;
	private Image[] menus = new Image[4];
	private float fadeFactor = 0f;

	private int windowMouseX = 1920;
	private int windowMouseY = 1080;

	@Override
	public void init(GameContainer gc) {
		// Initiate state
		image = new Image("/cinematics/one/0250.png");
		menus[0] = new Image("/images/Menu1.png");
		menus[1] = new Image("/images/Menu2.png");
		menus[2] = new Image("/images/Menu3.png");
		menus[3] = new Image("/images/MenuAlpha.png");
		System.out.println("Menu State Loaded");
	}

	@Override
	public void update(GameContainer gc, float dt) {
		// Update state
		Input input = gc.getInput();
		// if s
		if (input.isKeyPressed(27)) {
			gc.stop();
		}

		if (input.isButton(1)) {
			if (input.mouseX > 300 * windowMouseX / 1920 && input.mouseX < 600 * windowMouseX / 1920
					&& input.mouseY > 700 * windowMouseY / 1080
					&& input.mouseY < 900 * windowMouseY / 1080) {
				System.out.println("Menu 1");
			} else if (input.mouseX > 800 * windowMouseX / 1920 && input.mouseX < 1100 * windowMouseX / 1920
					&& input.mouseY > 600 * windowMouseY / 1080
					&& input.mouseY < 800 * windowMouseY / 1080) {
				System.out.println("Menu 2");
			} else if (input.mouseX > 1500 * windowMouseX / 1920 && input.mouseX < 1800 * windowMouseX / 1920
					&& input.mouseY > 700 * windowMouseY / 1080
					&& input.mouseY < 900 * windowMouseY / 1080) {
				System.out.println("Menu 3");
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

		if (fadeFactor <= 1) {
			fadeFactor += 0.01f;
		}

		r.drawTransparentImage(menus[0], ImageUtils.lightenImage(menus[3], fadeFactor), 300, 700, 300,
				200);
		r.drawTransparentImage(menus[1], ImageUtils.lightenImage(menus[3], fadeFactor), 800, 600, 300,
				200);
		r.drawTransparentImage(menus[2], ImageUtils.lightenImage(menus[3], fadeFactor), 1500, 700, 300,
				200);
	}

	@Override
	public void dipose() {
		// Dispose state
	}

}