package game;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Dimension;
import java.awt.event.KeyEvent;

import gameobject.*;
import gui.GameImage;
import skeleton.Skeleton;
/**
 * A játékot reprezentáló osztály.
 */
public class Game {
	
	/**
	 * Az alkalmazás által használt játék referencia.
	 * A Sokoban alkalmazásban csak egyetlen Game típusú referencia van.
	 */
	public static Game SOKOBANGAME;
	/**
	 * A pálya randomizálhatóságát tároló változó.
	 */
	public static boolean RANDOM = false;
	/**
	 * A pályát egy 2 dimenziós tömben tárolt mezők alkotják.
	 */
	private Field[][] fields;
	/**
	 * A játék osztály tárolja a dobozokat.
	 */
	private ArrayList<Box> boxes = new ArrayList<Box>();
	/**
	 * A játék osztály tárolja a játékosokat (kettőt).
	 */
	private ArrayList<Player> players = new ArrayList<Player>();
	/**
	 * Tárolja az utoljára lépett játékost.
	 */
	private Player lastSteppedPlayer;
	
	/**
	 * Visszaadja a játék-beli mezőket.
	 * @return A mezők.
	 */
	public Field[][] getFields() {
		return fields;
	}
	
	/**
	 * Visszaadja a játékosokat.
	 * @return A játékosok referenciái.
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	/**
	 * Ellenőrzi, hogy vége van-e a játéknak.
	 * @return True, ha vége van a játéknak, különben false.
	 */
	public boolean isOver() {
		for(Player p : players) {
			if(!p.isAlive())
				return true;
		}
		for(Box b : boxes) {
			if(b.isAlive())
				if(b.isMovable())
					return false;
		}
		return true;
	}
	
	/**
	 * Növeli az utoljára lépett játékos pontszámát.
	 * @param inc A növelés mértéke.
	 */
	public void givePointsToLastPlayer(int inc) {
		Skeleton.printStart(this, "givePoints.ToLastPlayer(inc: " + inc + ")");
		lastSteppedPlayer.increasePointsBy(inc);
		Skeleton.printReturn("void");
	}
	
	/**
	 * A játék bemenete.
	 * @param key A lenyomott billentyű kódja.
	 * @return True, ha történt lépés, különben false.
	 */
	public boolean execute(int key) {
		switch(key) {
		case KeyEvent.VK_UP:
			lastSteppedPlayer = players.get(0);
			return players.get(0).goInDirection(Direction.Up);
		case KeyEvent.VK_DOWN: 
			lastSteppedPlayer = players.get(0);
			return players.get(0).goInDirection(Direction.Down);
		case KeyEvent.VK_RIGHT:
			lastSteppedPlayer = players.get(0);
			return players.get(0).goInDirection(Direction.Right);
		case KeyEvent.VK_LEFT: 
			lastSteppedPlayer = players.get(0);
			return players.get(0).goInDirection(Direction.Left);
		case KeyEvent.VK_W:
			lastSteppedPlayer = players.get(1);
			return players.get(1).goInDirection(Direction.Up);
		case KeyEvent.VK_S:
			lastSteppedPlayer = players.get(1);
			return players.get(1).goInDirection(Direction.Down);
		case KeyEvent.VK_D:
			lastSteppedPlayer = players.get(1);
			return players.get(1).goInDirection(Direction.Right);
		case KeyEvent.VK_A:
			lastSteppedPlayer = players.get(1);
			return players.get(1).goInDirection(Direction.Left);
		}
		return false;
	}
	
	/**
	 * Visszaadja a nyerésre álló játékost.
	 * @return nyerésre álló játékos
	 */
	public Player getWinner() {
		if(!players.get(1).isAlive())
			return players.get(0);
		if(!players.get(0).isAlive())
			return players.get(1);
		if(players.get(1).getPoints() > players.get(0).getPoints())
			return players.get(1);
		if(players.get(0).getPoints() > players.get(1).getPoints())
			return players.get(0);
		return null;
	}
	
