package com.quad.states;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.quad.core.GameContainer;
import com.quad.core.Input;
import com.quad.core.Renderer;
import com.quad.core.components.ImageUtils;
import com.quad.core.components.State;
import com.quad.core.fx.Image;

import fr.crusche.beziermanagement.BezierCurve;
import fr.crusche.deschiffres.Compte;
import fr.crusche.deschiffres.Result;
import fr.crusche.deschiffres.Solution;
import fr.triobin.deschiffresetdeslettres.Score;

public class GameStartTest extends State {

	private int windowMouseX = 1920;
	private int windowMouseY = 1080;

	@Override
	public void init(GameContainer gc) {
		// Initiate state
	}

	@Override
	public void update(GameContainer gc, float dt) {
		// Update state
		Input input = gc.getInput();
		// if s
		if (input.isKeyPressed(27)) {
			gc.stop();
		}

		if (gc.getGame().cache.isGameWithBot) {
			System.out.println("Bot is playing");
			int RoundTypeId = -1;
			if (gc.getGame().cache.currentRound != 1) {
				if (gc.getGame().cache.currentRound > gc.getGame().cache.roundList.length) {
					gc.getGame().cache.nextState = 7;
					RoundTypeId = gc.getGame().cache.roundList[gc.getGame().cache.currentRound];
				} else {
					RoundTypeId = gc.getGame().cache.roundList[gc.getGame().cache.currentRound - 1];
				}
				
				if (RoundTypeId == 1) {

				} else if (RoundTypeId == 2) {
					List<Integer> listPlaquesList = (ArrayList<Integer>) gc.getGame().cache.botData
							.get("generatedList");
					int[] listPlaques = listPlaquesList.stream().mapToInt(i -> i).toArray();

					int i = 0;

					int objective = (int) (listPlaques.length * (1 - gc.getGame().cache.botDifficulty));

					while (i < objective) {
						// opération aléatoire
						int randomOp = (int) Math.floor(Math.random() * 4);
						int[] randomPlaques = new int[2];
						randomPlaques[0] = (int) Math.round(Math.random() * (listPlaques.length - 1));
						randomPlaques[1] = (int) Math.round(Math.random() * (listPlaques.length - 2));
						if (randomPlaques[1] == randomPlaques[0]) {
							randomPlaques[1] += 1;
						}
						int result = 0;
						switch (randomOp) {
							case 0:
								result = listPlaques[randomPlaques[0]] + listPlaques[randomPlaques[1]];
								break;

							case 1:
								result = listPlaques[randomPlaques[0]] - listPlaques[randomPlaques[1]];
								break;

							case 2:
								result = listPlaques[randomPlaques[0]] * listPlaques[randomPlaques[1]];
								break;

							case 3:
								try {
									result = listPlaques[randomPlaques[0]] / listPlaques[randomPlaques[1]];
								} catch (ArithmeticException e) {
									result = listPlaques[randomPlaques[0]];
								}
								break;
						}

						if (result != 0) {
							// remove the used id in int[] listPlaques
							int[] temp = new int[listPlaques.length - 2];
							int k = 0;

							for (int j = 0; j < listPlaques.length; j++) {
								if (j != randomPlaques[0] && j != randomPlaques[1]) {
									temp[k++] = listPlaques[j];
								}
							}
							listPlaques = temp;

							// add the result to the list
							int[] temp2 = new int[listPlaques.length + 1];

							for (int j = 0; j < listPlaques.length; j++) {
								temp2[j] = listPlaques[j];
							}
							temp2[listPlaques.length] = result;
							listPlaques = temp2;

							i++;
						}
					}

					Compte cpt = new Compte(listPlaques);

					// Liste de choix de plaques
					int[] plaques = cpt.GetPlaques();

					// Nombre à atteindre
					int tirage = (int) gc.getGame().cache.botData.get("goalnumber");

					// Initialize the recursive calculation root structure
					Solution solution = new Solution();
					solution.tirage = tirage;
					solution.depth = plaques.length;
					Result res = new Result();
					res.steps = plaques;
					res.text = "";
					res.value = 0;
					Arrays.sort(res.steps);
					solution.current.add(res);

					// Initialize the best approaching structure
					solution.best = new Result();
					solution.best.steps = res.steps;
					solution.best.value = solution.best.steps[solution.best.steps.length - 1];
					solution.best.text = String.format("%d", solution.best.value);

					// Start the recursive resolution
					solution = cpt.SolveTirage(solution);

					// Display the result
					// solution.best.value
					System.out.println("listPlaques : " + Arrays.toString(listPlaques));
					System.out.println("Tirage : " + tirage);
					System.out.println("Bot result : " + solution.best.text);
				}
			}

			gc.getGame().cache.currentPlayer = 1;
			for (int i = 0; i < 2; i++) {
				gc.getGame().cache.scores.add(new Score());
			}

			if (gc.getGame().cache.currentRound > gc.getGame().cache.roundList.length) {
				gc.getGame().cache.nextState = 7;
			} else if (gc.getGame().cache.roundList[gc.getGame().cache.currentRound - 1] == 1) {
				gc.getGame().cache.nextState = 3;
			} else if (gc.getGame().cache.roundList[gc.getGame().cache.currentRound - 1] == 2) {
				for (int i = 0; i < gc.getGame().cache.bestPlayerInTheRoundId.length; i++) {
					if (gc.getGame().cache.bestPlayerInTheRoundValue == 0) {
						gc.getGame().cache.scores.get(gc.getGame().cache.bestPlayerInTheRoundId[i] - 1)
								.addScore(10);
					} else {
						gc.getGame().cache.scores.get(gc.getGame().cache.bestPlayerInTheRoundId[i] - 1)
								.addScore(7);
					}
				}
				gc.getGame().cache.nextState = 5;
			}

			gc.getGame().cache.currentRound += 1;

		} else {
			if (gc.getGame().cache.currentRound == 1 && gc.getGame().cache.currentPlayer == 0) {
				gc.getGame().cache.scores = new ArrayList<Score>();
				for (int i = 0; i < gc.getGame().cache.playerNumber; i++) {
					gc.getGame().cache.scores.add(new Score());
				}
			}

			if (gc.getGame().cache.isGameSimultaneous) {
				if (gc.getGame().cache.currentRound == 1) {
					gc.getGame().cache.nextState = 4;
				}
			} else {
				gc.getGame().cache.currentPlayer += 1;

				if (gc.getGame().cache.currentPlayer > gc.getGame().cache.playerNumber) {
					gc.getGame().cache.currentPlayer = 1;
					gc.getGame().cache.currentRound += 1;
				}

				if (gc.getGame().cache.currentRound > gc.getGame().cache.roundList.length) {
					gc.getGame().cache.nextState = 7;
				} else if (gc.getGame().cache.roundList[gc.getGame().cache.currentRound - 1] == 1) {
					gc.getGame().cache.nextState = 3;
				} else if (gc.getGame().cache.roundList[gc.getGame().cache.currentRound - 1] == 2) {
					for (int i = 0; i < gc.getGame().cache.bestPlayerInTheRoundId.length; i++) {
						if (gc.getGame().cache.bestPlayerInTheRoundValue == 0) {
							gc.getGame().cache.scores.get(gc.getGame().cache.bestPlayerInTheRoundId[i] - 1)
									.addScore(10);
						} else {
							gc.getGame().cache.scores.get(gc.getGame().cache.bestPlayerInTheRoundId[i] - 1)
									.addScore(7);
						}
					}
					gc.getGame().cache.nextState = 5;
				}
			}
		}

		gc.getGame().setState(gc, 8);
	}

	@Override
	public void render(GameContainer gc, Renderer r, float dt) {
		// Render state
		r.drawFillRect(0, 0, 1920, 1080, 0xffff00, null);
	}

	@Override
	public void dipose() {
		// Dispose state
	}

}