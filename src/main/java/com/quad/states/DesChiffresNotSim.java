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
import fr.crusche.quadextension.DrawScoreTable;

public class DesChiffresNotSim extends State {

    private int length;
    private ArrayList<Integer> generatedList;
    private ArrayList<Integer> currentList;
    private ArrayList<Boolean> cardStatus = new ArrayList<Boolean>();
    private int cardWidth = 100;
    private int gap;
    private int XOffset;

    private int lastNumberCardSelected = -1;
    private int opSelected = 0;

    private int bestScore = 0;

    private int goalnumber;
    private int result = 0;

    private float maxtimer;
    private float timer = 0;

    private Image bgImage;
    private Image maskcardImage;
    private Image maskcardGoalImage;
    private Image resetImage;

    private float pauseInputTimer = 0;
    private float buttonTimeOut = 0;

    private ArrayList<Image> goalImages = new ArrayList<Image>();

    private int[] azertyMinKeyboardListCode = { 97, 122, 101, 114, 116, 121, 117, 105, 111, 112, 113, 115, 100, 102,
            103,
            104, 106, 107, 108, 109, 119, 120, 99, 118, 98, 110 };

    private int[] azertyMajKeyboardListCode = { 65, 90, 69, 82, 84, 89, 85, 73, 79, 80, 81, 83, 68, 70, 71, 72, 74, 75,
            76,
            77, 87, 88, 67, 86, 66, 78 };

    private ButtonManager buttonManager;

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
        currentList = new ArrayList<Integer>(generatedList);

        for (int i = 0; i < length; i++) {
            cardStatus.add(true);
        }

        maskcardImage = new Image("/images/chiffrescard/maskcard.png");
        maskcardGoalImage = new Image("/images/chiffrescard/maskcardgoal.png");
        resetImage = new Image("/images/reset.png");

        gc.loadSound("/sounds/rec1.wav", "jeu_chiffres");
        gc.playSound("jeu_chiffres");

        buttonManager = new ButtonManager(gc);

        buttonManager.addButton("reset", new Callable<JSONObject>() {
            @Override
            public JSONObject call() throws Exception {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type", "Reset");
                return jsonObject;
            }
        }, 1720, 50, 150, 50);

        buttonManager.addButton("annul", new Callable<JSONObject>() {
            @Override
            public JSONObject call() throws Exception {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type", "Annul");
                return jsonObject;
            }
        }, 1720, 150, 150, 50);

        // for operators

        buttonManager.addButton("plus", new Callable<JSONObject>() {
            @Override
            public JSONObject call() throws Exception {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type", "Operator");
                jsonObject.put("operator", 1);
                return jsonObject;
            }
        }, 610, 700, 100, 150);

        buttonManager.addButton("moins", new Callable<JSONObject>() {
            @Override
            public JSONObject call() throws Exception {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type", "Operator");
                jsonObject.put("operator", 2);
                return jsonObject;
            }
        }, 810, 700, 100, 150);

        buttonManager.addButton("mult", new Callable<JSONObject>() {
            @Override
            public JSONObject call() throws Exception {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type", "Operator");
                jsonObject.put("operator", 3);
                return jsonObject;
            }
        }, 1010, 700, 100, 150);

