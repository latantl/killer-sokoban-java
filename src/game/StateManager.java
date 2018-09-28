package game;

import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * A játék állapotának menedzseléséért felelős osztály.
 * Egy körkörös állapotgépet valósít meg, mert az alkalmazásunk ezt kívánja meg.
 * Első állapotunk a menü, onnan a játék állapotba lehet kerülni, majd a játéknak vége van
 * és újra a menübe lépünk.
 * @see State
 */
public class StateManager {
	
	/**
	 * Az alkalmazás által használt állapotgép.
	 * Mivel egyetlen előre meghatározott állapotgép alkotja az alkalmazást,
	 * ezért ezt egy statikus változóban tároljuk.
	 */
	public static StateManager SOKOBAN;
	
	/**
	 * Az állapotgép állapotai.
	 * @see State
	 */
	private ArrayList<State> states = new ArrayList<State>();
	
	/**
	 * az aktuális állapot sorszáma az állapotok listájában.
	 */
	private int actual;
	
	/**
	 * Konstruktor, ahol megadhatunk tetszőleges számú állapotot paraméterként.
	 * @param states Az állapotgép állapotai.
	 */
	public StateManager(State... states) {
		for(State s : states)
			this.states.add(s);
		actual = 0;
		this.states.get(actual).initializeState();
	}
	
	/**
	 * Az állapotgép léptetését megvalósító metódus.
	 */
	public void nextState() {
		actual++;
		if(actual >= states.size())
			actual = 0;
		this.states.get(actual).initializeState();
	}
	
	/**
	 * A pillanatnyi állapot szerinti parancs leadására szolgáló metódus.
	 * @param cmd Az elvégzendő parancs.
	 */
	public void protoExecute(String cmd) {
		states.get(actual).protoExecute(cmd);
	}
	
	/**
	 * A pillanatnyi állapot sajátos parancsait lekérdező metódus.
	 * @return A pillanatnyi állapot saját parancsai
	 */
	public ArrayList<String> getDictionary() {
		return states.get(actual).getDictionary();
	}
	
	/**
	 * A pillanatnyi állapot nevét lekérdező metódus.
	 * @return Az elérhető állapotok.
	 */
	public String getActualStateName() {
		return states.get(actual).getName();
	}
	
	/**
	 * A pillanatnyi állapot kimenetét kiírató metódus.
	 */
	public void protoOut() {
		states.get(actual).protoOut();
	}
	
	/**
	 * A billentyű lenyomásért felelő metódus.
	 * @param key a billentyű kódja.
	 */
	public void keyPressed(int key) {
		states.get(actual).keyPressed(key);
	}
	
	/**
	 * Meghívja az aktuális állapot kirajzoló metódusát.
	 * @param g A grafikus felület, amire rajzolunk.
	 * @param p A panel, amelyre rajzolunk.
	 * @param x A rajzolandó kép bal szélének x koordinátája.
	 * @param y A rajzolandó kép felső szélének y koordinátája.
	 * @param w A rajzolandó kép szélessége.
	 * @param h A rajzolandó kép magassága.
	 */
	public void paint(Graphics2D g, JPanel p, double x, double y, double w, double h) {
		states.get(actual).paint(g, p, x, y, w, h);
	}
	
}
