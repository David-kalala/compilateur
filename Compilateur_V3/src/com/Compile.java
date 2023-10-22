package com;

import java.io.IOException;

public class Compile {

	AnalyseurLexicale analyseLexicale;
	AnalyseurSyntaxique analyseSyntaxique;
	AnalyseurSemantique analyseSemantique;
	
	GenerationCode generationCode;
	
	public Compile() throws IOException {
		analyseLexicale = new AnalyseurLexicale();
		analyseSyntaxique = new AnalyseurSyntaxique(analyseLexicale);
		analyseSemantique = new AnalyseurSemantique(analyseSyntaxique);
		generationCode = new GenerationCode();
		
	}
	
	public void run_compile(String libName, String programmeName) {
		
		
		this.compile_fichier(libName);
		this.analyseLexicale.resetAnalyserLexicale();
		this.compile_fichier(programmeName);
		
		this.generationCode.startGenCode();
		
		generationCode.close();
	}
	
	public void compile_fichier(String programmeName) {
		this.generationCode.setFilesNames(programmeName);
		
		Noeud noeud = new Noeud();
		StringBuffer sb = generationCode.chargerFichier();
		
		// Initialisation
		analyseLexicale.chargerFichier(sb.toString());
		analyseLexicale.next();

		while(analyseLexicale.current_token.type != Type_token.EOF) {
			analyseSemantique.resetNbVariables();
			noeud = analyseSyntaxique.run_analyseSyntaxique();
			analyseSemantique.run_analyseSemantique(noeud);
			generationCode.genCode(noeud);
		}
	}
}