package com.quad.core.components;

import java.util.concurrent.Callable;
import java.util.function.Function;

public class Button {

    private Callable onClick;

    public Button(String name, Callable onClick) {
        this.onClick = onClick;
    }

    public void click() {
        try {
            onClick.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
