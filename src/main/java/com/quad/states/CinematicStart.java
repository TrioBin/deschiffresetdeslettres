package com.quad.states;

import java.io.File;

import com.quad.core.CacheStorage;
import com.quad.core.GameContainer;
import com.quad.core.Input;
import com.quad.core.Renderer;
import com.quad.core.components.Cinematic;
import com.quad.core.components.State;
import com.quad.core.fx.Image;
import com.quad.entity.Animation;

public class CinematicStart extends State {

	int state = 0;
	Image loadImage;

	CacheStorage cache;
	Cinematic cinematic;

	@Override
	public void init(GameContainer gc) {
		cache = gc.getGame().cache;

		String imagePath = "/cinematics/one/0001.png";
		loadImage = new Image(imagePath);
		state = 1;
		System.out.println("Load Cinematic");

		cache.addCinematic("one", 250);

		cinematic = cache.cinematics.get(0);

	}

	@Override
	public void update(GameContainer gc, float dt) {
		// Update state
		Input input = gc.getInput();
		// if s
		if (input.isKeyPressed(27)) {
			gc.stop();
		}

		if (cinematic.loaded) {
			state = 2;
		}
	}

	@Override
	public void render(GameContainer gc, Renderer r, float dt) {
		// Draw the loading image
		if (state == 1) {
			r.drawImage(loadImage, 0, 0, 1920, 1080);
			r.drawFillRect(0, 0, cinematic.imageload * 1920 / 250, 5, 0xff000000);
		} else if (state == 2) {
			cinematic.animation.update();
			r.drawImage(cinematic.animation.getImage(), 0, 0, 1920, 1080);
			if (cinematic.animation.getFrame() == 249) {
				gc.getGame().setState(gc, 1);
			}
		}
	}

	@Override
	public void dipose() {
		// Dispose state
	}
}