	/**
	 * Skeletonhoz használt mini pálya-építő.
	 * @param p Lépő játékos.
	 * @param s A forgatókönyv leírása.
	 * @return A skeleton játék.
	 */
	public static Game buildSkeleton(Player p, String s) {
		Game game = new Game();
		game.lastSteppedPlayer = p;
		ArrayList<Field> fields = new ArrayList<Field>();
    	fields.add(new Floor());
    	p.moveToField(fields.get(0));
    	for(int i = 1; i < s.length(); i++) {
    		switch(s.charAt(i)) {
    			case 'P':	addField(fields, new Floor());
    						(new Player(160, GameImage.PLAYER2_IMG)).moveToField(fields.get(i));
    						break;
    			case 'B':	addField(fields, new Floor());
    						Box b = new Box();
    						b.moveToField(fields.get(i));
    						game.boxes.add(b);
    						break;
    			case 'W':	addField(fields, new Wall());
    						break;
    			case 'F':	addField(fields, new Floor());
    						break;
    			case 'T':	addField(fields, new Target(game));
    						break;
    			case 'G':	addField(fields, new Button(new Hole()));
    						break;
    			case 'O':	Hole h = new Hole();
    						h.setOpened(true); 
    						addField(fields, h);
    						break;
    			case 'C':	addField(fields, new Hole());
							break;
				default:	System.out.println(s + ": Hiba a bemenetben! rossz bet� ezen a helyen: " + i);
							return null;
    		}
    	}
    	return game;
	}
	
	/**
	 * Hozzáad a skeleton pályához egy újabb mezőt.
	 * Kizárólag a Game.buildSkeleton metódus használja a pályaépítéshez.
	 * @param fields Az eddigi mezők
	 * @param f Az új mező
	 */
	private static void addField(ArrayList<Field> fields, Field f) {
    	Field f0 = fields.get(fields.size()-1);
		fields.add(f);
		f0.setNeighbour(f, Direction.Right);
		f.setNeighbour(f0, Direction.Left);
    }
	
	/**
	 * Véletlenszerű pályageneráló függvény.
	 * @param n A pálya mérete, azaz a pálya blokkmagassága és blokkszélessége is egyben.
	 * @return A generált pálya.
	 */
	private static Game randomGame(int n) {
		Game game = new Game();
		game.fields = new Field[n][];
		for(int i = 0; i < n; i++)
			game.fields[i] = new Field[n];
		//side walls
		for(int i = 0; i < n; i++) {
			game.fields[0][i] = new Wall();
			game.fields[n-1][i] = new Wall();
			if( i != 0 & i != n-1) {
				game.fields[i][0] = new Wall();
				game.fields[i][n-1] = new Wall();
			}
		}
		//inner walls, target fields, boxes, holes, buttons
		ArrayList<Dimension> list = new ArrayList<Dimension>();
		list.add(new Dimension(1, 1));
		list.add(new Dimension(n-2, n-2));
		Dimension d;
		//inner walls
		for(int i = 0; i < n*n*6/49; i++) {
			d = randOffList(1, n-1, 1, n-1, list);
			game.fields[d.height][d.width] = new Wall();
		}
		//target fields and boxes
		for(int i = 0; i < n*n*8/49; i++) {
			d = randOffList(1, n-1, 1, n-1, list);
			game.fields[d.height][d.width] = new Target(game);
			d = randOffList(1, n-1, 1, n-1, list);
			Box b = new Box();
			game.boxes.add(b);
			b.moveToField(game.fields[d.height][d.width] = new Floor());
		}
		//holes and buttons
		for(int i = 0; i < n*n*2/49; i++) {
			d = randOffList(1, n-1, 1, n-1, list);
			Hole h = new Hole();
			game.fields[d.height][d.width] = h;
			d = randOffList(1, n-1, 1, n-1, list);
			game.fields[d.height][d.width] = new Button(h);
		}
		//fill the rest with floor
		for(int i = 0; i < n; i++)
			for(int j = 0; j < n; j++)
				if(game.fields[i][j] == null)
					game.fields[i][j] = new Floor();
		//build connections between fields
		buildFieldConnections(game.fields);
		return game;
	}
	
