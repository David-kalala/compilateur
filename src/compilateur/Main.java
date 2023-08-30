package compilateur;

public class Main {

	public static void main(String[] args) {
		
		AnalyseurLexicale analyseLexicale = new AnalyseurLexicale(" oezdij34zDd +13 + - ! () eoden2SZ 9 if");
		analyseLexicale.runAnalyse();
		analyseLexicale.afficheListeTokens();
	}
}
