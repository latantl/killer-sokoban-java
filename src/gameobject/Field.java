package gameobject;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import gui.GameImage;
import skeleton.Skeleton;
/**
 * A mezőket reprezentáló absztrakt osztály.
 */
public abstract class Field extends GameObject {

	/**
	 * A mezőn lévő mozgatható objektum.
	 */
	protected Movable movable;
	/**
	 * A mező szomszédait tároló tömb.
	 * Mind a 4 irányban képes tárolni a szomszédokat.
	 * key: Direction, value: Field.
	 */
	private Map<Direction, Field> neighbours = new HashMap<Direction, Field>(); 
	/**
	 * A mezőhöz tartozó súrlódás.
	 */
	private int friction = 1;
	/**
	 * A mező súrlódásának lekérdezése.
	 * @return A mező súrlódasa.
	 */
	public int getFriction() {
		Skeleton.printStart(this, "getFriction()");
		Skeleton.printReturn(friction);
		return friction;
	}
	/**
	 * Növeli a mező súrlódását, ha lehet.
	 */
	public void increaseFriction() {
		Skeleton.printStart(this, "increaseFriction()");
		if(friction < 2)
			friction++;
		Skeleton.printReturn("void");
	}
	/**
	 * Csökkenti a mező súrlódását, ha lehet.
	 */
	public void decreaseFriction() {
		Skeleton.printStart(this, "decreaseFriction()");
		if(friction > 0)
			friction--;
		Skeleton.printReturn("void");
	}
	/**
	 * A mozgatható beállítása.
	 * @param m A mozgatható objektum.
	 * Beállítja a mezőn álló mozgatható objektumot.
	 */
	public void setMovable(Movable m) {
		Skeleton.printStart(this, "setMovable(" + m + ")");
		this.movable = m;
		Skeleton.printReturn("void");
	}
	/**
	 * Szomszéd lekérdezése.
	 * @param d Az iránya.
	 * @return d Ebben az irányban szomszédos mező. 
	 */
	public Field getNeighbour(Direction d) {
		Skeleton.printStart(this, "getNeighbour(d: " + d + ")");
		Field result = neighbours.get(d);
		Skeleton.printReturn(result);
		return result;
	}
	/**
	 * Szomszéd beállítása.
	 * @param f A szomszéd mező objektuma.
	 * @param d A szomszéd iránya.
	 */
	public void setNeighbour(Field f, Direction d) {
		Skeleton.printStart(this, "getNeighbour(" + d + ")");
		neighbours.put(d, f);
		Skeleton.printReturn("void");
	}
	/**
	 * Mozgatható törlése a mezőről.
	 * Törli a referenciát.
	 */
	public void clearMovable() {
		Skeleton.printStart(this, "clearMovable()");
		movable = null;
		Skeleton.printReturn("void");
	}
	/**
	 * @param d Az irány.
	 * Ellenőrzi, hogy eltolható-e d irányban az objektum.
	 * @return True, ha nincs a mezőn semmi.
	 * @return False, ha a mozgatható nem tud innen elmozdulni.
	 */
	public boolean canPlaceHere(Direction d) {
		Skeleton.printStart(this, "canPlaceHere(" + d + ")");
		boolean result = (movable == null) ? true : movable.isPushable(d);
		Skeleton.printReturn(result);
		return result;
	}
	/**
	 * A léptetést végzi.
	 * @param m A mozgatható objektum.
	 * @param d Az iránya.
	 * @param power A tolás ereje.
	 * @return True, ha sikerul ráléptetni az érkező objektumot, akkor igazzal tér vissza.
	 */
	public abstract boolean placeMovableOnThis(Movable m, Direction d, int power);
	
	/**
	 * A mezők az egyéb infójuk után kiírják a surlódásukat is.
	 * @see GameObject
	 */
	@Override
	public String protoInfo() {
		String s = this.protoFieldInfo() + friction;
		if(movable != null)
			s += movable.protoInfo();
		return s;
	}
	
	/**
	 * Mezők prototípushoz szükséges információja (típus és állapot) String-ben.
	 * @return Inormáció a mezőről.
	 */
	public abstract String protoFieldInfo();
	
	/**
	 * A mező képét és az esetlegesen rajta lévő mozgó objektum képét rárajzoló metódus.
	 * @param g A grafikus felület, amelyre rajzol.
	 * @param p A panel, amelyre rajzol.
	 * @param x A képek x koordinátája.
	 * @param y A képek y koordinátája.
	 * @param blocksize A képek mérete.
	 */
	public void paintImageArray(Graphics2D g, JPanel p, double x, double y, double blocksize) {
		this.getImage().draw(g, p, x, y, blocksize, 0);
		switch(friction) {
		case 0: GameImage.OIL_IMG.draw(g, p, x, y, blocksize, 0); break;
		case 2: GameImage.HONEY_IMG.draw(g, p, x, y, blocksize, 0); break;
		}
		if(movable == null) return;
		movable.getImage().draw(g, p, x, y, blocksize, movable.getHeading());
	}
	
}