package main;

import java.io.IOException;

import other.Compile;

public class Main {

	public static void main(String[] args) throws IOException {
	
//		Noeud noeud = new Noeud();
//		StringBuffer sb;
//		
//		GenerationCode generationCode = new GenerationCode("src/code_assembleur.asm", "src/programme.txt");
//		sb = generationCode.chargerFichier();
//		
//		AnalyseurLexicale analyseLexicale = new AnalyseurLexicale(sb.toString());
//		AnalyseurSyntaxique analyseSyntaxique = new AnalyseurSyntaxique(analyseLexicale);
//		AnalyseurSemantique analyseSemantique = new AnalyseurSemantique(analyseSyntaxique);
//
//		analyseLexicale.next();
//
//		while(analyseLexicale.current_token.type != Type_token.EOF) {
//			analyseSemantique.resetNbVariables();
//			noeud = analyseSyntaxique.run_analyseSyntaxique();
//			analyseSemantique.run_analyseSemantique(noeud);
//			generationCode.genCode(noeud);
//		}
//		
//		generationCode.endGenCode();
//		generationCode.close();
		
		
		Compile compile = new Compile();
		compile.run_compile("src/lib.c", "src/programme.c");
	}
}
