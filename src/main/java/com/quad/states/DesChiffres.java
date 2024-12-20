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

public class DesChiffres extends State {

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
        System.out.println("Des Chiffres State Loaded");
        NumberList numberList = new NumberList();
        length = gc.getGame().cache.NumberCardChiffres;
        maxtimer = gc.getGame().cache.chiffreTimer;
        goalnumber = (int) Math.round(Math.random() * 999) + 1;
        System.out.println("Goal Number: " + goalnumber);

        for (int i = 0; i < String.valueOf(goalnumber).length(); i++) {
            goalImages.add(new Image("/images/chiffrescard/" + String.valueOf(goalnumber).charAt(i) + ".png"));
        }

        generatedList = numberList.getGenerateList(length);

        for (int i = 0; i < length; i++) {
            cardStatus.add(true);
        }

        System.out.println("Generated List: " + generatedList);

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

        if (result == goalnumber) {
            gc.getGame().setState(gc, 2);
        }

        if (timer >= maxtimer) {
            gc.getGame().setState(gc, 2);
        }

        if (pauseInputTimer > 0) {
            pauseInputTimer -= dt;
            return;
        }
        // Update state
        Input input = gc.getInput();
        // if s
        if (input.isKeyPressed(27)) {
            gc.stop();
        }

        if (input.isKeyPressed(32)) {
            reset();
        }

        if (opSelected != 0 || cardUsed == 0) {
            for (int i = 0; i < length; i++) {
                if (input.isKeyPressed(azertyMinKeyboardListCode[i])
                        || input.isKeyPressed(azertyMajKeyboardListCode[i])) {
                    cardStatus.set(i, false);
                    if (cardUsed != 0) {
                        calcul = lastCalulation.replace("b", "(" + calcul + ")").replace("c",
                                "(" + generatedList.get(i).toString() + ")");
                    } else {
                        calcul += generatedList.get(i);
                    }
                    cardUsed++;
                    opSelected = 0;
                    System.out.println("Calcul: " + calcul);
                    calculationOfResult();
                }
            }
        }

        if (input.isKeyPressed(38)
                || input.isKeyPressed(49)) {
            lastCalulation = "b+c";
            opSelected = 1;
        } else if (input.isKeyPressed(233)
                || input.isKeyPressed(50)) {
            lastCalulation = "b-c";
            opSelected = 2;
        } else if (input.isKeyPressed(34)
                || input.isKeyPressed(51)) {
            lastCalulation = "c*b";
            opSelected = 3;
        } else if (input.isKeyPressed(39)
                || input.isKeyPressed(52)) {
            lastCalulation = "Math.floor(b/c)";
            opSelected = 4;
        }

        if (input.isButton(1))

        {
            if (opSelected != 0 || cardUsed == 0) {
                for (int i = 0; i < length; i++) {
                    if (input.mouseX > (XOffset + (i * (cardWidth + gap))) * windowMouseX / 1920
                            && input.mouseX < ((XOffset + (i * (cardWidth + gap))) + cardWidth) * windowMouseX / 1920
                            && input.mouseY > 900 * windowMouseY / 1080
                            && input.mouseY < 1050 * windowMouseY / 1080 && cardStatus.get(i)) {
                        cardStatus.set(i, false);
                        if (cardUsed != 0) {
                            calcul = lastCalulation.replace("b", "(" + calcul + ")").replace("c",
                                    "(" + generatedList.get(i).toString() + ")");
                        } else {
                            calcul += generatedList.get(i);
                        }
                        cardUsed++;
                        opSelected = 0;
                        calculationOfResult();
                    }
                }
            }

            if (input.mouseX > 610 * windowMouseX / 1920 && input.mouseX < 710 * windowMouseX / 1920
                    && input.mouseY > 700 * windowMouseY / 1080 && input.mouseY < 850 * windowMouseY / 1080) {
                lastCalulation = "b+c";
                opSelected = 1;
            } else if (input.mouseX > 810 * windowMouseX / 1920 && input.mouseX < 910 * windowMouseX / 1920
                    && input.mouseY > 700 * windowMouseY / 1080 && input.mouseY < 850 * windowMouseY / 1080) {
                lastCalulation = "b-c";
                opSelected = 2;
            } else if (input.mouseX > 1010 * windowMouseX / 1920 && input.mouseX < 1110 * windowMouseX / 1920
                    && input.mouseY > 700 * windowMouseY / 1080 && input.mouseY < 850 * windowMouseY / 1080) {
                lastCalulation = "c*b";
                opSelected = 3;
            } else if (input.mouseX > 1210 * windowMouseX / 1920 && input.mouseX < 1310 * windowMouseX / 1920
                    && input.mouseY > 700 * windowMouseY / 1080 && input.mouseY < 850 * windowMouseY / 1080) {
                lastCalulation = "Math.floor(b/c)";
                opSelected = 4;
            } else if (input.mouseX > 1720 * windowMouseX / 1920 && input.mouseX < 1870 * windowMouseX / 1920
                    && input.mouseY > 50 * windowMouseY / 1080 && input.mouseY < 100 * windowMouseY / 1080) {
                reset();
            }
        }

