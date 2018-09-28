package gameobject;

import gui.GameImage;
import skeleton.Skeleton;

/**
 *Játékost reprezentáló osztály.
 *@see Movable
 */
public class Player extends Movable  {
	
	/**
	 * A játékos súlya.
	 */
	private static final int PLAYER_WEIGHT = 40;
	
	/**
	 * A játékos tolóereje.
	 */
	private int power;
	
	/**
	 * A játékos pontszámának pillanatnyi értéke.
	 */
	private int points;
	
	/**
	 * A prototípus kimenetéhez használt azonosítója a játékosnak.
	 */
	private String protoName = "";
	
	/**
	 * A játékos képe.
	 */
	private GameImage playerImage;
	
	/**
	 * A prototípusban kimenetéhez használt azonosító beállítása.
	 * @param n A játékos prototípusban használt neve.
	 */
	public void setProtoName(String n) {
		protoName = n;
	}
	
	/**
	 * Konstruktor. Beállítja a játékos súlyát és erejét.
	 * @param power A játékos ereje.
	 * @param pi A játékos képe.
	 */
	public Player(int power, GameImage pi) {
		super(PLAYER_WEIGHT);
		this.power = power;
		playerImage = pi;
	}
	
	/**
	 * Visszaadja a játékos pontszámának pillanatnyi értékét.
	 * @return pontszám
	 */
	public int getPoints() {
		Skeleton.printStart(this, "getPoints()");
		int result = points;
		Skeleton.printReturn(result);
		return result;
	}
	
	/**
	 * Növeli a játékos pontszámának értékét.
	 * @param inc A növelés mértéke.
	 */
	public void increasePointsBy(int inc) {
		Skeleton.printStart(this, "increasePointsBy(inc: " + inc + ")");
		points += inc;
		Skeleton.printReturn("void");
	}
	
	/**
	 * Lépteti a játékost egy adott irányba.
	 * @param d A haladási irány.
	 * @return Ha a játékos lépett akkor true, különben false.
	 */
	public boolean goInDirection(Direction d) {
		Skeleton.printStart(this, "goInDirection(d: " + d + ")");
		boolean result = field.getNeighbour(d).placeMovableOnThis(this, d, power);
		switch(d) {
		case Up: heading = 0; break;
		case Right: heading = 90; break;
		case Down: heading = 180; break;
		case Left: heading = 270; break;
		}
		Skeleton.printReturn(result);
		return result;
	}
	
	/**
	 * Emeli a mező surlódását, amin a játékos éppen áll.
	 */
	public void frictionUp() {
		Skeleton.printStart(this, "frictionDown()");
		field.increaseFriction();
		Skeleton.printReturn("void");
	}
	
	/**
	 * Csökkenti a mező surlódását, amin a játékos éppen áll.
	 */
	public void frictionDown() {
		Skeleton.printStart(this, "frictionDown()");
		field.decreaseFriction();
		Skeleton.printReturn("void");
	}
	
	/**
	 * Movable osztálytól örökölt absztrakt metódus.
	 * Visszaadja, hogy a játékos adott irányba tolható-e.
	 * Ezt a metódust akkor használjuk, amikor megállapítjuk egy dobozról, hogy tolható-e vagy nem.
	 * @param d Tolási irány
	 * @return true, mert egy játékos mindig tolható.
	 * @see Movable
	 */
	@Override
	public boolean isPushable(Direction d) {
		Skeleton.printStart(this, "isPushable(d: " + d + ")");
		
		boolean result = true;
		Skeleton.printReturn(result);
		return result;
	}

	/**
	 * Movable osztálytól örökölt absztrakt metódus.
	 * Mozgatja a játékost egy célmezőre. Ilyenkor csak simán lépni próbál a játékos.
	 * @param t a célmező
	 * @see Movable
	 */
	@Override
	public void moveToTarget(Target t) {
		Skeleton.printStart(this, "moveToTarget(t: " + t + ")");
		
		this.moveToField(t);
		
		Skeleton.printReturn("void");
	}

	/**
	 * @see GameObject
	 */
	@Override
	public String protoInfo() {
		return protoName;
	}

	/**
	 * Visszaadja a Játékos képét.
	 */
	@Override
	public GameImage getImage() {
		return playerImage;
	}
	
}
