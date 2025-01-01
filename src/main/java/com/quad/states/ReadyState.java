package com.quad.states;

import com.quad.core.GameContainer;
import com.quad.core.Input;
import com.quad.core.Renderer;
import com.quad.core.components.State;
import com.quad.core.fx.Font;
import com.quad.core.fx.Image;

public class ReadyState extends State {

    float timer = 0;
    String text = "Ready ?";

    Image bgImage = new Image("/cinematics/one/0250.png");

    @Override
    public void init(GameContainer gc) {

    }

    @Override
    public void update(GameContainer gc, float dt) {
        timer += dt;
        
        if (timer >= 3) {
            text = "3";
            if (timer >= 4) {
                text = "2";
                if (timer >= 5) {
                    text = "1";
                    if (timer >= 6) {
                        gc.getGame().setState(gc, gc.getGame().cache.nextState);
                    }
                }
                
            }
        }

        // Update state
        Input input = gc.getInput();
        // if s
        if (input.isKeyPressed(27)) {
            gc.stop();
        }
    }

    @Override
    public void render(GameContainer gc, Renderer r, float dt) {
        r.drawImage(bgImage, 0, 0, gc.getWidth(), gc.getHeight());
        Font font = new Font("Arial", "normal", 100);
        r.setFont(font);
        r.drawString(text, 0xffffff, (gc.getWidth() - font.getWidthOfString(text)) / 2, gc.getHeight() / 2);
    }

    @Override
    public void dipose() {
        // Dispose state
    }
}