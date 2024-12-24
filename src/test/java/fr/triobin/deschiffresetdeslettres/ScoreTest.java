package fr.triobin.deschiffresetdeslettres;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ScoreTest {

    private Score score;

    @BeforeEach
    public void setUp() {
        score = new Score();
    }

    @Test
    public void testAddScore() {
        score.addScore(10);
        assertEquals(10, score.getScore());

        score.addScore(5);
        assertEquals(15, score.getScore());

        score.addScore(-3);
        assertEquals(12, score.getScore());
    }
}