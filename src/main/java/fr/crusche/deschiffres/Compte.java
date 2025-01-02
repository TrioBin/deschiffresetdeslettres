package fr.crusche.deschiffres;

import java.util.*;

public class Compte {
    private int[] plaques;
    private Random rnd = new Random();

    public Compte(int[] plaques) {
        this.plaques = plaques;
    }

    public int[] GetPlaques() {
        return plaques;
    }

    public Solution SolveTirage(Solution solution) {
        if (solution.depth == 1) {
            return solution;
        }

        List<Result> newCur = new ArrayList<Result>();
        for (Result cur : solution.current) {
            for (int l = 1; l < cur.steps.length; l++) {
                for (int r = 0; r < l; r++) {
                    for (int o = 0; o < 4; o++) {
                        Result res = Calculate(cur.steps, l, r, Operation.values()[o]);
                        if (res != null) {
                            res.text = cur.text + res.text + String.format("%n");

                            if (Math.abs(res.value - solution.tirage) < Math
                                    .abs(solution.best.value - solution.tirage)) {
                                solution.best = res;
                            }

                            if (solution.best.value == solution.tirage) {
                                return solution;
                            } else {
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
        result.steps = new int[steps.length - 1];
        int n = 0;
        for (int i = 0; i < steps.length; i++) {
            if (i != l && i != r) {
                result.steps[n] = steps[i];
                n++;
            }
        }
        switch (op) {
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
                if (steps[r] != 0 && steps[r] != 1 && steps[l] % steps[r] == 0) {
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