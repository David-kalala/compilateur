package compilateur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import compilateur.type.Type_token;

public class Main {

	public static void main(String[] args) throws IOException {
			
		Noeud noeud = new Noeud();
		StringBuffer sb;
		
		GenerationCode generationCode = new GenerationCode("code_assembleur.asm", "programme.txt");
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
