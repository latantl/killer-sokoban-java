package gameobject;

import skeleton.Skeleton;

/**
 * Mozgatható tárgyakat reprezentáló osztály.
 * absztrakt
 */

public abstract class Movable extends GameObject {
	/**
	 *  Az a mező, amin a mozgatható tárgy van.
	 */
	protected Field field;
	/**
	 * Megadja, hogy a mozgatható tárgy életben van-e vagy sem.
	 */
	private boolean alive;
	/**
	 * A mozgatható tárgy súlya.
	 */
	private int weight;
	/**
	 * Az irány, amelybe a tárgy néz, fokban megadva.
	 */
	protected double heading = 0.0;
	/**
	 * Konstruktor.
	 * @param weight a mozgó tárgy súlya.
	 */
	public Movable(int weight) {
		this.weight = weight;
		alive = true;
	}
	/**
	 * Adott mezőre mozgatja a mozgathatót.
	 * @param f: mező
	 */
	public void moveToField(Field f) {
		Skeleton.printStart(this, "moveToField(Field: " + f + ")");
		if(field != null)
			field.clearMovable();
		field = f;
		f.setMovable(this);
		Skeleton.printReturn("void");
	}
	/**
	 * Mozgathatót megőlő függvény.
	 */
	public void die() {
		Skeleton.printStart(this, "die()");
		alive = false;
		field.clearMovable();
		field = null;
		Skeleton.printReturn("void");
	}
	/**
	 * Megadja, hogy az adott mozgatható tárgy életben van-e vagy sem.
	 * @return alive: Ture, ha életben van a mozgathaté.
	 */
	public boolean isAlive() {
		Skeleton.printStart(this, "isAlive()");
		Skeleton.printReturn(alive);
		return alive;
	}
	/**
	 * Megadja, hogy adott irányban, adott mozgatható objektum mozgatható-e.
	 * @param m A mozgatható objektum.
	 * @param d A lépés iránya.
	 * @param power A tolás ereje.
	 * @return True, ha adott irányba megtolható az objektum.
	 * @return False, ha adott irányba nem tolható az objektum.
	 */
	public boolean push(Movable m, Direction d, int power) {
		Skeleton.printStart(this, "push(m: " + m + ", d: " + d + ", power: " + power + ")");
		power -= field.getFriction() * weight;
		if(power < 0) {
			Skeleton.printReturn(false);
			return false;
		}
		else if(field.getNeighbour(d).placeMovableOnThis(this, d, power)) {
			Skeleton.printReturn(true);
			return true;
		} else {
			boolean result = m.squeezeMovable(this, this.weight);
			Skeleton.printReturn(result);
			return result;
		}
	}
	/**
	 * Adott mozgatható objektum össze tud-e nyomni egy másikat.
	 * @param m A mozgatható objektum.
	 * @param w Az objektum súlya.
	 * @return True, ha a mozgatható objektum összenyomható a paraméterként megadott által.
	 * @return False, ha a mozgatható objektum nem összenyomható a paraméterként megadott által.
	 */
	public boolean squeezeMovable(Movable m, int w) {
		Skeleton.printStart(this, "squeezeMovable(m: " + m + ",w: " + w + ")");
		if(this.weight > w) {
			m.die();
			Skeleton.printReturn(true);
			return true;
		} else {
			Skeleton.printReturn(false);
			return false;
		}
	}
	/**
	 * Megvizsgálja, hogy adott gombra érkező mozgatható objektum súlya a határérték alatt/felett van.
	 * Ha az objektum súlya nagyobb, mint a határérték, a gomb lenyomódik.
	 * Ha az objektum súlya kisebb, mint a határérték, a gomb nem nyomódik le.
	 * @param b Gomb.
	 * @param bound Határérték.
	 */
	public void weighOn(Button b, int bound) {
		Skeleton.printStart(this, "weighOn(b: " + b + "bound: " + bound + ")");
		b.setButtonState(weight > bound);
		Skeleton.printReturn("void");
	}
	/**
	 * A mozgó objektum nézési irányát adja vissza.
	 * @return Az objektum iránya.
	 */
	public double getHeading() {
		return heading;
	} 
	/**
	 * Absztrakt függvény.
	 * A célmezőre lépést kezeli.
	 * @param t Célmező.
	 */
	public abstract void moveToTarget(Target t);
	/**
	 * Absztakt függvény.
	 * Adott irányba tolható-e a mozgatható objektum.
	 * @param d Irány.
	 * @return True, ha a megadott irányban a mozgatható objektum tolható.
	 */
	public abstract boolean isPushable(Direction d);
	
}