package com.quad.core.components;

import com.quad.core.GameContainer;
import com.quad.core.Input;

import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.concurrent.Callable;

import org.json.simple.JSONObject;

public class ButtonManager {
    private Input input;
    private JSONObject buttons = new JSONObject();

    public float CoeffWidth;
    public float CoeffHeight;

    public ButtonManager(GameContainer gc) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        this.CoeffWidth = (float) (screenSize.getWidth() / gc.getWidth());
        this.CoeffHeight = (float) (screenSize.getHeight() / gc.getHeight());
        //System.out.println("Width: " + CoeffWidth + ", Height: " + CoeffHeight);
    }

    public void linkInput(Input input) {
        this.input = input;
    }

    public JSONObject testButtons() {
        if (input.isButton(1)) {
            // pour chaque bouton
            for (Object key : buttons.keySet()) {
                JSONObject buttonData = (JSONObject) buttons.get(key);
                int x = (int) buttonData.get("x");
                int y = (int) buttonData.get("y");
                int width = (int) buttonData.get("width");
                int height = (int) buttonData.get("height");
                Button button = (Button) buttonData.get("data");
                if (input.getMouseX() > x * CoeffWidth && input.getMouseX() < (x + width) * CoeffWidth
                        && input.getMouseY() > y * CoeffHeight
                        && input.getMouseY() < (y + height) * CoeffHeight) {
                    return button.click();
                }
            }
        }
        return null;
    }

    public void addButton(String name, Callable onClick, int x, int y, int width, int height) {
        Button button = new Button(name, onClick);
        JSONObject buttonData = new JSONObject();
        buttonData.put("x", x);
        buttonData.put("y", y);
        buttonData.put("width", width);
        buttonData.put("height", height);
        buttonData.put("data", button);
        buttons.put(name, buttonData);
    }

    public void removeButton(String name) {
        if (name.startsWith("*")) {
            for (Object key : buttons.keySet()) {
                if (key.toString().startsWith(name.replace("*", ""))) {
                    buttons.remove(key);
                }
            }
        } else {
            buttons.remove(name);
        }
    }

    public void resetButtons() {
    }
}
