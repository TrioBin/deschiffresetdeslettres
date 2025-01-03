package com.quad.core;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.quad.core.components.Cinematic;

import fr.triobin.deschiffresetdeslettres.Score;

public class CacheStorage {
    public ArrayList<Cinematic> cinematics = new ArrayList<Cinematic>();

    public int NumberCardChiffres = 6;
    public int playerNumber = 2;
    public float chiffreTimer = 45;
    public float lettreTimer = 30;
    public boolean isGameSimultaneous = false;

    public boolean isGameWithBot = true;
    public float botDifficulty = 0.7f;
    public JSONObject botData = new JSONObject();

    public int currentPlayer = 0;
    public int currentRound = 1;
    public ArrayList<Score> scores = new ArrayList<Score>();

    public int[] bestPlayerInTheRoundId = {};
    public int bestPlayerInTheRoundValue = -1;

    public int[] roundList = { 1, 2, 1, 2, 1, 2, 1, 2 };
    //public int[] roundList = { 1, 2 };

    public int nextState = 3;

    public CacheStorage() {
    }

    public void addCinematic(String name, int length) {
        cinematics.add(new Cinematic(name, length));
    }
}
