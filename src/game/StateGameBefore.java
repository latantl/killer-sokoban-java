package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JPanel;

import gui.GamePanel;
import proto.Prototype;

/**
 * A játék előtti pályaválasztó állapot.
 */
public class StateGameBefore extends State {
	
	/**
	 * A pályaválasztó állapot specifikus parancsai.
	 */
	private static final ArrayList<String> MENUDICTIONARY = new ArrayList<String>(Arrays.asList(
			"other","bigger","smaller","start" ));
	
	/**
	 * Lehetséges pályaméretek tömbje.
	 */
	private int[] mapSizes = { 16, 20, 32 };
	
	/**
	 * A pillanatnyilag kiválasztott pályaméret sorszáma a tömbben.
	 * @see mapSizes
	 */
	private int sizeIndex = 0;

	/**
	 * @see State
	 */
	@Override
	public ArrayList<String> getDictionary() {
		return MENUDICTIONARY;
	}

	/**
	 * A menü állapotának neve.
	 * @return "menu"
	 * @see State
	 */
	@Override
	public String getName() {
		return "menu";
	}
	
	/**
	 * generál egy pályát.
	 * @see State
	 */
	@Override
	public void initializeState() {
		Game.genGame(mapSizes[sizeIndex]);
	}

	/**
	 * A pálya generálása és méretének változtatása lehetséges ebben az állapotban.
	 * Ezeket tudja végrehajtani ez a metódus.
	 * @see State
	 */
	@Override
	public void protoExecute(String cmd) {
		switch(cmd) {
		case "other": Game.genGame(mapSizes[sizeIndex]); break;
		case "bigger":
			if(sizeIndex < mapSizes.length - 1) {
				sizeIndex++;
				Game.genGame(mapSizes[sizeIndex]);
			}
			break;
		case "smaller":
			if(sizeIndex > 0) {
				sizeIndex--;
				Game.genGame(mapSizes[sizeIndex]);
			}
			break;
		case "start": StateManager.SOKOBAN.nextState();
		}
	}

	/**
	 * Az utoljára generált pályát rajzolja ki.
	 * @see State
	 */
	@Override
	public void protoOut() {
		Prototype.printFields();
	}

	/**
	 * Billentyűlenyomásért felelős metódus, kihasználja a prototípus implemetációját.
	 * A gombok, amelykre reagál: ENTER, UP, DOWN, SPACE.
	 */
	@Override
	public void keyPressed(int key) {
		switch(key) {
		case KeyEvent.VK_ENTER: protoExecute("start"); break;
		case KeyEvent.VK_UP: protoExecute("bigger"); break;
		case KeyEvent.VK_DOWN: protoExecute("smaller"); break;
		case KeyEvent.VK_SPACE: protoExecute("other"); break;
		}
	}

	/**
	 * Kiírja a billentyűk használatát.
	 */
	@Override
	public void paint(Graphics2D g, JPanel p, double x, double y, double w, double h) {
		g.setFont(new Font("Courier", Font.BOLD, (int) Math.round(w / 10)));
		g.setColor(Color.WHITE);
		String s = "KILLER SOKOBAN";
		g.drawString(s, (int)(x+(w-g.getFontMetrics().stringWidth(s))/2), (int)(y+h/12));
		
		int r1 = g.getFontMetrics().getHeight();
		g.setFont(new Font("Courier", Font.BOLD, (int) Math.round(w / 35)));
		s = "P1: ARROWS, K, L;    P2: W, A, S, D, 0, 1";
		g.drawString(s, (int)(x+(w-g.getFontMetrics().stringWidth(s))/2), (int)(y+h/12+r1/2));
		
		
		s = "SPACE: new map;  UP: bigger;  DOWN: smaller;  ENTER: start";
		g.drawString(s, (int)(x+(w-g.getFontMetrics().stringWidth(s))/2), (int)(y+h/12+r1));
		
		GamePanel.paintFields(g, p, x, y + 0.2*h, w / mapSizes[sizeIndex]);
	}

}
