package gameobject;

import gui.GameImage;
import skeleton.Skeleton;

/**
 * A nyomógombokat reprezentáló osztály.
 * @see Field
 */
public class Button extends Field {
	
	/**
	 * A gomb 60 súly felett kapcsol.
	 */
	private static final int BUTTON_WEIGHT_BOUND = 60;
	
	/**
	 * A gombhoz tartozó lyuk.
	 */
	private Hole hole;
	
	public Button(Hole hole) {
		this.hole = hole;
	}
	
	/**
	 * A kapcsoló állapotát irányítja.
	 * A lyukat nyitja, ha a kapcsolót megnyomják vagy zárja, ha már nem nyomják.
	 * @param state Ture a nyitáshoz, False a záráshoz.
	 */
	public void setButtonState(boolean state) {
		Skeleton.printStart(this, "setButtonState()");
		hole.setOpened(state);
		Skeleton.printReturn("void");
	}

	/**
	 * Mozgatható objektum lép a gombra.
	 * @param m A mozgatható objektum.
	 * @param d A lépés iránya.
	 * @return True, ha nincs rajta objektum.
	 * Ekkor magáralépteti az objektumot, az objektum pedig ránehézkedik a gombra.
	 * @return True, ha eltolja a rajtalévő mozgathatót és az új objektum lép rá.
	 * Ilyenkor az új objektum nehézkedik rá a gombra.
	 * @return False, ha nem tud rálépni az objektum.
	 */
	@Override
	public boolean placeMovableOnThis(Movable m, Direction d, int power) {
		Skeleton.printStart(this, "placeMovableOnThis( " + m + ", "+ d + ", power: " + power + ")");
		if(movable == null) {
			m.moveToField(this);
			m.weighOn(this, BUTTON_WEIGHT_BOUND);
			Skeleton.printReturn(true);
			return true;
		} else if(movable.push(m, d, power)) {
			m.moveToField(this);
			m.weighOn(this, BUTTON_WEIGHT_BOUND);
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
		return "B";
	}

	/**
	 * Visszaadja a képet a gombról.
	 */
	@Override
	public GameImage getImage() {
		return GameImage.BUTTON_IMG;
	}
	
}
