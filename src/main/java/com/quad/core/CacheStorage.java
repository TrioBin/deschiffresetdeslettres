package com.quad.core;

import java.util.ArrayList;

import com.quad.core.components.Cinematic;

import fr.triobin.deschiffresetdeslettres.Score;

public class CacheStorage {
    public ArrayList<Cinematic> cinematics = new ArrayList<Cinematic>();

    public int NumberCardChiffres = 6;
    public int playerNumber = 2;
    public float chiffreTimer = 45;
    public boolean isGameSimultaneous = false;

    public int currentPlayer = 0;
    public int currentRound = 1;
    public ArrayList<Score> scores = new ArrayList<Score>();

    public int[] bestPlayerInTheRoundId = {};
    public int bestPlayerInTheRoundValue = -1;

    public CacheStorage() {
    }

    public void addCinematic(String name, int length) {
        cinematics.add(new Cinematic(name, length));
    }
}
