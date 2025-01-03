package com.quad.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Callable;

import com.quad.core.GameContainer;
import com.quad.core.Input;
import com.quad.core.Renderer;
import com.quad.core.components.ButtonManager;
import com.quad.core.components.State;
import com.quad.core.fx.Font;
import com.quad.core.fx.Image;

import fr.crusche.quadextension.DraggableImage;
import fr.crusche.quadextension.DrawScoreTable;
import fr.fmuzaqi.deslettres.LetterList;

import org.json.simple.JSONObject;

public class DesLettresNotSim extends State {
    private float maxtimer;
    private float timer = 0;

    private Image bgImage;
    private Image bin;
    private Image binMask;
    private int binHeight = 300;

    private ButtonManager buttonManager;

    private char[] lettres_finales = new char[10];
    private DraggableImage[] images = new DraggableImage[10];

    @Override
    public void init(GameContainer gc) {
        bgImage = new Image("/cinematics/one/0250.png");
        buttonManager = new ButtonManager(gc);

        LetterList.tirages(lettres_finales);

        for (int i = 0; i < lettres_finales.length; i++) {
            images[i] = new DraggableImage(new Image("/images/chiffrescard/" + lettres_finales[i] + ".png"), lettres_finales[i] + "",
                    100 + i * 150, 100, 100, 150, gc);
        }

        bin = new Image("/images/corbeille.png");
        binMask = new Image("/images/corbeillemask.png");

        maxtimer = 30;
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
            if (images[i].update()) {
                break;
            }
        }

        buttonManager.linkInput(input);

        JSONObject buttonData = buttonManager.testButtons();

        if (buttonData != null) {

        }

        if (timer >= maxtimer) {
            String tirageWord = new String(lettres_finales).toLowerCase();
            gc.getGame().cache.botData.put("tirageLettres", tirageWord);
            gc.getGame().setState(gc, 2);
            ArrayList<DraggableImage> activeImage = new ArrayList<DraggableImage>();
            for (int i = 0; i < images.length; i++) {
                if (images[i].x > 0 && images[i].x < 1920 && images[i].y > 0
                        && images[i].y < 1080 - binHeight) {
                    activeImage.add(images[i]);
                }
            }
            Collections.sort(activeImage);
            String word = "";
            for (int i = 0; i < activeImage.size(); i++) {
                word += activeImage.get(i).data;
            }
            if (LetterList.validité(lettres_finales, word)) {
                gc.getGame().cache.scores.get(gc.getGame().cache.currentPlayer - 1).addScore(word.length());
            }
        }
    }

    @Override
    public void render(GameContainer gc, Renderer r, float dt) {
        // Render state
        r.setFont(new Font("Verdana", "normal", 50));

        r.drawImage(bgImage, 0, 0, 1920, 1080);

        r.drawFillRect(0, 1080 - binHeight, 1920, binHeight, 0x000000);

        r.drawString("Corbeille", 0xffffff, 50, 1080 - binHeight + 50);

        for (int i = 0; i < lettres_finales.length; i++) {
            images[i].render(r);
        }

        r.drawFillRect(0, 0, Math.round(1920 * timer / maxtimer), 50, 0x000fff);

        
        DrawScoreTable.drawScoreTable(50, 50, 50, gc.getGame().cache, r, gc.getGame().cache.isGameWithBot);
    }

    @Override
    public void dipose() {
        // Dispose state
    }
}