package fr.triobin.deschiffresetdeslettres;

public class Score {
    private int score = 0;
    public Score() {

    }

    public void addScore(int score) {
        this.score += score;
    }

    public int getScore() {
        return this.score;
    }
}
