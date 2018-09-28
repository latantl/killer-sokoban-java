package gameobject;

import gui.GameImage;

/**
 * A játékon belüli objektumok közös őse.
 * Grafikus és konzolos kimeneti interfészként is fel lehet fogni.
 */
public abstract class GameObject {
	
	/**
	 * A prototípus kimeneti pályanyelvéhez használt metódus.
	 * Leírja egy játék objektum típusát és állapotát.
	 * @return játék objektum típusa és állapota.
	 */
	public abstract String protoInfo();
	
	/**
	 * Visszaadja Egy adott egység képét.
	 * @return Az objektumhoz tartozó kép.
	 */
	public abstract GameImage getImage();
	
}
