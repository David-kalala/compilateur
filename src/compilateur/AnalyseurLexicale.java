package compilateur;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalyseurLexicale {

	private Token current_token;
	private Token previous_token;
	private String programme;
	private int index = 0;
	private ArrayList<Token> token_liste;

	AnalyseurLexicale(String programme) {
		this.programme = programme;
		token_liste = new ArrayList<Token>();
	}

	void runAnalyse() {
		while (index < programme.length()) {
			next();
			token_liste.add(current_token);
		}
	}

	void afficheListeTokens() {
		for (Token t : token_liste) {
			System.out.println("Valeur : " + t.valeur + " | Type : " + t.type);
			System.out.println();
		}
	}

	boolean check(Type type) {
		if (current_token.type == type) {
			next();
			return true;
		}
		else {
			//J'ai ajouté ça 
			accept(type);
			return false;
		}
	}


	void accept(Type type) {
		if (!check(type)) {
			System.err.println("ERREUR FATALE !");
			System.exit(0);
		}
	}

	void next() {
		previous_token = current_token;
		current_token = this.lectureProgramme();
	}

	@SuppressWarnings("deprecation")
	Token lectureProgramme() {
		Token tokenTmp = null;
		while (programme.charAt(index) == ' ') {
			index++;
		}

		if (programme.charAt(index) == '+') {
			tokenTmp = new Token(Character.toString(programme.charAt(index)), Type.plus);
			index++;
			return tokenTmp;
		}
		else if (programme.charAt(index) == '-') {
			tokenTmp = new Token(Character.toString(programme.charAt(index)), Type.moins);
			index++;
			return tokenTmp;
		}
		else if (programme.charAt(index) == '!') {
			tokenTmp = new Token(Character.toString(programme.charAt(index)), Type.exclamation);
			index++;
			return tokenTmp;
		}
		else if (programme.charAt(index) == '(') {
			tokenTmp = new Token(Character.toString(programme.charAt(index)), Type.parenthese_gauche);
			index++;
			return tokenTmp;
		}
		else if (programme.charAt(index) == ')') {
			tokenTmp = new Token(Character.toString(programme.charAt(index)), Type.parenthese_droite);
			index++;
			return tokenTmp;
		}
		else if (programme.charAt(index) == '{') {
			tokenTmp = new Token(Character.toString(programme.charAt(index)), Type.acolade_gauche);
			index++;
			return tokenTmp;
		}
		else if (programme.charAt(index) == '}') {
			tokenTmp = new Token(Character.toString(programme.charAt(index)), Type.acolade_droite);
			index++;
			return tokenTmp;
		}
		else if (programme.charAt(index) == '[') {
			tokenTmp = new Token(Character.toString(programme.charAt(index)), Type.crochet_gauche);
			index++;
			return tokenTmp;
		}
		else if (programme.charAt(index) == ']') {
			tokenTmp = new Token(Character.toString(programme.charAt(index)), Type.crochet_droit);
			index++;
			return tokenTmp;
		}
		else if (programme.charAt(index) == '>') {
			tokenTmp = new Token(Character.toString(programme.charAt(index)), Type.superieur);
			index++;
			return tokenTmp;
		}
		else if (programme.charAt(index) == '<') {
			tokenTmp = new Token(Character.toString(programme.charAt(index)), Type.inferieur);
			index++;
			return tokenTmp;
		}
		else if (programme.charAt(index) == '=') {
			tokenTmp = new Token(Character.toString(programme.charAt(index)), Type.egal);
			index++;
			return tokenTmp;
		}
		else if (programme.charAt(index) == ',') {
			tokenTmp = new Token(Character.toString(programme.charAt(index)), Type.virgule);
			index++;
			return tokenTmp;
		}
		else if (programme.charAt(index) == '/') {
			tokenTmp = new Token(Character.toString(programme.charAt(index)), Type.slash);
			index++;
			return tokenTmp;
		}
		else if (programme.charAt(index) == '&') {
			tokenTmp = new Token(Character.toString(programme.charAt(index)), Type.esperluette);
			index++;
			return tokenTmp;
		}
		else if (Pattern.matches("[0-9]", Character.toString(programme.charAt(index)))) {
			StringBuilder numberBuilder = new StringBuilder();
			while (index < programme.length() && Character.isDigit(programme.charAt(index))) {
				numberBuilder.append(programme.charAt(index));
				index++;
			}
			tokenTmp = new Token(numberBuilder.toString(), Type.constante);
			index++;
			return tokenTmp;
		}
		else if (Pattern.matches(".*\\bif\\b.*", Character.toString(programme.charAt(index)))) {
			StringBuilder ifBuilder = new StringBuilder();
			while (index < programme.length() && !Character.isSpaceChar(programme.charAt(index))){
				ifBuilder.append(programme.charAt(index));
				index++;
			}
			tokenTmp = new Token(ifBuilder.toString(), Type.if_token);
			index++;
			return tokenTmp;
		}
		else if (Pattern.matches(".*[a-zA-Z0-9]*", Character.toString(programme.charAt(index)))) {
			StringBuilder alphaNumeric = new StringBuilder();
			while (index < programme.length() && !Character.isSpaceChar(programme.charAt(index))){
				alphaNumeric.append(programme.charAt(index));
				index++;
			}
			tokenTmp = new Token(alphaNumeric.toString(), Type.identificateur);
			index++;
			return tokenTmp;
		}
		else {
			return null;
		}

	}
}
