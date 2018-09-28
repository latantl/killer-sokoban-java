package gameobject;

import game.Game;
import gui.GameImage;
import skeleton.Skeleton;

/**
 * Célmezőt reprezentáló osztály.
 */
public class Target extends Field {
	
	/**
	 * A célmező állapota. A mező lehet elért és nem elért állapotban.
	 */
	private boolean isReached;
	
	/**
	 * Game referencia. Azért van rá szükség, mert ez hívja meg rajta a pontadó metódust.
	 */
	Game game;
	
	/**
	 * Konstruktor.
	 * @param game A Game objektum, amelynek jelezni kell, ha pontot kell kapnia egy játékosnak.
	 */
	public Target(Game game) {
		isReached = false;
		this.game = game;
	}
	
	/**
	 * Ha egy doboz eléri a mezőt, átállítja az isReached értékét.
	 * @param b A mezõt elérõ doboz.
	 */
	public void reachedBy(Box b) {
		Skeleton.printStart(this, "reachedBy(b: " + b + ")");
		b.die();
		game.givePointsToLastPlayer(1);
		isReached = true;
		Skeleton.printReturn("void");
	}
	/**
	 * Mozgatható objektum rá tud-e lépni a megadott mezőre adott irányban.
	 * @param m A mozgatható objektum.
	 * @param d Az iránya.
	 * @return True, ha az objektumot magára tudja léptetni a mező.
	 * @return False, ha nem tud rálépni az objektum.
	 */
	@Override
	public boolean placeMovableOnThis(Movable m, Direction d, int power) {
		Skeleton.printStart(this, "placeMovableOnThis( " + m + ", "+ d + ", power: " + power + ")");
		if(movable == null) {
			if(isReached) {
				m.moveToField(this);
			} else {
				m.moveToTarget(this);
			}
			Skeleton.printReturn(true);
			return true;
		} else if(movable.push(m, d, power)) {
			if(isReached) {
				m.moveToField(this);
			} else {
				m.moveToTarget(this);
			}
			Skeleton.printReturn(true);
			return true;
		} else {
			Skeleton.printReturn(false);
			return false;
		}
	}

	/**
	 * @see Field
	 */
	@Override
	public String protoFieldInfo() {
		String s = "T";
		if(isReached)
			s += "-";
		else
			s += "+";
		return s;
	}

	/**
	 * Visszaadja a cél mező képét.
	 */
	@Override
	public GameImage getImage() {
		if(isReached)
			return GameImage.FLOOR_IMG;
		else
			return GameImage.TARGETZONE_IMG;
	}
}
