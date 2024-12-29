package com.quad.core.components;

import java.util.concurrent.Callable;
import java.util.function.Function;

import org.json.simple.JSONObject;

public class Button {

    private Callable onClick;

    public Button(String name, Callable onClick) {
        this.onClick = onClick;
    }

    public JSONObject click() {
        try {
            return (JSONObject) onClick.call();
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject(); // Return an empty JSONObject in case of an exception
        }
    }
}
