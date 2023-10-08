package main;

import java.io.IOException;

import other.Compile;

public class Main {

	public static void main(String[] args) throws IOException {

		Compile compile = new Compile();
		compile.run_compile("src/lib.c", "src/programme.c");
	}
}
