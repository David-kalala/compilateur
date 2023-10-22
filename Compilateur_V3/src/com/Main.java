package com;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {

		if (args.length <= 1 && args.length > 2) {
			System.out.println("Usage : java com/Main lib.c programme.c");
		}
		System.out.println(args.length);

		Compile compile = new Compile();
		compile.run_compile(args[0], args[1]);
	}
}
