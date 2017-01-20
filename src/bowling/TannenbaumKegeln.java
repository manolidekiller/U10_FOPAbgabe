package bowling;

import java.util.*;

public class TannenbaumKegeln extends Game {

	private HashMap<Integer, LinkedList<Integer>> score;

	public TannenbaumKegeln(int maxPlayers) {
		super(maxPlayers);
		super.setPinCount(9);
		super.setMaxRound(100);
		super.setName("Tannenbaumkegeln");

		score = new HashMap<Integer, LinkedList<Integer>>();

	}
/**
 * gibt eine score Liste fuer das Spiel Tannenbaum wieder
 * @return LinkedList aus Integer
 */
	private LinkedList<Integer> getFreshScoreTable() {
		LinkedList<Integer> newScoreTable = new LinkedList<Integer>();
		newScoreTable.add(1);
		newScoreTable.add(2);
		newScoreTable.add(2);
		newScoreTable.add(3);
		newScoreTable.add(3);
		newScoreTable.add(3);
		newScoreTable.add(3);
		newScoreTable.add(3);
		newScoreTable.add(3);
		newScoreTable.add(3);
		newScoreTable.add(4);
		newScoreTable.add(4);
		newScoreTable.add(4);
		newScoreTable.add(4);
		newScoreTable.add(4);
		newScoreTable.add(4);
		newScoreTable.add(5);
		newScoreTable.add(5);
		newScoreTable.add(5);
		newScoreTable.add(5);
		newScoreTable.add(5);
		newScoreTable.add(6);
		newScoreTable.add(6);
		newScoreTable.add(6);
		newScoreTable.add(6);
		newScoreTable.add(7);
		newScoreTable.add(7);
		newScoreTable.add(7);
		newScoreTable.add(8);
		newScoreTable.add(8);
		newScoreTable.add(9);
		return newScoreTable;
	}

	@Override
	public int[] getScore(Player player) {
		int[] returnValue = new int[score.get(player.getID()).size()];
		for (int i = 0; i < score.get(player.getID()).size(); i++) {
			returnValue[i] = score.get(player.getID()).get(i);
		}
		return returnValue;
	}

	@Override
	public Player getWinner() {
		Player bestPlayer = null;
		if (super.getRound()-1 >= super.getRoundCount()) {
			int smallestSizeYet = Integer.MAX_VALUE;
			for (Player p : super.getListOfPlayers()) {
				if (smallestSizeYet > score.get(p.getID()).size()) {
					smallestSizeYet = score.get(p.getID()).size();
					bestPlayer = p;
				}
			}
			super.setHasFinished(true);
		} else {
			for (Player p : super.getListOfPlayers()) {
				if (score.get(p.getID()).size() == 0) {
					super.setHasFinished(true);
					bestPlayer = p;
				}
			}
		}
		return bestPlayer;
	}

	@Override
	public boolean startGame() {
		for (Player p : super.getListOfPlayers()) {
			score.put(p.getID(), getFreshScoreTable());
		}
		super.resetPins();
		return super.startGame();
	}

	@Override
	public boolean throwBall(int count) {
		if (!super.throwBall(count))
			return false;
		Player activePlayer = super.getActivePlayer();
		getWinner();

		if (super.getThrow() == 1) {
			if (count == 9) { // Strike ----------------------------------------
				score.get(activePlayer.getID()).remove(Integer.valueOf(count));
				super.setActivePlayer(this.getNextPlayer(activePlayer));
				return true;
				// -------------------------------------------------------------
			}
			// Kein Strike -----------------------------------
			zwischenspeicher = count;
			
			super.setThrow(2);
			super.setPinsLeft(super.getPinsLeft() - count);
			return true;
			// -----------------------------------------------
		} else if (super.getThrow() == 2) {
			score.get(activePlayer.getID()).remove(Integer.valueOf(count+zwischenspeicher));
			super.setActivePlayer(this.getNextPlayer(activePlayer));
			return true;
		} else return false;
	}

	private int zwischenspeicher = 0;
/**
 * Wechselt den Spieler zum naechsten Spieler. Ist man beim letzten angekommen wird wieder zum ersten Spieler geweschelt
 * und die Runde um 1 erhoeht
 * @param actual
 * @return Player object
 */
	private Player getNextPlayer(Player actual) {
		zwischenspeicher = 0;
		super.setThrow(1);
		super.setPinsLeft(super.getPinCount());
		if (actual.equals(super.getListOfPlayers().getLast())) {
			super.setRound(getRound() + 1);
			return super.getListOfPlayers().getFirst();
		} else
			return super.getListOfPlayers().get(super.getListOfPlayers().indexOf(actual) + 1);
	}
}