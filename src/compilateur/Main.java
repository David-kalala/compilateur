package compilateur;

import compilateur.type.Type_token;

public class Main {

	public static void main(String[] args) {
		
		Noeud n = new Noeud();
		AnalyseurLexicale analyseLexicale = new AnalyseurLexicale("12+EOF");
		AnalyseurSyntaxique analyseSyntaxique = new AnalyseurSyntaxique(analyseLexicale);
		GenerationCode generationCode = new GenerationCode();
		
		analyseLexicale.next();
		
		while(analyseLexicale.current_token.type != Type_token.EOF) {
			//analyseLexicale.next();

			n = analyseSyntaxique.run_analyse();
			generationCode.genCode(n);
		}
	
		analyseLexicale.afficheListeTokens();
	}
}
