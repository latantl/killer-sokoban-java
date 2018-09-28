package game;

import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * Az alkalmazás egy állapotát reprezentáló absztrakt osztály.
 * A pracssoros be- és kimenetekért és (részben) a grafikus felhasználói felületért felel.
 * Az alkalmazás állapotait a StateManager osztály kezeli.
 * @see StateManager
 */
public abstract class State {
	
	/**
	 * Az állapot előkészületeit véghezvívő absztrakt metódus.
	 */
	public abstract void initializeState();
	
	/**
	 * Egy prototípus parancsot végrehajtó metódus.
	 * @param cmd A parancs neve.
	 */
	public abstract void protoExecute(String cmd);
	
	/**
	 * Az adott állapotban elérhető parancsoknak a listája.
	 * @return Parancsok listája.
	 */
	public abstract ArrayList<String> getDictionary();
	
	/**
	 * Az állapot neve.
	 * @return Az állapot neve.
	 */
	public abstract String getName();
	
	/**
	 * Az állapot parancssoros kimenetéért felelős metódus.
	 */
	public abstract void protoOut();
	
	/**
	 * A billentyű bemenetet kezelő metódus.
	 */
	public abstract void keyPressed(int key);

	/**
	 * Az állapot grafikus kimenete, a rajzoló metódus.
	 * @param g A grafikus felület, amelyre rajzolunk.
	 * @param p A panel, amelyre rajzolunk.
	 * @param x A rajzolandó kép bal szélének x koordinátája.
	 * @param y A rajzolandó kép felső szélének y koordinátája.
	 * @param w A rajzolandó kép szélessége.
	 * @param h A rajzolandó kép magassága.
	 */
	public abstract void paint(Graphics2D g, JPanel p, double x, double y, double w, double h);
	
}
