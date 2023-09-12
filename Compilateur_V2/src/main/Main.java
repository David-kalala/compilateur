package main;

import java.io.IOException;

import analyseur.AnalyseurLexicale;
import analyseur.AnalyseurSemantique;
import analyseur.AnalyseurSyntaxique;
import genCode.GenerationCode;
import other.Noeud;
import type.Type_token;

public class Main {

	public static void main(String[] args) throws IOException {
			
		Noeud noeud = new Noeud();
		StringBuffer sb;
		
		GenerationCode generationCode = new GenerationCode("Compilateur_V2/src/code_assembleur.asm", "Compilateur_V2/src/programme.txt");
		sb = generationCode.chargerFichier();
		
		AnalyseurLexicale analyseLexicale = new AnalyseurLexicale(sb.toString());
		AnalyseurSyntaxique analyseSyntaxique = new AnalyseurSyntaxique(analyseLexicale);
		AnalyseurSemantique analyseSemantique = new AnalyseurSemantique(analyseSyntaxique);

		analyseLexicale.next();

		while(analyseLexicale.current_token.type != Type_token.EOF) {
			analyseSemantique.resetNbVariables();
			noeud = analyseSyntaxique.run_analyseSyntaxique();
			analyseSemantique.run_analyseSemantique(noeud);
			generationCode.genCode(noeud);
		}
		
		generationCode.endGenCode();
		generationCode.close();
	}
}
