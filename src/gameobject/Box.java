package gameobject;

import gui.GameImage;
import skeleton.Skeleton;

/**
 * A dobozokat reprezentáló osztály.
 * @see Movable
*/
public class Box extends Movable {  
	/** 
	 * A doboz súlya 80.
	 */
	private static final int BOX_WEIGHT = 80; 
	/**
	 * Konstruktor.
	 */
	public Box() {
		super(BOX_WEIGHT);
	}
	/**
	 * Megnézi, hogy a doboz mozgatható-e.
	 * @return True, ha mozgatható.
	 */
	public boolean isMovable() {
		Skeleton.printStart(this, "isMovable()");
		boolean result = (this.isPushable(Direction.Left) & this.isPushable(Direction.Right)) | (this.isPushable(Direction.Up) & this.isPushable(Direction.Down));
		Skeleton.printReturn(result);
		return result;
	}
	/**
	 * Movable osztálytól örökölt absztrakt metódus.
	 * A doboz célmezőre lép.
	 * @param t A célmező változója.
	 * A  t reachedBy() függvényét hívja meg.
	 * @see Movable
	 */
	@Override
	public void moveToTarget(Target t) {
		Skeleton.printStart(this, "moveToTarget(" + t + ")");
		t.reachedBy(this);
		Skeleton.printReturn("void");
	}
	/**
	 * Movable osztálytól örökölt absztrakt metódus.
	 * Visszaadja, hogy a doboz adott irányban tolható-e vagy sem.
	 * @param d Az irányt adja meg.
	 * @return True, ha tolható.
	 * @see Movable
	 */
	@Override
	public boolean isPushable(Direction d) {
		Skeleton.printStart(this, "isPushable(" + d + ")");
		boolean result = field.getNeighbour(d).canPlaceHere(d);
		Skeleton.printReturn(result);
		return result;
	}
	
	/**
	 * @see GameObject
	 */
	@Override
	public String protoInfo() {
		return "b";
	}
	
	/**
	 * Visszaadja a képet a dobozról.
	 * @see GameObject
	 */
	@Override
	public GameImage getImage() {
		return GameImage.BOX_IMG;
	}
}
