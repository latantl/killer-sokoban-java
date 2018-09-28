package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JPanel;

import gameobject.*;
import gui.GameImage;
import gui.GamePanel;
import proto.Prototype;

/**
 * Az állapot, melyben folyik a játék két játékos között.
 * @see State
 */
public class StateGame extends State {
	
	/**
	 * A játék parancsainak listája
	 */
	private static final ArrayList<String> GAMEPROTODICTIONARY = new ArrayList<String>(Arrays.asList(
			"p+","p-","q+","q-","pu","pd","qu","qd","pr","pl","qr","ql" ));

	/**
	 * Az első játékos.
	 */
	private Player p;
	/**
	 * A második játékos.
	 */
	private Player q;
	
	/**
	 * @see State
	 */
	@Override
	public ArrayList<String> getDictionary() {
		return GAMEPROTODICTIONARY;
	}

	/**
	 * A játék állapotának neve a prototípusban.
	 * @see State
	 * @return "game"
	 */
	@Override
	public String getName() {
		return "game";
	}
	
	/**
	 * Létrehozza a két játékost, elhelyezi őket a pályán.
	 * @see State
	 */
	@Override
	public void initializeState() {
		ArrayList<Player> players = Game.SOKOBANGAME.getPlayers();
		Field[][] fields = Game.SOKOBANGAME.getFields();
		int n = fields.length;
		(p = new Player(160, GameImage.PLAYER1_IMG)).moveToField(fields[1][1]);
		p.setProtoName("p");
		players.add(p);
		(q = new Player(160, GameImage.PLAYER2_IMG)).moveToField(fields[n-2][n-2]);
		q.setProtoName("q");
		players.add(q);
	}

	/**
	 * A játékosok irányítását teszi lehetővé.
	 * @see State
	 */
	@Override
	public void protoExecute(String cmd) {
		switch(cmd) {
		case "p+": p.frictionUp(); break;
		case "p-": p.frictionDown(); break;
		case "pu": Game.SOKOBANGAME.execute(KeyEvent.VK_UP); break;
		case "pd": Game.SOKOBANGAME.execute(KeyEvent.VK_DOWN); break;
		case "pr": Game.SOKOBANGAME.execute(KeyEvent.VK_RIGHT); break;
		case "pl": Game.SOKOBANGAME.execute(KeyEvent.VK_LEFT); break;
		case "q+": q.frictionUp(); break;
		case "q-": q.frictionDown(); break;
		case "qu": Game.SOKOBANGAME.execute(KeyEvent.VK_W); break;
		case "qd": Game.SOKOBANGAME.execute(KeyEvent.VK_S); break;
		case "qr": Game.SOKOBANGAME.execute(KeyEvent.VK_D); break;
		case "ql": Game.SOKOBANGAME.execute(KeyEvent.VK_A); break;
		}
		if(Game.SOKOBANGAME.isOver())
			StateManager.SOKOBAN.nextState();
	}

	/**
	 * Kirajzolja a pályát a pillanatnyi állapotában a szabványos kimenetre.
	 * @see State
	 */
	@Override
	public void protoOut() {
		for(Player p : Game.SOKOBANGAME.getPlayers()) {
			System.out.println(p.protoInfo() + ": " + p.getPoints());
		}
		Prototype.printFields();
	}

	/**
	 * Billentyűlenyomásért felelős metódus, kihasználja a prototípus implemetációját.
	 * A gombok, amelykre reagál: UP, DOWN, RIGHT, LEFT, K, L, W, A, S, D, 0, 1
	 */
	@Override
	public void keyPressed(int key) {
		switch(key) {
		case KeyEvent.VK_UP: protoExecute("qu"); break;
		case KeyEvent.VK_DOWN: protoExecute("qd"); break;
		case KeyEvent.VK_RIGHT: protoExecute("qr"); break;
		case KeyEvent.VK_LEFT: protoExecute("ql"); break;
		case KeyEvent.VK_K: protoExecute("q-"); break;
		case KeyEvent.VK_L: protoExecute("q+"); break;
		case KeyEvent.VK_W: protoExecute("pu"); break;
		case KeyEvent.VK_S: protoExecute("pd"); break;
		case KeyEvent.VK_D: protoExecute("pr"); break;
		case KeyEvent.VK_A: protoExecute("pl"); break;
		case KeyEvent.VK_0: protoExecute("p-"); break;
		case KeyEvent.VK_1: protoExecute("p+"); break;
		}
	}

	/**
	 * Kiírja a játékosok pontszámát.
	 */
	@Override
	public void paint(Graphics2D g, JPanel p, double x, double y, double w, double h) {
		g.setFont(new Font("Courier", Font.BOLD, (int) Math.round(w / 20)));
		g.setColor(Color.WHITE);
		String s = "PLAYER1: " + this.p.getPoints();
		g.drawString(s, (int) x+10, (int)(y+h/20));
		s = "PLAYER2: " + this.q.getPoints();
		g.drawString(s, (int) x+10, (int)(y+h/10));
		
		GamePanel.paintFields(g, p, x, y + 0.2*h, w / Game.SOKOBANGAME.getFields().length);
	}
	
}
