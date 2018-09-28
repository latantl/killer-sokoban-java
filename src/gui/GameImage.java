package gui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.io.File;
import javax.swing.JPanel;

/**
 * A játék grafikus változatában megjelenő képeket reprezentáló osztály.
 */
public class GameImage {

	private static String sep = File.separator;
	/**
	 * Doboz képe.
	 */
	public static final GameImage BOX_IMG =
			new GameImage(Toolkit.getDefaultToolkit().getImage("img" + sep + "box.png"));
	/**
	 * Padló mező képe.
	 */
	public static final GameImage FLOOR_IMG =
			new GameImage(Toolkit.getDefaultToolkit().getImage("img" + sep + "floor.png"));
	/**
	 * Fal mező képe.
	 */
	public static final GameImage WALL_IMG =
			new GameImage(Toolkit.getDefaultToolkit().getImage("img" + sep + "wall.png"));
	/**
	 * Cél mező képe.
	 */
	public static final GameImage TARGETZONE_IMG =
			new GameImage(Toolkit.getDefaultToolkit().getImage("img" + sep + "target.png"));
	/**
	 * Gomb mező képe.
	 */
	public static final GameImage BUTTON_IMG =
			new GameImage(Toolkit.getDefaultToolkit().getImage("img" + sep + "button.png"));
	/**
	 * Lyuk mező képe.
	 */
	public static final GameImage HOLE_IMG =
			new GameImage(Toolkit.getDefaultToolkit().getImage("img" + sep + "hole.png"));
	/**
	 * Első játékos képe.
	 */
	public static final GameImage PLAYER1_IMG =
			new GameImage(Toolkit.getDefaultToolkit().getImage("img" + sep + "p1.png"));
	/**
	 * Második játékos képe.
	 */
	public static final GameImage PLAYER2_IMG =
			new GameImage(Toolkit.getDefaultToolkit().getImage("img" + sep + "p2.png"));
	/**
	 * A méz képe.
	 */
	public static final GameImage HONEY_IMG =
			new GameImage(Toolkit.getDefaultToolkit().getImage("img" + sep + "honey.png"));
	/**
	 * Az olaj képe.
	 */
	public static final GameImage OIL_IMG =
			new GameImage(Toolkit.getDefaultToolkit().getImage("img" + sep + "oil.png"));
	/**
	 * Az eltárolt kép.
	 */
	private Image image;
	
	/**
	 * Konstruktor, amely csak egy képet vár.
	 * @param image A kép.
	 */
	public GameImage(Image image) {
		this.image = image;
	}
	
	/**
	 * Rajzoló metódus. Adott helyre, adott grafikus felületre és panelra, adott méretben és adott
	 * elforgatással kirajzolja a képet.
	 * @param g Grafikus felület
	 * @param p A panel, amire rajzolunk.
	 * @param x A kép x koordinátája a grafikus felületen
	 * @param y A kép y koordinátája a grafikus felületen
	 * @param blocksize A kép mérete.
	 * @param rotation A kép elforgatása az eredetihez képest.
	 */
	public void draw(Graphics2D g, JPanel p, double x, double y, double blocksize, double rotation) {
        AffineTransform trans1 = new AffineTransform();
        trans1.rotate(Math.toRadians(rotation), x + blocksize / 2, y + blocksize / 2);
        trans1.translate(x, y);
        double scale = blocksize / ((double)image.getHeight(p));
        trans1.scale(scale, scale);
        g.drawImage(image, trans1, p);
	}
	
}
