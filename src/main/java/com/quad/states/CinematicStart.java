package com.quad.states;

import java.io.File;

import com.quad.core.GameContainer;
import com.quad.core.Input;
import com.quad.core.Renderer;
import com.quad.core.components.State;
import com.quad.core.fx.Image;
import com.quad.entity.Animation;

public class CinematicStart extends State {

	int state = 0;
	Animation back = new Animation();
	Image[] images = new Image[250];

	int imageload = 0;

	@Override
	public void init(GameContainer gc) {
		String imagePath = "/cinematics/one/" + String.format("%04d", 0 + 1) + ".png";
		images[0] = new Image(imagePath);
		state = 1;
		System.out.println("Load Cinematic");

		Thread CinematicLoad = new Thread(() -> {
			for (int i = 0; i < images.length; i++) {
				int index = i;
				Thread ImageLoad = new Thread(() -> {
					int k = index;
					String path = "/cinematics/one/" + String.format("%04d", k + 1) + ".png";
					images[k] = new Image(path);
					imageload++;
				});
				ImageLoad.start();
			}

			while (true) {
				System.out.println(imageload);
				if (imageload == 250) {
					back.setFrames(images);
					state = 2;
					System.out.println("Cinematic Loaded");
					break;
				}
			}
		});

		CinematicLoad.start();
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
		// Draw the loading image
		if (state == 1) {
			r.drawImage(images[0], 0, 0, 1920, 1080);
			r.drawFillRect(0, 0, imageload * 1920 / 250, 5, 0xff000000);
		} else if (state == 2) {
			back.update();
			r.drawImage(back.getImage(), 0, 0, 1920, 1080);
			if (back.getFrame() == 249) {
				gc.getGame().setState(gc, 1);
			}
		}
	}

	@Override
	public void dipose() {
		// Dispose state
	}
}