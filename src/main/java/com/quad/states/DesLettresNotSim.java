package com.quad.states;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;

import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

import com.quad.core.GameContainer;
import com.quad.core.Input;
import com.quad.core.Renderer;
import com.quad.core.components.ButtonManager;
import com.quad.core.components.State;
import com.quad.core.fx.Image;

import org.json.simple.JSONObject;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import fr.brisse.deschiffres.NumberList;
import fr.crusche.deschiffres.Compte;
import fr.crusche.deschiffres.Result;
import fr.crusche.deschiffres.Solution;

public class DesLettresNotSim extends State {
    private float maxtimer;
    private float timer = 0;

    private Image bgImage;

    private ButtonManager buttonManager;

    @Override
    public void init(GameContainer gc) {
        bgImage = new Image("/cinematics/one/0250.png");
        buttonManager = new ButtonManager(gc);
    }

    @Override
    public void update(GameContainer gc, float dt) {
        timer += dt;
        
        // Update state
        Input input = gc.getInput();
        // if s
        if (input.isKeyPressed(27)) {
            gc.stop();
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
    }

    @Override
    public void dipose() {
        // Dispose state
    }
}