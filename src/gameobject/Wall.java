package gameobject;

import gui.GameImage;
import skeleton.Skeleton;

/**
 * A falakat reprezentáló osztály.
 *@see Field
 */
public class Wall extends Field {

	/**
	 * Mozgatható objektum lép a gombra.
	 * @param m A mozgatható objektum.
	 * @param d A lépés iránya.
	 * @return False, mert a falra soha nem lehet rálépni.
	 */
	@Override
	public boolean placeMovableOnThis(Movable m, Direction d, int power) {
		Skeleton.printStart(this, "placeMovableOnThis( " + m + ", "+ d + ", power: " + power + ")");
		Skeleton.printReturn(false);
		return false;
	}

	/**
	 * @see Field
	 */
	@Override
	public String protoFieldInfo() {
		return "W";
	}
	
	/**
	 * A többi mezővel ellentétben ide semmiféleképpen sem lehet ládát tolni.
	 * @see Field
	 */
	@Override
	public boolean canPlaceHere(Direction d) {
		return false;
	}

	/**
	 * Visszaadja a fal mező képét.
	 */
	@Override
	public GameImage getImage() {
		return GameImage.WALL_IMG;
	}
	
}