package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import game.Game;
import game.StateGame;
import game.StateGameAfter;
import game.StateGameBefore;
import game.StateManager;
import gameobject.Field;

/**
 * A grafikus megjelenítésért felelős osztály, amel a JPanel leszármazottja és kezeli a billentyű lenyomásokat.
 */
public class GamePanel extends JPanel implements KeyListener {
	private static final long serialVersionUID = 1L;

	private JFrame frame;
	
	private GamePanel(JFrame frame) {
		this.frame = frame;
		frame.addKeyListener(this);
		this.setOpaque(false);
		this.setBackground(Color.BLACK);
	}
	/**
	 * Billentyű nyomás - és elengedés metódus.
	 */
	@Override
	public void keyTyped(KeyEvent arg0) { }

	/**
	 * Billentyű elengedése metódus, nem csinál semmit.
	 */
	@Override
	public void keyReleased(KeyEvent arg0) { }

	/**
	 * Billentyű lenyomva metódus.  Nem csinál semmit.
	 * Továbbítja a megnyomott billentyű információját az állapotgépnek, majd frissíti a képernyőt.
	 */
	@Override
	public void keyPressed(KeyEvent arg0) {
		StateManager.SOKOBAN.keyPressed(arg0.getKeyCode());
		this.repaint();
		frame.invalidate();
		frame.validate();
		frame.repaint();
	}

	/**
	 * A kirajzoló metódus, továbbhívja az állapotgépet.
	 */
	@Override
	public void paint(Graphics g) {
		double h = this.getHeight();
		double w = this.getWidth();
		if(h * 0.8 > w) {
			h = w * 1.25;
		} else {
			w = h * 0.8;
		}
		double x = (this.getWidth() - w) / 2;
		double y = (this.getHeight() - h) / 2;
		g.setColor(Color.DARK_GRAY);
		Graphics2D g2 = (Graphics2D) g; 
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		StateManager.SOKOBAN.paint(g2, this, x, y, w, h);
	}
	
	/**
	 * A pályát kirajzoló metódus.
	 * @param g A grafikus felület, amelyre rajzoljuk.
	 * @param p A panel, amelyre rajzoljuk.
	 * @param x A pálya bal felső sarkának x koordinátája.
	 * @param y A pálya bal felső sarkának y koordinátája.
	 * @param size A fieldek szélességének és magasságának hossza pixelben.
	 */
	public static void paintFields(Graphics2D g, JPanel p, double x, double y, double size) {
		Field[][] fields = Game.SOKOBANGAME.getFields();
		for(int i = 0; i < fields.length; i++) {
			for(int j = 0; j < fields[i].length; j++) {
				fields[i][j].paintImageArray(g, p, x + j*size, y + i*size, size);
			}
		}
	}
	
	/**
	 * A grafikus verzió főprogramja.
	 */
	public static void main(String[] args) {
		Game.RANDOM = true;
		StateManager.SOKOBAN = new StateManager(new StateGameBefore(), new StateGame(), new StateGameAfter());
		JFrame frame = new JFrame("Killer Sokoban");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(400,520));
		frame.setMinimumSize(new Dimension(400, 520));
    	frame.setBackground(Color.BLACK);
    	//frame.setIconImage(SokobanImage.HOLE_IMG);
    	frame.add(new GamePanel(frame));
    	frame.setResizable(true);
        frame.setLocation(400, 100);
        frame.pack();
        frame.setVisible(true);
	}
	
}
