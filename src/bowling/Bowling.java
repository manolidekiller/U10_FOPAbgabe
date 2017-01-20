package bowling;

import java.util.*;

public class Bowling extends Game {

	private HashMap<Integer, LinkedList<Integer>> scorelist;

	public Bowling(int numOfPlayers) {
		super(numOfPlayers);
		super.setMaxRound(10);
		super.setPinCount(10);
		super.setName("Bowling");
		scorelist = new HashMap<Integer, LinkedList<Integer>>();
	}

	@Override
	public boolean startGame() {
		for (Player p : super.getListOfPlayers()) {
			scorelist.put(p.getID(), new LinkedList<Integer>());
		}
		super.resetPins();
		return super.startGame();
	}

	@Override
	public int[] getScore(Player player) {
		LinkedList<Integer> all = scorelist.get(player.getID());
		int[] retValue = new int[21];
		int[] retValue2 = new int[10];

		for (int i = 0; i < all.size(); i++) {
			if (all.get(i) != null) {
				retValue[i] = all.get(i);
			}
		}

		int j = 0;
		for (int i = 0; i < retValue2.length; i++) {
			if (j < 18) {
				if (retValue[j] == 10) {;
					retValue2[i] = retValue[j] + retValue[j + 2];
					if (retValue[j + 2]==10)
						 retValue2[i] += retValue[j + 4];
					else retValue2[i] += retValue[j + 3];
					if (i > 0 && i < super.getRound())
						retValue2[i] += retValue2[i - 1];
					j += 2;
				} else if (retValue[j] + retValue[j + 1] == 10) {
					retValue2[i] = retValue[j] + retValue[j + 1] + retValue[j + 2];
					if (i > 0 && i < super.getRound())
						retValue2[i] += retValue2[i - 1];
					j += 2;
				} else {
					retValue2[i] = retValue[j] + retValue[j + 1];
					if (i > 0 && i < super.getRound())
						retValue2[i] += retValue2[i - 1];
					j += 2;
				}
			}
		}
		retValue2[9] = retValue2[8] + retValue[18] + retValue[19] + retValue[20];

		return retValue2;
	}

	@Override
	public Player getWinner() {
		if (super.getRound() == super.getRoundCount()) {
			Player bestPlayer = null;
			int bestScoreYet = -1;
			for (Player p : super.getListOfPlayers()) {
				int endScore = getScore(p)[9];
				if (endScore > bestScoreYet) {
					bestPlayer = p;
					bestScoreYet = endScore;
				}
			}
			// super.setHasFinished(true);
			return bestPlayer;
		}
		return null;
	}

	@Override
	public boolean throwBall(int count) {
		if (!super.throwBall(count))
			return false;
		Player activePlayer = super.getActivePlayer();
		LinkedList<Integer> activePlayerScore = scorelist.get(super.getActivePlayer().getID());
		if (super.getRound() < 10) {
			if (super.getThrow() == 1) {
				if (count == 10) { // 1. Wurf = Strike
					activePlayerScore.add(count);
					activePlayerScore.add(0);
					Player newActivePlayer = endTurnOfActivePlayer(activePlayer);
					super.setActivePlayer(newActivePlayer);
					return true;
				} else if (count < 10) { // 1. Wurf = kein Strike
					activePlayerScore.add(count);
					super.setPinsLeft(super.getPinsLeft() - count);
					super.setThrow(2);
					return true;
				}
			} else if (super.getThrow() == 2) {
				activePlayerScore.add(count);
				Player newActivePlayer = endTurnOfActivePlayer(activePlayer);
				super.setActivePlayer(newActivePlayer);
				return true;
			}
		} else if (super.getRound() == 10) {
			if (super.getThrow() == 1) {
				if (count == 10) {
					activePlayerScore.add(count);
					super.resetPins();
					super.setThrow(2);
					return true;
				} else if (count < 10) {
					activePlayerScore.add(count);
					super.setPinsLeft(super.getPinsLeft() - count);
					super.setThrow(2);
					return true;
				}
			} else if (super.getThrow() == 2) {
				if (activePlayerScore.getLast() + count >= 10) {
					activePlayerScore.add(count);
					super.resetPins();
					super.setThrow(3);
					return true;
				} else {
					activePlayerScore.add(count);
					Player newActivePlayer = endTurnOfActivePlayer(activePlayer);
					super.setActivePlayer(newActivePlayer);
					return true;
				}
			} else if (super.getThrow() == 3) {
				activePlayerScore.add(count);
				Player newActivePlayer = endTurnOfActivePlayer(activePlayer);
				if (activePlayer.equals(super.getListOfPlayers().getLast())) {
					super.setRound(super.getRound() + 1);
				} 
				super.setActivePlayer(newActivePlayer);
				return true;
			}
		}
		getWinner();
		return false;
	}
	/**
	 * Wechselt den Spieler zum naechsten Spieler. Ist man beim letzten angekommen wird wieder zum ersten Spieler geweschelt
	 * und die Runde um 1 erhoeht
	 * @param actual
	 * @return Player object
	 */
	private Player endTurnOfActivePlayer(Player actual) {
		super.setThrow(1);
		super.setPinsLeft(super.getPinCount());
		if (actual.equals(super.getListOfPlayers().getLast())) {
			if (super.getRound()!=10)
				super.setRound(super.getRound() + 1);
			else { 
				super.setHasFinished(true); 
			}
			return super.getListOfPlayers().getFirst();
		} else
			return super.getListOfPlayers().get(super.getListOfPlayers().indexOf(actual) + 1);
	}
}
