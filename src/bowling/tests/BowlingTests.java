package bowling.tests;

import static org.junit.Assert.*;
import org.junit.Test;
import bowling.*;

public class BowlingTests {

	@Test
	public void testGetScore() {
		IGame game = new Bowling(2);
		Player Joko = game.addPlayer("Joko");
		Player Klaas = game.addPlayer("Klaas");

		game.startGame();

		// Runde 0-10 simulieren
		for (int round = 0; round < 10; round++) {
			assertTrue(game.throwBall(5));
			assertTrue(game.throwBall(1));
			assertTrue(game.throwBall(5));
			assertTrue(game.throwBall(1));
		}

		int[] score = game.getScore(Klaas);

		assertEquals(60, score[9]);

	}

	@Test
	public void testGetWinner() {
		IGame game = new Bowling(2);
		Player p0 = game.addPlayer("Joko");
		Player p1 = game.addPlayer("Klaas");

		game.startGame();

		// Runde 0-10 simulieren
		for (int round = 0; round < 10; round++) {
			assertTrue(game.throwBall(5));
			assertTrue(game.throwBall(4));
			assertTrue(game.throwBall(4));
			assertTrue(game.throwBall(4));
		}

		assertTrue(game.hasFinished());
		assertEquals(p0, game.getWinner());
	}

	@Test
	public void testPerfectGame() {
		IGame game = new Bowling(2);

		Player Manoli = game.addPlayer("p0");
		Player Ozan = game.addPlayer("Ozan");

		assertTrue(game.startGame());

		for (int round = 0; round < 9; round++) {
			assertTrue(game.throwBall(10));
			assertTrue(game.throwBall(4));
			assertTrue(game.throwBall(5));
		}

		assertTrue(game.throwBall(10));
		assertTrue(game.throwBall(10));
		assertTrue(game.throwBall(10));
		assertTrue(game.throwBall(10));
		assertTrue(game.throwBall(10));
		assertTrue(game.throwBall(10));

		assertTrue(game.hasFinished());
		assertEquals(game.getScore(Manoli)[9], 300);

	}

	@Test
	public void test_CountRemainingPins() {
		// ARRANGE
		IGame tbs = new TannenbaumKegeln(5);
		tbs.addPlayer("Ozan");
		tbs.addPlayer("Manouil");
		tbs.addPlayer("WerDasLiestIstCool");
		tbs.startGame();

		// ACT
		tbs.throwBall(5);

		// ASSERT
		assertEquals(4, tbs.getPinsLeft());
	}

	@Test
	public void test_IfActivePlayerChanged() {
		// ARRANGE
		IGame tbs = new TannenbaumKegeln(5);
		tbs.addPlayer("Ozan");
		tbs.addPlayer("Manouil");
		tbs.startGame();

		// ACT
		for (int i = 0; i < 17; i++) {
			tbs.throwBall(1);
			tbs.throwBall(2);
		}

		// ASSERT
		assertEquals(tbs.getPlayer(1), tbs.getActivePlayer());
	}

	@Test
	public void testGetWinnerTannenbaum() {
		IGame game = new TannenbaumKegeln(2);

		Player p0 = game.addPlayer("Joko");
		Player p1 = game.addPlayer("Klaas");

		game.startGame();

		for (int round = 0; round < 100; round++) {
			assertTrue(game.throwBall(5));
			assertTrue(game.throwBall(1));
			assertTrue(game.throwBall(5));
			assertTrue(game.throwBall(3));

		}

		assertEquals(p0, game.getWinner());
	}

	@Test
	public void testThrowBallBowling() {
		IGame game = new Bowling(2);
		game.addPlayer("Ozan");
		game.addPlayer("Manoli");
		game.startGame();

		game.throwBall(8);

		assertEquals(2, game.getPinsLeft());

	}

	@Test
	public void testgetNextPlayerBowling() {
		IGame game = new Bowling(2);
		Player Ozan = game.addPlayer("Ozan");
		Player Manoli = game.addPlayer("Manoli");
		game.startGame();

		// ACT
		for (int i = 0; i < 17; i++) {
			game.throwBall(1);
			game.throwBall(2);
		}

		// ASSERT
		assertEquals(Manoli, game.getActivePlayer());

	}

	@Test
	public void testFalseTannenBaum() {
		IGame game = new TannenbaumKegeln(2);

		Player Ozan = game.addPlayer("Ozan");
		Player Manoli = game.addPlayer("Manoli");

		game.startGame();

		assertFalse(game.throwBall(11));

	}

}