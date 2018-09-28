package gameobject;

import gui.GameImage;
import skeleton.Skeleton;

/**
 * A lyukakat reprezentáló osztály.
 * @see Movable
*/
public class Hole extends Field {
	
	/** 
	 * A lyuk állapota.
	 */	
	private boolean opened;
	
	/**
	 * Konstruktor. A lyuk alapértelmezett esetben zárva van.
	 */
	public Hole() {
		opened = false;
	}
	
	/**
	 * A lyuk állapotát változtatja.
	 * @param state Az új állapot értéke.
	 */
	public void setOpened(boolean state) {
		Skeleton.printStart(this, "setOpened()");
		opened = state;
		Skeleton.printReturn("void");
	}

	/**
	 * Mozgatható objektum lép lyukra.
	 * @param m A mozgathato objektum.
	 * @param d A lepes iranya.
	 * @return True, ha a lyuk nyitva van.
	 * Ekkor megöli az objektumot.
	 * @return True, ha nincs rajta objektum. Ekkor magáralepteti az objektumot.
	 * @return True, ha eltolja a rajtalevo mozgathatot es az uj objektum lep ra.
	 * @return False, ha nem tud ralepni az objektum.
	 */
	public boolean placeMovableOnThis(Movable m, Direction d, int power) {
		Skeleton.printStart(this, "placeMovableOnThis( " + m + ", "+ d + ", power: " + power + ")");
		if(opened) {
			m.die();
			Skeleton.printReturn(true);
			return true;
		}else{
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
	}

	/**
	 * @see Field
	 */
	@Override
	public String protoFieldInfo() {
		String s = "H";
		if(opened)
			s += "+";
		else
			s += "-";
		return s;
	}

	/**
	 * Visszaadja a lyuk képét.
	 */
	@Override
	public GameImage getImage() {
		if(opened)
			return GameImage.HOLE_IMG;
		else
			return GameImage.FLOOR_IMG;
	}
	
}
