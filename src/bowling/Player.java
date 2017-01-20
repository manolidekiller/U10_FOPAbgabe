package bowling;

public class Player {
	private int id;
	private String name;
	
	/** Konstruktor der Klasse
	 * @param id Eindeutige ID des Player
	 * @param name Name des Player
	 */
	public Player(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	/** Getter-Methode von name
	 * @return string name
	 */
	public String getName() {
		return this.name;
	}
	
	/** Getter-Methode für id
	 * @return int id
	 */
	public int getID() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return String.format("%s: %s",getID(), getName());
	}

	
}
