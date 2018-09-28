package skeleton;

import game.Game;
import gameobject.*;
import gui.GameImage;

/**
 * A Skeleton főprogramjának osztálya.
 */
public class Skeleton {

	/**
	 * A skeleton módja. True: bekapcsolva, False: kikapcsolva.
	 */
	public static boolean write = false;
	/**
	 * Behuzás mértéke.
	 */
    private static int tab = 0;
    
    /**
     * Metódus indulásának kiírásához használható metódus.
     * @param o Az objektum, amelyen meghívódott a metódus. Célszerűen: this
     * @param s A metódus meghívásának részletei szövegben.
     */
    public static void printStart(Object o, String s) {
    	if(!write) return;
    	for(int i = 0; i < tab; i++)
    		System.out.print("|    ");
    	System.out.println(o + "." + s);
    	tab++;
    }
    
    /**
     * Metódus visszatérésének kiírásához használható metódus.
     * @param r A visszatérési érték, melynek a toString() metódusanak visszatérési értéke lesz kiírva.
     */
    public static void printReturn(Object r) {
    	if(!write) return;
    	tab--;
    	for(int i = 0; i < tab; i++)
    		System.out.print("|    ");
    	System.out.println("return value: " + r);
    }

    /**
     * Skeleton forgatókönyv végrehajtása.
     * @param s A skeleton bemenete.
     */
    private static void scenario(String s) {
    	if(s.charAt(0) != 'P') {
    		System.out.println(s + ": Hiba a bemenetben! A forgatókönyv nem játékossal kezdődik!");
    		return;
    	}
    	write = false;
    	Player p = new Player(160, GameImage.PLAYER1_IMG);
    	if(Game.buildSkeleton(p, s) != null) {
    		write = true;
    		System.out.println("\nScenario " + s + ":");
        	p.goInDirection(Direction.Right);
    	}
    }
    
    /**
     * Főprogram.
     * @param args A skeleton bemenetei (forgatókönyvek).
     */
    public static void main(String[] args) {
    	for(String s : args) {
    		scenario(s);
    	}
    }
    
    
}