        if (cardUsed == length) {
            if (bestScore == 0 || Math.abs(goalnumber - result) < bestScore) {
                bestScore = Math.abs(goalnumber - result);
            }
            reset();
            pauseInputTimer = 0.5f;
        }
    }

    @Override
    public void render(GameContainer gc, Renderer r, float dt) {
        // Render state
        r.drawImage(bgImage, 0, 0, 1920, 1080);

        for (int index = 0; index < length; index++) {
            if (cardStatus.get(index)) {
                r.drawTransparentImage(
                        new Image("/images/chiffrescard/" + String.valueOf(generatedList.get(index)) + ".png"),
                        maskcardImage,
                        XOffset + index * (cardWidth + gap), 900, cardWidth, 150);
            } else {
                r.drawTransparentImage(
                        new Image("/images/chiffrescard/" + String.valueOf(generatedList.get(index)) + ".png"),
                        maskcardSelectedImage,
                        XOffset + index * (cardWidth + gap), 900, cardWidth, 150);

            }
        }

        for (int i = 0; i < String.valueOf(goalnumber).length(); i++) {
            r.drawTransparentImage(goalImages.get(i), maskcardGoalImage, 100 + i * 120, 100, 100, 150);
        }

        if (result != 0) {
            for (int i = 0; i < String.valueOf(result).length(); i++) {
                r.drawTransparentImage(new Image("/images/chiffrescard/" + String.valueOf(result).charAt(i) + ".png"),
                        maskcardGoalImage, 100 + i * 120, 300, 100, 150);
            }
        }

        if (bestScore != 0) {
            for (int i = 0; i < String.valueOf(bestScore).length(); i++) {
                r.drawTransparentImage(
                        new Image("/images/chiffrescard/" + String.valueOf(bestScore).charAt(i) + ".png"),
                        maskcardGoalImage, 100 + i * 120, 500, 100, 150);
            }
        }

        int[] padding = { 0, 0, 0, 0 };
        if (opSelected != 0) {
            padding[opSelected - 1] = 20;
        }

        r.drawTransparentImage(new Image("/images/chiffrescard/plus.png"), maskcardImage, 610, 700 - padding[0], 100,
                150);
        r.drawTransparentImage(new Image("/images/chiffrescard/moins.png"), maskcardImage, 810, 700 - padding[1], 100,
                150);
        r.drawTransparentImage(new Image("/images/chiffrescard/mult.png"), maskcardImage, 1010, 700 - padding[2], 100,
                150);
        r.drawTransparentImage(new Image("/images/chiffrescard/div.png"), maskcardImage, 1210, 700 - padding[3], 100,
                150);

        r.drawFillRect(0, 0, Math.round(1920 * timer / maxtimer), 50, 0x000fff);

        r.drawTransparentImage(maskPlayerImage, maskPlayerImage, 0, 0, 150, 50);

        r.drawImage(resetImage, 1720, 50, 150, 50);
    }

    @Override
    public void dipose() {
        // Dispose state
    }

    public void calculationOfResult() {
        Context cx = Context.enter();
        try {
            Scriptable scope = cx.initStandardObjects();

            Object jsOut = Context.javaToJS(System.out, scope);
            ScriptableObject.putProperty(scope, "out", jsOut);

            result = Integer.parseInt(Context.toString(cx.evaluateString(scope, calcul, "<cmd>", 1, null)));
        } finally {
            Context.exit();
        }

        System.out.println("Result: " + result);
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