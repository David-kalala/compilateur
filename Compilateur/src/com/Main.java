package com;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {

		if (args.length < 2) {
			System.out.println("Il manque des arguments sur la ligne de commande.");
			System.out.println("Usage : java com/Main lib.c programme.c");
		} 
		else if (args.length > 2) {
			System.out.println("Il y a trop d'arguments sur la ligne de commande.");
			System.out.println("Usage : java com/Main lib.c programme.c");
		}
		else {
			Compile compile = new Compile();
			compile.run_compile(args[0], args[1]);
		}
	}
}
