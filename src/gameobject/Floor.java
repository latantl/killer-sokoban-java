package gameobject;

import gui.GameImage;
import skeleton.Skeleton;

/**
 * Az üres mezőket reprezentáló osztály.
 *@see Field
 */
public class Floor extends Field {
	/**
	 * Mozgatható objektum lép a mezőre.
	 * @param m A mozgatható objektum.
	 * @param d A lépés iránya.
	 * @return True, ha nincs rajta objektum.
	 * Ekkor magáralépteti az objektumot.
	 * @return True, ha eltolja a rajtalévő mozgathatót és az új objektum lép rá.
	 * @return False, ha nem tud rálépni az objektum.
	 */
	@Override
	public boolean placeMovableOnThis(Movable m, Direction d, int power) {
		Skeleton.printStart(this, "placeMovableOnThis( " + m + ", "+ d + ", power: " + power + ")");
		if(movable == null) {
			m.moveToField(this);
			Skeleton.printReturn(true);
			return true;
		} else if(movable.push(m, d, power)) {
			m.moveToField(this);
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
		return "F";
	}

	/**
	 * Visszaadja a padló mező képét.
	 */
	@Override
	public GameImage getImage() {
		return GameImage.FLOOR_IMG;
	}
}
