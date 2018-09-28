package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JPanel;

import gameobject.Player;
import gui.GamePanel;
import proto.Prototype;

/**
 * A játék vége állapot. Innen vissza lehet lépni a menübe.
 *
 */
public class StateGameAfter extends State {
	
	/**
	 * A játék végén leadható parancsok listája.
	 * Tulajdonképpen csak egy 'back' parancs adható le, ami visszalépteti a felhasználót a menübe.
	 */
	private static final ArrayList<String> GAMEENDPROTODICTIONARY = new ArrayList<String>(Arrays.asList(
			"back" ));

	/**
	 * @see State
	 */
	@Override
	public ArrayList<String> getDictionary() {
		return GAMEENDPROTODICTIONARY;
	}

	/**
	 * A játék vége utáni állapot neve.
	 * @see State
	 * @return "gameend"
	 */
	@Override
	public String getName() {
		return "gameend";
	}
	
	/**
	 * A bejeződött játék utáni állapotban nincsenek inicializálandó dolgok,
	 * így ez a metódus nem csinál semmit.
	 * @see State
	 */
	@Override
	public void initializeState() {
		
	}

	/**
	 * Visszaléptethető az alkalmazás a menü állapotba.
	 * @see State
	 */
	@Override
	public void protoExecute(String cmd) {
		switch(cmd) {
		case "back": StateManager.SOKOBAN.nextState();
		}
	}

	/**
	 * Kiírja a játék végső állapotát.
	 * @see State
	 */
	@Override
	public void protoOut() {
		for(Player p : Game.SOKOBANGAME.getPlayers()) {
			String s = p.protoInfo() + ": " + p.getPoints();
			if(p == Game.SOKOBANGAME.getWinner())
				s += " WINNER";
			if(!p.isAlive())
				s += " DEAD";
			System.out.println(s);
		}
		Prototype.printFields();
	}

	/**
	 * Billentyűlenyomásért felelős metódus, kihasználja a prototípus implemetációját.
	 * Minden gomra reagál.
	 */
	@Override
	public void keyPressed(int key) {
		protoExecute("back");
	}

	/**
	 * Kiírja a győztest.
	 */
	@Override
	public void paint(Graphics2D g, JPanel p, double x, double y, double w, double h) {
		g.setFont(new Font("Courier", Font.BOLD, (int) Math.round(w / 10)));
		String s;
		if(Game.SOKOBANGAME.getPlayers().get(0) == Game.SOKOBANGAME.getWinner()) {
			g.setColor(Color.BLUE);
			s = "PLAYER1 WON!";
		} else {
			g.setColor(Color.ORANGE);
			s = "PLAYER2 WON!";
		}
		g.drawString(s, (int)(x+(w-g.getFontMetrics().stringWidth(s))/2), (int)(y+h/10));
		
		GamePanel.paintFields(g, p, x, y + 0.2*h, w / Game.SOKOBANGAME.getFields().length);
	}

}
