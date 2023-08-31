package compilateur;

import compilateur.type.Type_token;

public class Main {

	public static void main(String[] args) {
		
		Noeud arbre = new Noeud();
		AnalyseurLexicale analyseLexicale = new AnalyseurLexicale("124 + EOF");
		AnalyseurSyntaxique analyseSyntaxique = new AnalyseurSyntaxique(analyseLexicale);
		GenerationCode generationCode = new GenerationCode();
		
		analyseLexicale.next();
		
		while(analyseLexicale.current_token.type != Type_token.EOF) {
			//analyseLexicale.next();

			arbre = analyseSyntaxique.run_analyse();
			generationCode.genCode(arbre);
		}
	
		analyseLexicale.afficheListeTokens();
	}
}
