package fr.triobin.deschiffresetdeslettres;

import com.quad.core.AbstractGame;
import com.quad.core.GameContainer;
import com.quad.core.fx.SoundClip;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        GameContainer gc = new GameContainer(new AbstractGame());
        gc.setWidth(1920);
        gc.setHeight(1080);
        gc.setScale(1);
        gc.setTitle("Des chiffres et des lettres");
        gc.setClearScreen(true);
        gc.setDebug(false);
        gc.setFrameCap(30);
        gc.start();
        //SoundClip.toggleMute();
    }
}