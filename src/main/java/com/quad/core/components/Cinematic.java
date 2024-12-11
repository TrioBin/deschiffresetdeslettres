package com.quad.core.components;

import java.util.Timer;
import java.util.TimerTask;

import com.quad.core.fx.Image;
import com.quad.entity.Animation;

public class Cinematic {
    public boolean loaded = false;
    public Image[] images;
    public Animation animation = new Animation();
    public int imageload = 0;

    public Cinematic(String name, int length) {
        this.images = new Image[length];
        Thread CinematicLoad = new Thread(() -> {
            for (int i = 0; i < length; i++) {
                int index = i;
                Thread ImageLoad = new Thread(() -> {
                    int k = index;
                    String path = "/cinematics/" + name + "/" + String.format("%04d", k + 1) + ".png";
                    images[k] = new Image(path);
                    imageload++;
                });
                ImageLoad.start();
            }

            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                public void run() {
                    if (imageload == 250) {
                        animation.setFrames(images);
                        loaded = true;
                        timer.cancel();
                    }
                }
            };
            timer.scheduleAtFixedRate(task, 0, 100);
        });

        CinematicLoad.start();
    }
}