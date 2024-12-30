package fr.crusche.quadextension;

import com.quad.core.GameContainer;
import com.quad.core.Renderer;
import com.quad.core.fx.Image;

import java.awt.Dimension;
import java.awt.Toolkit;

public class DraggableImage implements Comparable<DraggableImage> {
    Image image;
    public int x;
    public int y;
    public String data;
    int width;
    int height;
    boolean isDragging = false;
    GameContainer gc;
    Renderer r;

    int startdragx, startdragy;

    public float CoeffWidth;
    public float CoeffHeight;

    public DraggableImage(Image image, String data, int x, int y, int width, int height, GameContainer gc) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.gc = gc;
        this.data = data;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.CoeffWidth = (float) (screenSize.getWidth() / gc.getWidth());
        this.CoeffHeight = (float) (screenSize.getHeight() / gc.getHeight());
    }

    public void setRenderer(Renderer r) {
        this.r = r;
    }

    public void testIfOnImage() {
        if (gc.getInput().isButton(1)) {
            if (gc.getInput().getMouseX() > x * CoeffWidth && gc.getInput().getMouseX() < (x + width) * CoeffWidth
                    && gc.getInput().getMouseY() > y * CoeffHeight
                    && gc.getInput().getMouseY() < (y + height) * CoeffHeight) {
                isDragging = true;
                startdragx = (int) (gc.getInput().getMouseX() / CoeffWidth) - x;
                startdragy = (int) (gc.getInput().getMouseY() / CoeffHeight) - y;
            }
        } else {
            isDragging = false;
        }
    }

    public boolean update() {
        testIfOnImage();
        if (isDragging) {
            x = (int) (gc.getInput().getMouseX() / CoeffWidth) - startdragx;
            y = (int) (gc.getInput().getMouseY() / CoeffHeight) - startdragy;
            return true;
        } else {
            return false;
        }
    }

    public void render() {
        this.r.drawImage(image, x, y, width, height);
    }

    public void render(Renderer r) {
        r.drawImage(image, x, y, width, height);
    }

    @Override
    public int compareTo(DraggableImage emp) {
    //trions les employés selon leur age dans l'ordre croiddant
    //retroune un entier négative, zéro ou positive si l'age 
    //de cet employé est moins que, égale à ou supérieur à l'objet comparé avec
    return (this.x - emp.x);
    }
}
