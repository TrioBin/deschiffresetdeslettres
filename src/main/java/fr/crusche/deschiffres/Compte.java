package fr.crusche.deschiffres;

import java.util.*;

public class Compte {
    private final int nbPlaques = 24;
    private final int nbTirage = 6;
    private final int  minTirage= 101;
    private final int  maxTirage= 999;
    private int[] plaques = new int[nbPlaques];
    private Random rnd = new Random();

    public Compte() {
        int idx = 0;
        for (int n = 1; n <= 10; n++) {
            plaques[idx] = n;
            plaques[idx+1] = n;
            idx += 2;
        }
        plaques[idx++] = 25;
        plaques[idx++] = 50;
        plaques[idx++] = 75;
        plaques[idx++] = 100;
    }

    public int[] GetPlaques() {
        List<Integer> chosenIdx = new ArrayList<Integer>();
        int nbChosen = 0;
        while (nbChosen < nbTirage) {
            int p = rnd.nextInt(nbPlaques);
            if (!chosenIdx.contains(p)) {
                chosenIdx.add(p);
                nbChosen++;
            }
        }

        int[] tirage = new int[nbTirage];
        for (int i=0; i<nbTirage; i++) {
            tirage[i] = plaques[chosenIdx.get(i)];
        }
        return tirage;
    }

    public int GetTirage() {
        return minTirage + rnd.nextInt(maxTirage - minTirage + 1);
    }

    public Solution SolveTirage(Solution solution) {
        if (solution.depth == 1) {
            return solution;
        }

        List<Result> newCur = new ArrayList<Result>();
        for (Result cur : solution.current) {
            for (int l=1; l<cur.steps.length; l++) {
                for (int r=0; r<l; r++) {
                    for (int o=0; o<4; o++) {
                        Result res = Calculate(cur.steps, l, r, Operation.values()[o]);
                        if (res != null) {
                            res.text = cur.text + res.text + String.format("%n");

                            if (Math.abs(res.value - solution.tirage) < Math.abs(solution.best.value - solution.tirage)) {
                                solution.best = res;
                            }

                            if (solution.best.value == solution.tirage) {
                                return solution;
                            }
                            else {
                                newCur.add(res);
                            }
                        }
                    }
                }
            }
        }

        solution.current = newCur;
        solution.depth--;

        return SolveTirage(solution);
    }

    public enum Operation {
        Add,
        Multiply,
        Substract,
        Divide
    }

    public Result Calculate(int[] steps, int l, int r, Operation op) {
        Result result = new Result();
        result.steps = new int[steps.length-1];
        int n = 0;
        for (int i=0; i<steps.length; i++) {
            if (i != l && i != r) {
                result.steps[n] = steps[i];
                n++;
            }
        }
        switch(op) {
            case Add: {
                result.steps[n] = steps[l] + steps[r];
                result.value = result.steps[n];
                result.text = String.format("%d + %d = %d%n", steps[l], steps[r], result.value);
                Arrays.sort(result.steps);
                return result;
            }
            case Multiply: {
                if (steps[r] != 1) {
                    result.steps[n] = steps[l] * steps[r];
                    result.value = result.steps[n];
                    result.text = String.format("%d x %d = %d%n", steps[l], steps[r], result.value);
                    Arrays.sort(result.steps);
                    return result;
                }
                break;
            }
            case Substract: {
                if (steps[l] > steps[r]) {
                    result.steps[n] = steps[l] - steps[r];
                    result.value = result.steps[n];
                    result.text = String.format("%d - %d = %d%n", steps[l], steps[r], result.value);
                    Arrays.sort(result.steps);
                    return result;
                }
                break;
            }
            case Divide: {
                if (steps[r] != 1 && steps[l] % steps[r] == 0) {
                    result.steps[n] = steps[l] / steps[r];
                    result.value = result.steps[n];
                    result.text = String.format("%d / %d = %d%n", steps[l], steps[r], result.value);
                    Arrays.sort(result.steps);
                    return result;
                }
                break;
            }
        }
        return null;
    }
}