        buttonManager.addButton("div", new Callable<JSONObject>() {
            @Override
            public JSONObject call() throws Exception {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type", "Operator");
                jsonObject.put("operator", 4);
                return jsonObject;
            }
        }, 1210, 700, 100, 150);
    }

    @Override
    public void update(GameContainer gc, float dt) {
        timer += dt;
        buttonTimeOut -= dt;
        if (buttonTimeOut < 0) {
            buttonTimeOut = 0;
        }

        if (result == goalnumber) {
            if (gc.getGame().cache.bestPlayerInTheRoundValue == 0) {
                int[] tempArray = Arrays.copyOf(gc.getGame().cache.bestPlayerInTheRoundId,
                        gc.getGame().cache.bestPlayerInTheRoundId.length + 1);
                tempArray[tempArray.length - 1] = gc.getGame().cache.currentPlayer;
                gc.getGame().cache.bestPlayerInTheRoundId = tempArray;
            } else {
                gc.getGame().cache.bestPlayerInTheRoundId = new int[] { gc.getGame().cache.currentPlayer };
            }
            gc.getGame().cache.bestPlayerInTheRoundValue = 0;
            gc.getGame().setState(gc, 2);
        }

        if (timer >= maxtimer) {
            gc.getGame().cache.botData.put("goalnumber", goalnumber);
            gc.getGame().cache.botData.put("generatedList", generatedList);

            Compte cpt = new Compte(convertToIntArray(generatedList));

            // Liste de choix de plaques
            int[] plaques = cpt.GetPlaques();

            // Nombre à atteindre
            int tirage = goalnumber;

            // Initialize the recursive calculation root structure
            Solution solution = new Solution();
            solution.tirage = tirage;
            solution.depth = plaques.length;
            Result res = new Result();
            res.steps = plaques;
            res.text = "";
            res.value = 0;
            Arrays.sort(res.steps);
            solution.current.add(res);

            // Initialize the best approaching structure
            solution.best = new Result();
            solution.best.steps = res.steps;
            solution.best.value = solution.best.steps[solution.best.steps.length - 1];
            solution.best.text = String.format("%d", solution.best.value);

            // Start the recursive resolution
            solution = cpt.SolveTirage(solution);

            if (solution.best.value == goalnumber) {
                if (gc.getGame().cache.bestPlayerInTheRoundValue == 0) {
                    int[] tempArray = Arrays.copyOf(gc.getGame().cache.bestPlayerInTheRoundId,
                            gc.getGame().cache.bestPlayerInTheRoundId.length + 1);
                    tempArray[tempArray.length - 1] = gc.getGame().cache.currentPlayer;
                    gc.getGame().cache.bestPlayerInTheRoundId = tempArray;
                } else {
                    gc.getGame().cache.bestPlayerInTheRoundId = new int[] { gc.getGame().cache.currentPlayer };
                }
                gc.getGame().cache.bestPlayerInTheRoundValue = 0;
                gc.getGame().setState(gc, 2);
            } else {
                if (Math.abs(solution.best.value - goalnumber) >= gc.getGame().cache.bestPlayerInTheRoundValue) {
                    if (gc.getGame().cache.bestPlayerInTheRoundValue == Math.abs(solution.best.value - goalnumber)) {
                        gc.getGame().cache.bestPlayerInTheRoundId[gc.getGame().cache.bestPlayerInTheRoundId.length] = gc
                                .getGame().cache.currentPlayer;
                    } else {
                        gc.getGame().cache.bestPlayerInTheRoundId = new int[] { gc.getGame().cache.currentPlayer };
                    }
                    gc.getGame().cache.bestPlayerInTheRoundValue = Math.abs(solution.best.value - goalnumber);
                }
                gc.getGame().setState(gc, 2);
            }
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

        buttonManager.linkInput(input);

        JSONObject buttonData = buttonManager.testButtons();

        if (buttonData != null && buttonTimeOut == 0) {
            buttonTimeOut = 0.2f;
            if (buttonData.get("type").equals("NumberCard")) {
                if (lastNumberCardSelected != -1 && lastNumberCardSelected != (int) buttonData.get("cardIndex")
                        && opSelected != 0) {
                    int result = 0;

                    switch (opSelected) {
                        case 1:
                            result = currentList.get(lastNumberCardSelected)
                                    + currentList.get((int) buttonData.get("cardIndex"));
                            break;

                        case 2:
                            result = currentList.get(lastNumberCardSelected)
                                    - currentList.get((int) buttonData.get("cardIndex"));
                            break;

                        case 3:
                            result = currentList.get(lastNumberCardSelected)
                                    * currentList.get((int) buttonData.get("cardIndex"));
                            break;

                        case 4:
                            result = (int) (currentList.get(lastNumberCardSelected)
                                    / currentList.get((int) buttonData.get("cardIndex")));
                            break;

                        default:
                            break;
                    }
                    currentList.remove(lastNumberCardSelected);
                    if ((int) buttonData.get("cardIndex") > lastNumberCardSelected) {
                        currentList.remove((int) buttonData.get("cardIndex") - 1);
                    } else {
                        currentList.remove((int) buttonData.get("cardIndex"));
                    }
                    if (currentList.size() == 0) {
                        reset(result);
                    } else {
                        currentList.add(result);
                    }

                    lastNumberCardSelected = -1;
                    opSelected = 0;
                } else {
                    lastNumberCardSelected = (int) buttonData.get("cardIndex");
                }
            } else if (buttonData.get("type").equals("Operator")) {
                opSelected = (int) buttonData.get("operator");
            } else if (buttonData.get("type").equals("Reset")) {
                reset();
            } else if (buttonData.get("type").equals("Annul")) {
                if (lastNumberCardSelected != -1) {
                    lastNumberCardSelected = -1;
                    opSelected = 0;
                }
            }
        }
    }

    @Override
    public void render(GameContainer gc, Renderer r, float dt) {
        // Render state
        r.drawImage(bgImage, 0, 0, 1920, 1080);

        int length = currentList.size();

        int cardNumber = 0;
        for (int i = 0; i < length; i++) {
            cardNumber += String.valueOf(currentList.get(i)).length();
        }
        int cardWidthAddition = cardWidth;
        int largeur = cardWidth * cardNumber;

        int maxgap = 100;

        gap = (1800 - largeur) / (length + 1);
        if (gap > maxgap) {
            gap = maxgap;
        }

        XOffset = (1920 - (largeur + length * gap) + gap) / 2;

        int CurrentX = XOffset;
        int[] padding = new int[length];
        if (lastNumberCardSelected != -1) {
            padding[lastNumberCardSelected] = 20;
        }

        for (int index = 0; index < length; index++) {
            char[] card = String.valueOf(currentList.get(index)).toCharArray();
            for (int i = 0; i < card.length; i++) {
                r.drawImage(new Image("/images/chiffrescard/" + card[i] + ".png"),
                        CurrentX + i * cardWidth, 900 - padding[index], cardWidth, 150);
            }
            final int cardIndex = index;
            buttonManager.addButton("card" + Integer.toString(cardIndex + 1), new Callable<JSONObject>() {
                @Override
                public JSONObject call() throws Exception {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("type", "NumberCard");
                    jsonObject.put("cardIndex", cardIndex);
                    return jsonObject;
                }
            }, CurrentX, 900, cardWidth * card.length + gap, 150);

            CurrentX += cardWidth * card.length + gap;
        }

        for (int i = 0; i < String.valueOf(goalnumber).length(); i++) {
            r.drawTransparentImage(goalImages.get(i), maskcardGoalImage, 100 + i * 120, 100, 100, 150);
        }

        if (bestScore != 0) {
            for (int i = 0; i < String.valueOf(bestScore).length(); i++) {
                r.drawTransparentImage(
                        new Image("/images/chiffrescard/" + String.valueOf(bestScore).charAt(i) + ".png"),
                        maskcardGoalImage, 100 + i * 120, 500, 100, 150);
            }
        }

        int[] OpPadding = { 0, 0, 0, 0 };
        if (opSelected != 0) {
            OpPadding[opSelected - 1] = 20;
        }

        r.drawTransparentImage(new Image("/images/chiffrescard/plus.png"), maskcardImage, 610, 700 - OpPadding[0], 100,
                150);
        r.drawTransparentImage(new Image("/images/chiffrescard/moins.png"), maskcardImage, 810, 700 - OpPadding[1], 100,
                150);
        r.drawTransparentImage(new Image("/images/chiffrescard/mult.png"), maskcardImage, 1010, 700 - OpPadding[2], 100,
                150);
        r.drawTransparentImage(new Image("/images/chiffrescard/div.png"), maskcardImage, 1210, 700 - OpPadding[3], 100,
                150);

        r.drawFillRect(0, 0, Math.round(1920 * timer / maxtimer), 50, 0x000fff);

        r.drawImage(resetImage, 1720, 50, 150, 50);
        r.drawImage(resetImage, 1720, 150, 150, 50);

        DrawScoreTable.drawScoreTable(50, 50, 50, gc.getGame().cache, r);
    }

    @Override
    public void dipose() {
        // Dispose state
    }

    public void reset() {
        opSelected = 0;
        currentList = new ArrayList<Integer>(generatedList);
    }

    public void reset(int result) {
        opSelected = 0;
        if (Math.abs(result - goalnumber) < Math.abs(bestScore - goalnumber)) {
            bestScore = result;
        }
        currentList = new ArrayList<Integer>(generatedList);
    }

    // method to convert ArrayList<Integer> to int[]
    private static int[] convertToIntArray(ArrayList<Integer> list) {
        int[] intArray = new int[list.size()];

        for (int i = 0; i < list.size(); i++) {
            intArray[i] = list.get(i);
        }

        return intArray;
    }
}