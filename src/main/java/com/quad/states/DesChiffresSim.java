package com.quad.states;

import java.util.ArrayList;

import com.quad.core.GameContainer;
import com.quad.core.Input;
import com.quad.core.Renderer;
import com.quad.core.components.State;
import com.quad.core.fx.Image;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import fr.brisse.deschiffres.NumberList;

public class DesChiffresSim extends State {

    private int windowMouseX = 1535;
    private int windowMouseY = 863;
    private int length;
    private ArrayList<Integer> generatedList;
    private ArrayList<Boolean> cardStatus = new ArrayList<Boolean>();
    private int cardWidth = 100;
    private int gap;
    private int XOffset;

    private String calcul = "";
    private String lastCalulation = "";
    private int cardUsed = 0;
    private int opSelected = 0;

    private int bestScore = 0;

    private int goalnumber;
    private int result = 0;

    private float maxtimer;
    private float timer = 0;

    private Image bgImage;
    private Image maskcardImage;
    private Image maskcardGoalImage;
    private Image maskcardSelectedImage;
    private Image maskPlayerImage;
    private Image resetImage;

    private float pauseInputTimer = 0;

    private ArrayList<Image> goalImages = new ArrayList<Image>();

    private int[] azertyMinKeyboardListCode = { 97, 122, 101, 114, 116, 121, 117, 105, 111, 112, 113, 115, 100, 102,
            103,
            104, 106, 107, 108, 109, 119, 120, 99, 118, 98, 110 };

    private int[] azertyMajKeyboardListCode = { 65, 90, 69, 82, 84, 89, 85, 73, 79, 80, 81, 83, 68, 70, 71, 72, 74, 75,
            76,
            77, 87, 88, 67, 86, 66, 78 };

    @Override
    public void init(GameContainer gc) {
        bgImage = new Image("/cinematics/one/0250.png");
        // Initiate state
        NumberList numberList = new NumberList();
        length = gc.getGame().cache.NumberCardChiffres;
        maxtimer = gc.getGame().cache.chiffreTimer;
        goalnumber = (int) Math.round(Math.random() * 999) + 1;

        for (int i = 0; i < String.valueOf(goalnumber).length(); i++) {
            goalImages.add(new Image("/images/chiffrescard/" + String.valueOf(goalnumber).charAt(i) + ".png"));
        }

        generatedList = numberList.getGenerateList(length);

        for (int i = 0; i < length; i++) {
            cardStatus.add(true);
        }

        int maxgap = 100;

        gap = (1800 - length * cardWidth) / (length + 1);
        if (gap > maxgap) {
            gap = maxgap;
        }

        XOffset = (1920 - length * (cardWidth + gap) + gap) / 2;

        maskcardImage = new Image("/images/chiffrescard/maskcard.png");
        maskcardSelectedImage = new Image("/images/chiffrescard/maskcardselected.png");
        maskcardGoalImage = new Image("/images/chiffrescard/maskcardgoal.png");
        resetImage = new Image("/images/reset.png");

        maskPlayerImage = new Image("/images/joueur" + String.valueOf(gc.getGame().cache.currentPlayer) + "mask.png");

        gc.loadSound("/sounds/rec1.wav", "jeu_chiffres");
        gc.playSound("jeu_chiffres");
    }

    @Override
    public void update(GameContainer gc, float dt) {
        timer += dt;

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

    public void reset() {
        cardStatus.clear();
        for (int i = 0; i < length; i++) {
            cardStatus.add(true);
        }
        calcul = "";
        lastCalulation = "";
        cardUsed = 0;
        opSelected = 0;
        result = 0;
    }
}