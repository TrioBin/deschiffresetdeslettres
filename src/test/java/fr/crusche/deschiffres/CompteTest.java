package fr.crusche.deschiffres;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.*;

public class CompteTest {

    @Test
    public void testSolveTirageExactSolution() {
        Compte compte = new Compte();
        Solution solution = new Solution();
        solution.tirage = 100;
        solution.depth = 1;
        Result result = new Result();
        result.value = 100;
        result.steps = new int[] { 100 };
        solution.current = Arrays.asList(result);
        solution.best = result;

        Solution solved = compte.SolveTirage(solution);

        assertEquals(100, solved.best.value);
    }

    @Test
    public void testSolveTirageApproximateSolution() {
        Compte compte = new Compte();
        Solution solution = new Solution();
        solution.tirage = 100;
        solution.depth = 2;
        Result result1 = new Result();
        result1.value = 50;
        result1.steps = new int[] { 50 };
        Result result2 = new Result();
        result2.value = 25;
        result2.steps = new int[] { 25 };
        solution.current = Arrays.asList(result1, result2);
        solution.best = result1;

        Solution solved = compte.SolveTirage(solution);

        assertTrue(solved.best.value <= 100);
    }

    @Test
    public void testSolveTirageNoSolution() {
        Compte compte = new Compte();
        Solution solution = new Solution();
        solution.tirage = 100;
        solution.depth = 1;
        Result result = new Result();
        result.value = 50;
        result.steps = new int[] { 50 };
        solution.current = Arrays.asList(result);
        solution.best = result;

        Solution solved = compte.SolveTirage(solution);

        assertNotEquals(100, solved.best.value);
    }

    @Test
    public void testGetPlaques() {
        Compte compte = new Compte();
        int[] tirage = compte.GetPlaques();
        assertEquals(6, tirage.length);
        for (int plaque : tirage) {
            assertTrue(plaque >= 1 && plaque <= 100);
        }
    }

    @Test
    public void testGetTirage() {
        Compte compte = new Compte();
        int tirage = compte.GetTirage();
        assertTrue(tirage >= 101 && tirage <= 999);
    }
}