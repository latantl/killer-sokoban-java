package proto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import game.*;
import gameobject.Field;
import skeleton.Skeleton;

/**
 * A prototípus programot megvalósító osztály.
 */
public class Prototype {
	
	/**
	 * A prototípus program minden állapotában leadható parancsok listája.
	 */
	private static final ArrayList<String> dictionary = new ArrayList<String>(Arrays.asList(
			"cmds","exit","state","randoff","randon","sklt+","sklt-" ));
	
	/**
	 * Egy parancsot végrehajtó metódus.
	 * Ha állapotspecifikus a parancs, akkor továbbadja a feladatot az állapot menedzsernek.
	 * @param cmd A parancs neve.
	 */
	private static void protoExecute(String cmd) {
		if( !(dictionary.contains(cmd)|StateManager.SOKOBAN.getDictionary().contains(cmd)) ) {
			System.out.println("< Unknown command '"+cmd+"' in state '"+StateManager.SOKOBAN.getActualStateName()+"'");
			return;
		}
		switch(cmd) {
		case "cmds": listCommands(); break;
		case "state": StateManager.SOKOBAN.protoOut(); break;
		case "randoff": Game.RANDOM = false; break;
		case "randon": Game.RANDOM = true; break;
		case "sklt+": Skeleton.write = true; break;
		case "sklt-": Skeleton.write = false; break;
		default: StateManager.SOKOBAN.protoExecute(cmd);
		}
	}
	
	/**
	 * Pillanatnyilag leadható parancsokat kilistázó metódus.
	 */
	private static void listCommands() {
		System.out.print("< available commands:");
		for(String s : dictionary)
			System.out.print(" '"+s+"'");
		for(String s : StateManager.SOKOBAN.getDictionary())
			System.out.print(" '"+s+"'");
		System.out.println();
	}
	
	/**
	 * A prototípus főprogram.
	 * @param args Előre leadott parancsok.
	 */
	public static void main(String[] args) {
		StateManager.SOKOBAN = new StateManager(new StateGameBefore(), new StateGame(), new StateGameAfter());
		Scanner reader = new Scanner(System.in);
		boolean go = true;
		while(go) {
			for(String s : args) {
				if(s.equals("exit")) {
					go = false;
					break;
				}
				protoExecute(s);
			}
			StateManager.SOKOBAN.protoOut();
			if(go) {
				System.out.print("> ");
				args = reader.nextLine().split(" ");
			}
		}
		reader.close();
	}
	
	/**
	 * A pálya egy állapotát a pálya nyelvén karakteresen kiíró metódus.
	 */
	public static void printFields() {
		Field[][] fields = Game.SOKOBANGAME.getFields();
		for(int i = 0; i < fields.length; i++) {
			for(int j = 0; j < fields[i].length; j++) {
				String s = fields[i][j].protoInfo();
				while(s.length() < 4)
					s += " ";
				System.out.print(s);
			}
			System.out.println();
		}	
	}
	
}