	/**
	 * Kétdimenziós tömbben tárolt Field objektumokat összeköt, azaz beállítja a szomszéd referenciákat.
	 * @param fields A mezők.
	 */
	private static void buildFieldConnections(Field[][] fields) {
		int n = fields.length;
		for(int i = 0; i < n; i++)
			for(int j = 0; j < n; j++) {
				if(i != 0)
					fields[i][j].setNeighbour(fields[i-1][j], Direction.Up);
				if(i != n-1)
					fields[i][j].setNeighbour(fields[i+1][j], Direction.Down);
				if(j != n-1)
					fields[i][j].setNeighbour(fields[i][j+1], Direction.Right);
				if(j != 0)
					fields[i][j].setNeighbour(fields[i][j-1], Direction.Left);
			}
	}
	
	/**
	 * A véletlen pálya generálásához használt függvény.
	 * Azért van rá szükség, mert úgy generáljuk a pályát, hogy veszünk egy véletlen, nem foglalt
	 * koordinátát, és arra a helyre rakjuk a Field objektumot, esetlegesen rá egy Movable-t is.
	 * @param x1 A kigenerálandó hely oszlopszámának minimuma.
	 * @param x2 A kigenerálandó hely oszlopszámának maximuma.
	 * @param y1 A kigenerálandó hely sorszámának minimuma.
	 * @param y2 A kigenerálandó hely sorszámának maximuma.
	 * @param list A lista, amelyben elvannak tárolva a foglalt helyek.
	 * @return A kigenerált hely.
	 */
	private static Dimension randOffList(int x1, int x2, int y1, int y2, ArrayList<Dimension> list) {
		Random r = new Random();
		Dimension result = new Dimension(r.nextInt(x2 - x1) + x1, r.nextInt(y2 - y1) + y1);
		while(dimensionReserved(result, list))
			result = new Dimension(r.nextInt(x2 - x1) + x1, r.nextInt(y2 - y1) + y1);
		list.add(result);
		return result;
	}
	
	/**
	 * Megnézi, hogy egy adott koordináta/hely benne van-e egy listában, tehát le van-e foglalva.
	 * @param d1 Az adott hely.
	 * @param list A foglalt helyek listája.
	 * @return True, ha foglalt, különben false.
	 */
	private static boolean dimensionReserved(Dimension d1, ArrayList<Dimension> list) {
		for(Dimension d2 : list)
			if(d1.height == d2.height & d1.width == d2.width)
				return true;
		return false;
	}
	
	/**
	 * A prototípushoz használt determinizált pályageneráló.
	 * @param n A pálya sor és oszlopszáma.
	 * @return A generált pálya.
	 */
	private static Game protoGame(int n) {
		Game game = new Game();
		game.fields = new Field[n][];
		for(int i = 0; i < n; i++)
			game.fields[i] = new Field[n];
		//side walls
		for(int i = 0; i < n; i++) {
			game.fields[0][i] = new Wall();
			game.fields[n-1][i] = new Wall();
			if( i != 0 & i != n-1) {
				game.fields[i][0] = new Wall();
				game.fields[i][n-1] = new Wall();
			}
		}
		//fill with floor
		for(int i = 1; i < n-1; i++)
			for(int j = 1; j < n-1; j++)
				game.fields[i][j] = new Floor();
		//some target field and box
		for(int j = 1; j < 5; j++) {
			Box b = new Box();
			game.boxes.add(b);
			b.moveToField(game.fields[4][j]);
			game.fields[6][j] = new Target(game);
		}
		//some button and hole
		for(int j = 5; j < 8; j++) {
			Hole h = new Hole();
			game.fields[6][j] = h; 
			game.fields[4][j] = new Button(h);
		}
		//build connections between fields
		buildFieldConnections(game.fields);
		return game;
	}
	
	/**
	 * A pálya generálásáért felelős metódus.
	 * Determinisztikusan és véletlenszerűen is tud generálni.
	 * Attól függ, hogy a mi a RANDOM változó értéke.
	 * @param n pálya mérete.
	 * @see RANDOM
	 */
	public static void genGame(int n) {
		if(RANDOM)
			Game.SOKOBANGAME = Game.randomGame(n);
		else
			Game.SOKOBANGAME = Game.protoGame(n);
	}
	
}
