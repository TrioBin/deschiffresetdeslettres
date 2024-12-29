package com.quad.states;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import com.quad.core.GameContainer;
import com.quad.core.Input;
import com.quad.core.Renderer;
import com.quad.core.components.ButtonManager;
import com.quad.core.components.State;
import com.quad.core.fx.Image;

import fr.crusche.quadextension.DraggableImage;
import fr.fmuzaqi.deslettres.LetterList;

import org.json.simple.JSONObject;

public class DesLettresNotSim extends State {
    private float maxtimer;
    private float timer = 0;

    private Image bgImage;

    private ButtonManager buttonManager;

    private char[] lettres_finales = new char[10];
    private DraggableImage[] images = new DraggableImage[10];

    @Override
    public void init(GameContainer gc) {
        bgImage = new Image("/cinematics/one/0250.png");
        buttonManager = new ButtonManager(gc);

        LetterList.tirages(lettres_finales);

        for (int i = 0; i < lettres_finales.length; i++) {
            images[i] = new DraggableImage(new Image("/images/chiffrescard/" + lettres_finales[i] + ".png"), 100 + i * 150, 100, 100, 150, gc);
        }
    }

    @Override
    public void update(GameContainer gc, float dt) {
        timer += dt;
        
        // Update state
        Input input = gc.getInput();
        
        if (input.isKeyPressed(27)) {
            gc.stop();
        }

        for (int i = 0; i < lettres_finales.length; i++) {
            images[i].update();
        }

        buttonManager.linkInput(input);

        JSONObject buttonData = buttonManager.testButtons();

        if (buttonData != null) {

        }
    }

    @Override
    public void render(GameContainer gc, Renderer r, float dt) {
        // Render state
        r.drawImage(bgImage, 0, 0, 1920, 1080);
        
        for (int i = 0; i < lettres_finales.length; i++) {
            images[i].render(r);
        }
    }

    @Override
    public void dipose() {
        // Dispose state
    }
}