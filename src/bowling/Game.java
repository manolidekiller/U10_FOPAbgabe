package bowling;

import java.util.LinkedList;
/**
 * 
 * @author Emmanouil Vergopoulos, Ozan Agtas, Jasmin Reis Klapper, Berfin Korkmaz
 *
 */
public abstract class Game implements IGame {

	private boolean gameStarted;
	private boolean gameFinished;
	private String name;

	private int currentRound;
	private int maxRounds;
	private int pinsLeft;
	private int maxPins;
	private int maxPlayerCount;
	private int throwNr;

	private Player currentPlayer;
	private LinkedList<Player> listOfPlayers;

	public Game(int maxPlayers) {
		this.maxPlayerCount = maxPlayers;
		this.gameStarted = false;
		this.gameFinished = false;
		this.currentRound = 1;
		this.throwNr = 1;
		this.maxRounds = 0;
		this.listOfPlayers = new LinkedList<Player>();
		System.out.println(this.gameStarted);
		// this.listOfPlayers.ensureCapacity(2 * maxPlayers);
	}

	@Override
	public Player addPlayer(String name) {
		Player p = new Player(listOfPlayers.size(), name);
		listOfPlayers.add(p);
		System.out.println(p.toString());
		return p;
	}

	@Override
	public Player getActivePlayer() {
		return this.currentPlayer;
	}
	public void setActivePlayer(Player p) {
		this.currentPlayer = p;
	}

	@Override
	public int getActivePlayerCount() {
		return this.listOfPlayers.size();
	}

	@Override
	public int getMaxPlayerCount() {
		return this.maxPlayerCount;
	}

	@Override
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int getPinsLeft() {
		return this.pinsLeft;
	}
	public void setPinsLeft(int nr) {
		this.pinsLeft = nr;
	}

	@Override
	public int getPinCount() {
		return this.maxPins;
	}
	public void setPinCount(int pins) {
		this.maxPins = pins;
	}

	@Override
	public Player getPlayer(int id) {
		for (Player p : listOfPlayers) {
			if (p.getID() == id)
				return p;
		}
		return null;
	}

	public LinkedList<Player> getListOfPlayers() {
		return this.listOfPlayers;
	}

	@Override
	public int getRound() {
		return this.currentRound;
	}
	public void setRound(int newRound) {
		this.currentRound = newRound;
	}
	
	@Override
	public int getRoundCount() {
		return this.maxRounds;
	}
	public void setMaxRound(int rounds) {
		this.maxRounds = rounds;
	}

	@Override
	public int getThrow() {
		return throwNr;
	}
	public void setThrow(int throwNr) {
		this.throwNr = throwNr;
	}

	@Override
	public boolean hasFinished() {
		return gameFinished;
	}
	public void setHasFinished(boolean f) {
		gameFinished = f;
	}
	
	@Override
	public boolean hasStarted() {
		return gameStarted;
	}

	@Override
	public boolean startGame() {
		if (hasStarted())
			System.err.println("Spiel ist bereits gestartet!");
		if (listOfPlayers.size() < 2)
			System.err.println("Anzahl der Spieler ist zur klein!");

		if (!gameStarted && listOfPlayers.size() >= 2 && getName() != null && getRoundCount() > 0 && this.getPinCount() > 0) {
			this.setActivePlayer(this.getListOfPlayers().getFirst());
			gameStarted = true;
		}
		this.resetPins();
		return hasStarted();
	}
	/**
	 * 
	 */
	public void resetPins()
	{
		this.pinsLeft = this.maxPins;
	}

	@Override
	public boolean throwBall(int count) {
		if (!hasStarted() || hasFinished()) {
			System.err.println("Spiel hat noch nicht gestartet oder ist bereits beendet!");
			return false;
		}
		if (count > getPinsLeft() || count < 0) {
			System.err.println("Anzahl der umgeworfenen Pins ist kleiner 0 oder negativ");
			return false;
		}
		
		return true;
	}
}
